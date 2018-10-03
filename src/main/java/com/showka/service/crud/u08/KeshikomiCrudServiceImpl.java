package com.showka.service.crud.u08;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.showka.domain.Keshikomi;
import com.showka.domain.Nyukin;
import com.showka.domain.Urikake;
import com.showka.domain.UrikakeKeshikomi;
import com.showka.domain.builder.KeshikomiBuilder;
import com.showka.entity.CKeshikomi;
import com.showka.entity.TKeshikomi;
import com.showka.repository.i.CKeshikomiRepository;
import com.showka.repository.i.TKeshikomiRepository;
import com.showka.service.crud.u05.i.UrikakeCrudService;
import com.showka.service.crud.u06.i.UrikakeKeshikomiCrudService;
import com.showka.service.crud.u07.i.SeikyuUrikakeCrudService;
import com.showka.service.crud.u08.i.KeshikomiCrudService;
import com.showka.service.crud.u08.i.NyukinCrudService;
import com.showka.value.AmountOfMoney;
import com.showka.value.EigyoDate;
import com.showka.value.TheTimestamp;

@Service
public class KeshikomiCrudServiceImpl implements KeshikomiCrudService {

	@Autowired
	private TKeshikomiRepository repo;

	@Autowired
	private CKeshikomiRepository cancelRepo;

	@Autowired
	private NyukinCrudService nyukinCrudService;

	@Autowired
	private UrikakeCrudService urikakeCrudService;

	@Autowired
	private SeikyuUrikakeCrudService seikyuUrikakeCrudService;

	@Autowired
	private UrikakeKeshikomiCrudService urikakeKeshikomiCrudService;

	@Override
	public void save(Keshikomi keshikomi) {
		// entity
		Optional<TKeshikomi> _e = repo.findById(keshikomi.getRecordId());
		TKeshikomi e = _e.orElse(new TKeshikomi());
		// set columns
		e.setDate(keshikomi.getDate().toDate());
		e.setTimestamp(keshikomi.getTimestamp().toDate());
		e.setKingaku(keshikomi.getKingaku().intValue());
		e.setNyukinId(keshikomi.getNyukin().getRecordId());
		String urikakeId = keshikomi.getUrikakeId();
		e.setUrikakeId(urikakeId);
		// OCC
		e.setVersion(keshikomi.getVersion());
		// record id
		String recordId = _e.isPresent() ? e.getRecordId() : UUID.randomUUID().toString();
		e.setRecordId(recordId);
		keshikomi.setRecordId(recordId);
		// save
		repo.save(e);
		// 消込完了の場合、JSeikyuUrikakeからレコードを削除する
		// 消込未完の場合、JSeikyuUrikakeのレコードを戻す。
		UrikakeKeshikomi urikakeKeshikomi = urikakeKeshikomiCrudService.getDomain(urikakeId);
		if (urikakeKeshikomi.done()) {
			seikyuUrikakeCrudService.deleteIfExists(urikakeId);
		} else {
			seikyuUrikakeCrudService.revert(urikakeId);
		}
	}

	@Override
	public void override(String nyukinId, EigyoDate date, Collection<Keshikomi> keshikomiList) {
		// get old
		List<Keshikomi> oldList = this.getKeshikomiSetOfNyukin(nyukinId).stream().filter(k -> {
			// 営業日が同じもののみ抽出
			return k.getDate().equals(date);
		}).collect(Collectors.toList());
		// delete removed
		oldList.stream().filter(o -> {
			return !keshikomiList.contains(o);
		}).forEach(o -> {
			delete(o.getRecordId(), o.getVersion());
		});
		// save
		keshikomiList.forEach(this::save);
	}

	@Override
	public Set<Keshikomi> getKeshikomiSetOfNyukin(String nyukinId) {
		// example
		TKeshikomi e = new TKeshikomi();
		e.setNyukinId(nyukinId);
		Example<TKeshikomi> example = Example.of(e);
		// find
		List<TKeshikomi> keshikomiList = repo.findAll(example);
		// build as map
		return keshikomiList.stream().map(k -> {
			return this.getDomain(k.getRecordId());
		}).collect(Collectors.toSet());
	}

	@Override
	public Set<Keshikomi> getKeshikomiSetOfUrikake(String urikakeId) {
		// example
		TKeshikomi e = new TKeshikomi();
		e.setUrikakeId(urikakeId);
		Example<TKeshikomi> example = Example.of(e);
		// find
		List<TKeshikomi> keshikomiList = repo.findAll(example);
		// build as map
		return keshikomiList.stream().map(k -> {
			return this.getDomain(k.getRecordId());
		}).collect(Collectors.toSet());
	}

	@Override
	public void cancel(String keshikomiId, EigyoDate date) {
		TKeshikomi e = repo.getOne(keshikomiId);
		// delete
		this.delete(e.getRecordId(), e.getVersion());
		// cancel
		CKeshikomi ce = new CKeshikomi();
		ce.setCancelDate(date.toDate());
		ce.setDate(e.getDate());
		ce.setKingaku(e.getKingaku());
		ce.setNyukinId(e.getNyukinId());
		ce.setTimestamp(e.getTimestamp());
		ce.setUrikakeId(e.getUrikakeId());
		ce.setRecordId(e.getRecordId());
		cancelRepo.save(ce);
	}

	/**
	 * 削除.
	 * 
	 * @param keshikomiId
	 *            消込ID
	 * @param version
	 *            バージョン
	 */
	void delete(String keshikomiId, Integer version) {
		// entity
		TKeshikomi e = repo.getOne(keshikomiId);
		// OCC
		e.setVersion(version);
		// delete
		repo.delete(e);
		// 消込データが削除された場合、請求売掛関係テーブルのレコード復帰処理が必要。
		seikyuUrikakeCrudService.revert(e.getUrikakeId());
	}

	/**
	 * 消込取得.
	 * 
	 * @param keshikomiId
	 *            消込ID
	 * @return 消込
	 */
	Keshikomi getDomain(String keshikomiId) {
		// entity
		TKeshikomi e = repo.getOne(keshikomiId);
		// get 入金
		Nyukin nyukin = nyukinCrudService.getDomain(e.getNyukinId());
		// get 売掛
		Urikake urikake = urikakeCrudService.getDomain(e.getUrikakeId());
		// set builder
		KeshikomiBuilder b = new KeshikomiBuilder();
		b.withNyukin(nyukin);
		b.withUrikake(urikake);
		b.withDate(new EigyoDate(e.getDate()));
		b.withTimestamp(new TheTimestamp(e.getTimestamp()));
		b.withKingaku(new AmountOfMoney(e.getKingaku()));
		b.withRecordId(e.getRecordId());
		b.withVersion(e.getVersion());
		// build
		return b.build();
	}

	/**
	 * 存在検証.
	 * 
	 * @param keshikomiId
	 *            消込ID
	 * @return 存在する場合true
	 */
	boolean exsists(String keshikomiId) {
		return repo.existsById(keshikomiId);
	}
}
