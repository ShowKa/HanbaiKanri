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
import com.showka.domain.builder.KeshikomiBuilder;
import com.showka.entity.TKeshikomi;
import com.showka.repository.i.TKeshikomiRepository;
import com.showka.service.crud.u05.i.UrikakeCrudService;
import com.showka.service.crud.u08.i.KeshikomiCrudService;
import com.showka.service.crud.u08.i.NyukinCrudService;
import com.showka.value.AmountOfMoney;
import com.showka.value.EigyoDate;

@Service
public class KeshikomiCrudServiceImpl implements KeshikomiCrudService {

	@Autowired
	private TKeshikomiRepository repo;

	@Autowired
	private NyukinCrudService nyukinCrudService;

	@Autowired
	private UrikakeCrudService urikakeCrudService;

	@Override
	public void save(Keshikomi keshikomi) {
		// entity
		Optional<TKeshikomi> _e = repo.findById(keshikomi.getRecordId());
		TKeshikomi e = _e.orElse(new TKeshikomi());
		// set columns
		e.setDate(keshikomi.getDate().toDate());
		e.setKingaku(keshikomi.getKingaku().intValue());
		e.setNyukinId(keshikomi.getNyukin().getRecordId());
		e.setUrikakeId(keshikomi.getUrikake().getRecordId());
		// OCC
		e.setVersion(keshikomi.getVersion());
		// record id
		String recordId = _e.isPresent() ? e.getRecordId() : UUID.randomUUID().toString();
		e.setRecordId(recordId);
		keshikomi.setRecordId(recordId);
		// save
		repo.save(e);
	}

	@Override
	public void override(String nyukinId, EigyoDate date, Collection<Keshikomi> keshikomiList) {
		if (keshikomiList.isEmpty()) {
			return;
		}
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
