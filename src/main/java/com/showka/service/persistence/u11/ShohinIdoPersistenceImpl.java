package com.showka.service.persistence.u11;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.showka.domain.builder.ShohinIdoBuilder;
import com.showka.domain.u11.ShohinIdo;
import com.showka.domain.u11.ShohinIdoMeisai;
import com.showka.domain.z00.Busho;
import com.showka.domain.z00.Shohin;
import com.showka.entity.TShohinIdo;
import com.showka.entity.TShohinIdoMeisai;
import com.showka.kubun.ShohinIdoKubun;
import com.showka.kubun.i.Kubun;
import com.showka.repository.i.TShohinIdoMeisaiRepository;
import com.showka.repository.i.TShohinIdoRepository;
import com.showka.service.persistence.u11.i.ShohinIdoMeisaiPersistence;
import com.showka.service.persistence.u11.i.ShohinIdoPersistence;
import com.showka.service.persistence.u11.i.ShohinZaikoPersistence;
import com.showka.service.persistence.z00.i.BushoPersistence;
import com.showka.service.specification.u11.i.ShohinIdoSpecification;
import com.showka.system.exception.UnsatisfiedSpecificationException;
import com.showka.value.EigyoDate;
import com.showka.value.TheDate;
import com.showka.value.TheTimestamp;

@Service
public class ShohinIdoPersistenceImpl implements ShohinIdoPersistence {

	@Autowired
	private TShohinIdoRepository repo;

	@Autowired
	private TShohinIdoMeisaiRepository tShohinIdoMeisaiRepository;

	@Autowired
	private ShohinIdoMeisaiPersistence shohinIdoMeisaiPersistence;

	@Autowired
	private BushoPersistence bushoPersistence;

	@Autowired
	private ShohinZaikoPersistence shohinZaikoPersistence;

	@Override
	public void save(ShohinIdo domain) {
		// 部署
		Busho busho = domain.getBusho();
		// 移動の営業日
		EigyoDate date = domain.getDate();
		// 移動明細
		List<ShohinIdoMeisai> meisai = domain.getMeisai();
		// domain -> entity
		Optional<TShohinIdo> _e = repo.findById(domain.getRecordId());
		TShohinIdo entity = _e.isPresent() ? _e.get() : new TShohinIdo();
		if (!_e.isPresent()) {
			String recordId = UUID.randomUUID().toString();
			entity.setRecordId(recordId);
		}
		entity.setBushoId(busho.getRecordId());
		entity.setDate(date.toDate());
		entity.setKubun(domain.getKubun().getCode());
		entity.setTimestamp(domain.getTimestamp().toDate());
		// occ
		entity.setVersion(domain.getVersion());
		// record id
		domain.setRecordId(entity.getRecordId());
		// save
		repo.save(entity);
		// meisai
		shohinIdoMeisaiPersistence.overrideList(entity.getRecordId(), meisai);
		// 在庫データばしの場合は0在庫レコードを作る
		meisai.forEach(m -> {
			Shohin shohin = m.getShohinDomain();
			shohinZaikoPersistence.saveZeroIfEmpty(busho, date, shohin);
		});
	}

	@Override
	public void delete(ShohinIdo domain) {
		String pk = domain.getRecordId();
		Integer version = domain.getVersion();
		// delete meisai
		shohinIdoMeisaiPersistence.deleteAll(pk);
		// occ
		TShohinIdo entity = repo.getOne(pk);
		entity.setVersion(version);
		// delete
		repo.delete(entity);
	}

	@Override
	public ShohinIdo getDomain(String pk) {
		// get entity
		TShohinIdo entity = repo.getOne(pk);
		// get other domains
		Busho busho = bushoPersistence.getDomain(entity.getBusho().getCode());
		List<ShohinIdoMeisai> meisai = shohinIdoMeisaiPersistence.getDomainList(pk);
		// build domain
		ShohinIdoBuilder b = new ShohinIdoBuilder();
		b.withBusho(busho);
		b.withDate(new EigyoDate(entity.getDate()));
		b.withKubun(Kubun.get(ShohinIdoKubun.class, entity.getKubun()));
		b.withMeisai(meisai);
		b.withRecordId(entity.getRecordId());
		b.withTimestamp(new TheTimestamp(entity.getTimestamp()));
		b.withVersion(entity.getVersion());
		return b.build();
	}

	@Override
	public boolean exsists(String pk) {
		return repo.existsById(pk);
	}

	@Override
	public List<ShohinIdo> getShohinIdoListInDate(Busho busho, TheDate date, Shohin shohin) {
		// criteria
		TShohinIdo ido = new TShohinIdo();
		TShohinIdoMeisai idoMeisai = new TShohinIdoMeisai();
		ido.setBushoId(busho.getRecordId());
		ido.setDate(date.toDate());
		idoMeisai.setShohinId(shohin.getRecordId());
		idoMeisai.setShohinIdo(ido);
		// search
		Example<TShohinIdoMeisai> example = Example.of(idoMeisai);
		List<TShohinIdoMeisai> idoMeisaiList = tShohinIdoMeisaiRepository.findAll(example);
		// distinct
		Stream<TShohinIdo> idoList = idoMeisaiList.stream().map(meisai -> {
			return meisai.getShohinIdo();
		}).distinct();
		// build & return
		return idoList.map(i -> {
			return getDomain(i.getRecordId());
		}).collect(Collectors.toList());
	}

	@Override
	public void shohinIdo(ShohinIdoSpecification specification) throws UnsatisfiedSpecificationException {
		// 業務的仕様を満たすか判定
		specification.ascertainSatisfaction();
		// 削除対象の商品移動を削除
		List<ShohinIdo> idoListForDelete = specification.getShohinIdoForDelete();
		idoListForDelete.forEach(d -> this.delete(d));
		// 新たに商品移動を登録
		List<ShohinIdo> idoList = specification.getShohinIdo();
		idoList.forEach(this::save);
	}

	@Override
	public void shohinIdoForcibly(ShohinIdoSpecification specification) {
		// 削除対象の商品移動を削除
		List<ShohinIdo> idoListForDelete = specification.getShohinIdoForDelete();
		idoListForDelete.forEach(d -> this.delete(d));
		// 新たに商品移動を登録
		List<ShohinIdo> idoList = specification.getShohinIdo();
		idoList.forEach(this::save);
	}

	@Override
	public void deleteForcibly(String id) {
		ShohinIdo domain = this.getDomain(id);
		this.delete(domain);
	}
}