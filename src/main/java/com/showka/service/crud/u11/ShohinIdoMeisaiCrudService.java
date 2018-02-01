package com.showka.service.crud.u11;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.showka.domain.ShohinDomain;
import com.showka.domain.ShohinIdoMeisaiDomain;
import com.showka.domain.builder.ShohinIdoMeisaiDomainBuilder;
import com.showka.entity.TShohinIdoMeisai;
import com.showka.entity.TShohinIdoMeisaiPK;
import com.showka.repository.i.TShohinIdoMeisaiRepository;
import com.showka.service.crud.MeisaiCrudService;
import com.showka.service.crud.z00.i.MShohinCrudService;

@Service
public class ShohinIdoMeisaiCrudService implements MeisaiCrudService<ShohinIdoMeisaiDomain, TShohinIdoMeisaiPK> {

	@Autowired
	private TShohinIdoMeisaiRepository repo;

	@Autowired
	private MShohinCrudService shohinCrudService;

	@Override
	public void save(String id, ShohinIdoMeisaiDomain shohinIdoMeisai) {
		// domain -> entity
		TShohinIdoMeisaiPK pk = new TShohinIdoMeisaiPK();
		pk.setMeisaiNumber(shohinIdoMeisai.getMeisaiNumber());
		pk.setShohinIdoId(id);
		Optional<TShohinIdoMeisai> _e = repo.findById(pk);
		TShohinIdoMeisai entity = _e.isPresent() ? _e.get() : new TShohinIdoMeisai();
		if (!_e.isPresent()) {
			entity.setRecordId(UUID.randomUUID().toString());
		}
		entity.setNumber(shohinIdoMeisai.getNumber());
		entity.setPk(pk);
		entity.setShohinId(shohinIdoMeisai.getShohinDomain().getRecordId());
		entity.setVersion(shohinIdoMeisai.getVersion());
		// save
		repo.save(entity);
	}

	@Override
	public void delete(TShohinIdoMeisaiPK pk, int version) {
		TShohinIdoMeisai entity = repo.getOne(pk);
		entity.setVersion(version);
		repo.delete(entity);
	}

	@Override
	public void deleteAll(String id) {
		Iterable<TShohinIdoMeisai> entities = getMeisaiList(id);
		repo.deleteAll(entities);
	}

	@Override
	public void overrideList(String id, List<ShohinIdoMeisaiDomain> meisaiList) {
		if (meisaiList.isEmpty()) {
			deleteAll(id);
			return;
		}
		// delete removed
		List<ShohinIdoMeisaiDomain> oldList = getDomainList(id);
		oldList.stream().filter(o -> {
			return !meisaiList.contains(o);
		}).forEach(o -> {
			TShohinIdoMeisaiPK _id = new TShohinIdoMeisaiPK();
			_id.setMeisaiNumber(o.getMeisaiNumber());
			_id.setShohinIdoId(o.getShohinDomain().getRecordId());
			delete(_id, o.getVersion());
		});
		// save
		meisaiList.forEach(m -> save(id, m));
	}

	@Override
	public ShohinIdoMeisaiDomain getDomain(TShohinIdoMeisaiPK pk) {
		TShohinIdoMeisai entity = repo.getOne(pk);
		return buildDomain(entity);
	}

	@Override
	public List<ShohinIdoMeisaiDomain> getDomainList(String id) {
		List<TShohinIdoMeisai> entities = getMeisaiList(id);
		return entities.stream().map(e -> {
			return buildDomain(e);
		}).collect(Collectors.toList());
	}

	/**
	 * エンティティのリストを取得.
	 * 
	 * @param id
	 * @return
	 */
	private List<TShohinIdoMeisai> getMeisaiList(String id) {
		TShohinIdoMeisai entity = new TShohinIdoMeisai();
		TShohinIdoMeisaiPK pk = new TShohinIdoMeisaiPK();
		pk.setShohinIdoId(id);
		entity.setPk(pk);
		Example<TShohinIdoMeisai> example = Example.of(entity);
		List<TShohinIdoMeisai> entities = repo.findAll(example);
		return entities;
	}

	/**
	 * Entity -> Domain
	 * 
	 * @param entity
	 *            商品移動明細Entity
	 * @return domain
	 */
	private ShohinIdoMeisaiDomain buildDomain(TShohinIdoMeisai entity) {
		ShohinDomain shohinDomain = shohinCrudService.getDomain(entity.getShohin().getCode());
		ShohinIdoMeisaiDomainBuilder b = new ShohinIdoMeisaiDomainBuilder();
		b.withMeisaiNumber(entity.getPk().getMeisaiNumber());
		b.withNumber(entity.getNumber());
		b.withRecordId(entity.getRecordId());
		b.withShohinDomain(shohinDomain);
		b.withVersion(entity.getVersion());
		return b.build();
	}
}
