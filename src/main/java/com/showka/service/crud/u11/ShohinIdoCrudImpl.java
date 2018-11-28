package com.showka.service.crud.u11;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.builder.ShohinIdoBuilder;
import com.showka.domain.u11.ShohinIdo;
import com.showka.domain.u11.ShohinIdoMeisai;
import com.showka.domain.z00.Busho;
import com.showka.domain.z00.Shohin;
import com.showka.entity.TShohinIdo;
import com.showka.kubun.ShohinIdoKubun;
import com.showka.kubun.i.Kubun;
import com.showka.repository.i.TShohinIdoRepository;
import com.showka.service.crud.u11.i.ShohinIdoCrud;
import com.showka.service.crud.u11.i.ShohinIdoMeisaiCrud;
import com.showka.service.crud.z00.i.BushoCrud;
import com.showka.service.persistence.u11.i.ShohinZaikoPersistence;
import com.showka.value.EigyoDate;
import com.showka.value.TheTimestamp;

@Service
public class ShohinIdoCrudImpl implements ShohinIdoCrud {

	@Autowired
	private TShohinIdoRepository repo;

	@Autowired
	private ShohinIdoMeisaiCrud shohinIdoMeisaiCrud;

	@Autowired
	private BushoCrud bushoPersistence;

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
		shohinIdoMeisaiCrud.overrideList(entity.getRecordId(), meisai);
		// TODO Listener を作って移動したほうがよいかも。
		// 在庫データなしの場合は0在庫レコードを作る
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
		shohinIdoMeisaiCrud.deleteAll(pk);
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
		List<ShohinIdoMeisai> meisai = shohinIdoMeisaiCrud.getDomainList(pk);
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
	public boolean exists(String pk) {
		return repo.existsById(pk);
	}
}
