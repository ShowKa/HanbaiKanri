package com.showka.service.crud.u11;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.BushoDomain;
import com.showka.domain.ShohinIdoDomain;
import com.showka.domain.ShohinIdoMeisaiDomain;
import com.showka.domain.builder.ShohinIdoDomainBuilder;
import com.showka.entity.TShohinIdo;
import com.showka.kubun.ShohinIdoKubun;
import com.showka.kubun.i.Kubun;
import com.showka.repository.i.TShohinIdoRepository;
import com.showka.service.crud.u11.i.ShohinIdoCrudService;
import com.showka.service.crud.u11.i.ShohinIdoMeisaiCrudService;
import com.showka.service.crud.z00.i.BushoCrudService;
import com.showka.value.TheDate;
import com.showka.value.TheTimestamp;

@Service
public class ShohinIdoCrudServiceImpl implements ShohinIdoCrudService {

	@Autowired
	private TShohinIdoRepository repo;

	@Autowired
	private ShohinIdoMeisaiCrudService shohinIdoMeisaiCrudService;

	@Autowired
	private BushoCrudService bushoCrudService;

	@Override
	public void save(ShohinIdoDomain domain) {
		// domain -> entity
		Optional<TShohinIdo> _e = repo.findById(domain.getRecordId());
		TShohinIdo entity = _e.isPresent() ? _e.get() : new TShohinIdo();
		if (!_e.isPresent()) {
			String recordId = UUID.randomUUID().toString();
			entity.setRecordId(recordId);
		}
		entity.setBushoId(domain.getBusho().getRecordId());
		entity.setDate(domain.getDate().toDate());
		entity.setKubun(domain.getKubun().getCode());
		entity.setTimestamp(domain.getTimestamp().toDate());
		// occ
		entity.setVersion(domain.getVersion());
		// save
		repo.save(entity);
		// meisai
		shohinIdoMeisaiCrudService.overrideList(entity.getRecordId(), domain.getMeisai());
	}

	@Override
	public void delete(String pk, Integer version) {
		// delete meisai
		shohinIdoMeisaiCrudService.deleteAll(pk);
		// occ
		TShohinIdo entity = repo.getOne(pk);
		entity.setVersion(version);
		// delete
		repo.delete(entity);
	}

	@Override
	@Deprecated
	public void delete(ShohinIdoDomain domain) {
		throw new RuntimeException("forbidden");
	}

	@Override
	public ShohinIdoDomain getDomain(String pk) {
		// get entity
		TShohinIdo entity = repo.getOne(pk);
		// get other domains
		BushoDomain busho = bushoCrudService.getDomain(entity.getBusho().getCode());
		List<ShohinIdoMeisaiDomain> meisai = shohinIdoMeisaiCrudService.getDomainList(pk);
		// build domain
		ShohinIdoDomainBuilder b = new ShohinIdoDomainBuilder();
		b.withBusho(busho);
		b.withDate(new TheDate(entity.getDate()));
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
}
