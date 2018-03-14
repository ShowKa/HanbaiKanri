package com.showka.service.crud.u11;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.showka.domain.Busho;
import com.showka.domain.Shohin;
import com.showka.domain.ShohinIdo;
import com.showka.domain.ShohinIdoMeisai;
import com.showka.domain.builder.ShohinIdoBuilder;
import com.showka.entity.TShohinIdo;
import com.showka.entity.TShohinIdoMeisai;
import com.showka.kubun.ShohinIdoKubun;
import com.showka.kubun.i.Kubun;
import com.showka.repository.i.TShohinIdoMeisaiRepository;
import com.showka.repository.i.TShohinIdoRepository;
import com.showka.service.crud.u11.i.ShohinIdoCrudService;
import com.showka.service.crud.u11.i.ShohinIdoMeisaiCrudService;
import com.showka.service.crud.z00.i.BushoCrudService;
import com.showka.service.specification.u11.i.ShohinIdoSpecification;
import com.showka.system.exception.UnsatisfiedSpecificationException;
import com.showka.value.TheDate;
import com.showka.value.TheTimestamp;

@Service
public class ShohinIdoCrudServiceImpl implements ShohinIdoCrudService {

	@Autowired
	private TShohinIdoRepository repo;

	@Autowired
	private TShohinIdoMeisaiRepository tShohinIdoMeisaiRepository;

	@Autowired
	private ShohinIdoMeisaiCrudService shohinIdoMeisaiCrudService;

	@Autowired
	private BushoCrudService bushoCrudService;

	@Override
	public void save(ShohinIdo domain) {
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
		// record id
		domain.setRecordId(entity.getRecordId());
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
	public ShohinIdo getDomain(String pk) {
		// get entity
		TShohinIdo entity = repo.getOne(pk);
		// get other domains
		Busho busho = bushoCrudService.getDomain(entity.getBusho().getCode());
		List<ShohinIdoMeisai> meisai = shohinIdoMeisaiCrudService.getDomainList(pk);
		// build domain
		ShohinIdoBuilder b = new ShohinIdoBuilder();
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
		idoListForDelete.forEach(d -> this.delete(d.getRecordId(), d.getVersion()));
		// 新たに商品移動を登録
		List<ShohinIdo> idoList = specification.getShohinIdo();
		idoList.forEach(this::save);
	}

	@Override
	public void shohinIdoForcibly(ShohinIdoSpecification specification) {
		// 削除対象の商品移動を削除
		List<ShohinIdo> idoListForDelete = specification.getShohinIdoForDelete();
		idoListForDelete.forEach(d -> this.delete(d.getRecordId(), d.getVersion()));
		// 新たに商品移動を登録
		List<ShohinIdo> idoList = specification.getShohinIdo();
		idoList.forEach(this::save);
	}

	@Override
	public void deleteForcibly(String recordId) {
		TShohinIdo record = repo.getOne(recordId);
		this.delete(recordId, record.getVersion());
	}
}
