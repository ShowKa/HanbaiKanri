package com.showka.service.crud.u07;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.showka.domain.builder.SeikyuMeisaiBuilder;
import com.showka.domain.u06.Urikake;
import com.showka.domain.u07.SeikyuMeisai;
import com.showka.entity.TSeikyuMeisai;
import com.showka.entity.TSeikyuMeisaiPK;
import com.showka.repository.i.TSeikyuMeisaiRepository;
import com.showka.service.crud.u06.i.UrikakeCrud;
import com.showka.service.crud.u07.i.SeikyuMeisaiCrud;
import com.showka.value.AmountOfMoney;

@Service
public class SeikyuMeisaiCrudImpl implements SeikyuMeisaiCrud {

	@Autowired
	private TSeikyuMeisaiRepository repo;

	@Autowired
	private UrikakeCrud urikakePersistence;

	@Override
	public void save(String seikyuId, SeikyuMeisai domain) {
		// pk
		TSeikyuMeisaiPK pk = new TSeikyuMeisaiPK();
		pk.setSeikyuId(seikyuId);
		pk.setUrikakeId(domain.getUrikakeId());
		// get entity
		Optional<TSeikyuMeisai> _e = repo.findById(pk);
		TSeikyuMeisai e = _e.orElse(new TSeikyuMeisai());
		// set columns
		e.setPk(pk);
		e.setKingaku(domain.getKingaku().intValue());
		// record id
		e.setRecordId(domain.getRecordId());
		// save without OCC
		repo.save(e);
	}

	@Override
	public void delete(TSeikyuMeisaiPK pk, int version) {
		// entity
		TSeikyuMeisai e = repo.getOne(pk);
		// OCC
		e.setVersion(version);
		// delete
		repo.delete(e);
	}

	@Override
	public SeikyuMeisai getDomain(TSeikyuMeisaiPK pk) {
		// entitu
		TSeikyuMeisai e = repo.getOne(pk);
		// 売掛
		Urikake urikake = urikakePersistence.getDomainById(e.getUrikakeId());
		// build
		SeikyuMeisaiBuilder b = new SeikyuMeisaiBuilder();
		b.withKingaku(new AmountOfMoney(e.getKingaku()));
		b.withUrikake(urikake);
		b.withRecordId(e.getRecordId());
		b.withVersion(e.getVersion());
		return b.build();
	}

	@Override
	public List<SeikyuMeisai> getDomainList(String seikyuId) {
		List<TSeikyuMeisai> entities = this.getSeikyuMeisaiList(seikyuId);
		return entities.stream().map(_e -> {
			return this.getDomain(_e.getPk());
		}).collect(Collectors.toList());
	}

	@Override
	public void deleteAll(String seikyuId) {
		List<TSeikyuMeisai> entities = this.getSeikyuMeisaiList(seikyuId);
		repo.deleteAll(entities);
	}

	@Override
	public void overrideList(String seikyuId, List<SeikyuMeisai> meisaiList) {
		// delete all if empty
		if (meisaiList.isEmpty()) {
			this.deleteAll(seikyuId);
			return;
		}
		// delete removed 明細
		List<SeikyuMeisai> oldList = this.getDomainList(seikyuId);
		oldList.stream().filter(o -> {
			return !meisaiList.contains(o);
		}).forEach(o -> {
			TSeikyuMeisaiPK pk = new TSeikyuMeisaiPK();
			pk.setSeikyuId(seikyuId);
			pk.setUrikakeId(o.getUrikakeId());
			this.delete(pk, o.getVersion());
		});
		// save
		meisaiList.forEach(m -> this.save(seikyuId, m));
	}

	// private methods
	/**
	 * 請求明細のEntityを取得.
	 * 
	 * @param seikyuId
	 *            請求ID
	 * @return entities
	 */
	private List<TSeikyuMeisai> getSeikyuMeisaiList(String seikyuId) {
		TSeikyuMeisaiPK pk = new TSeikyuMeisaiPK();
		pk.setSeikyuId(seikyuId);
		TSeikyuMeisai e = new TSeikyuMeisai();
		e.setPk(pk);
		Example<TSeikyuMeisai> example = Example.of(e);
		List<TSeikyuMeisai> entities = repo.findAll(example);
		return entities;
	}
}
