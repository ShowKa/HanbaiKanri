package com.showka.service.crud.u05;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.showka.domain.ShohinDomain;
import com.showka.domain.UriageRirekiMeisaiDomain;
import com.showka.domain.builder.UriageRirekiMeisaiDomainBuilder;
import com.showka.entity.RUriageMeisai;
import com.showka.entity.RUriageMeisaiPK;
import com.showka.repository.i.RUriageMeisaiRepository;
import com.showka.service.crud.u05.i.UriageRirekiMeisaiCrudService;
import com.showka.service.crud.z00.i.MShohinCrudService;

@Service
public class UriageRirekiMeisaiCrudServiceImpl implements UriageRirekiMeisaiCrudService {

	@Autowired
	private RUriageMeisaiRepository repo;

	@Autowired
	private MShohinCrudService shohinCrudService;

	@Override
	public void save(UriageRirekiMeisaiDomain domain) {
		// set pk
		RUriageMeisaiPK pk = new RUriageMeisaiPK();
		pk.setUriageId(domain.getUriageId());
		pk.setMeisaiNumber(domain.getMeisaiNumber());

		// get entity
		RUriageMeisai e = repo.findById(pk).orElse(new RUriageMeisai());

		// override entity
		e.setPk(pk);
		e.setHanbaiNumber(domain.getHanbaiNumber());
		e.setHanbaiTanka(domain.getHanbaiTanka().intValue());
		e.setRecordId(domain.getRecordId());
		e.setShohinId(domain.getShohinDomain().getRecordId());

		// optimistic lock
		e.setVersion(domain.getVersion());

		// save
		repo.save(e);
	}

	@Override
	public void overrideList(List<UriageRirekiMeisaiDomain> meisaiList) {
		// delete removed
		List<UriageRirekiMeisaiDomain> oldList = getDomainList(meisaiList.get(0).getUriageId());
		oldList.stream().filter(o -> {
			return !meisaiList.contains(o);
		}).forEach(o -> {
			delete(o);
		});

		// save
		meisaiList.forEach(m -> save(m));
	}

	@Override
	public void delete(UriageRirekiMeisaiDomain domain) {
		RUriageMeisaiPK pk = new RUriageMeisaiPK();
		pk.setUriageId(domain.getUriageId());
		pk.setMeisaiNumber(domain.getMeisaiNumber());
		Integer version = domain.getVersion();
		this.delete(pk, version);
	}

	@Override
	public void delete(RUriageMeisaiPK pk, Integer version) {
		RUriageMeisai entity = repo.getOne(pk);
		entity.setVersion(version);
		repo.delete(entity);
	}

	@Override
	public UriageRirekiMeisaiDomain getDomain(RUriageMeisaiPK pk) {
		RUriageMeisai e = repo.getOne(pk);
		return buildDomainByEntity(e);
	}

	@Override
	public List<UriageRirekiMeisaiDomain> getDomainList(String uriageId) {
		// search
		RUriageMeisaiPK pk = new RUriageMeisaiPK();
		pk.setUriageId(uriageId);
		RUriageMeisai ex = new RUriageMeisai();
		ex.setPk(pk);
		Example<RUriageMeisai> example = Example.of(ex);

		// build domain list
		List<RUriageMeisai> entities = repo.findAll(example);
		List<UriageRirekiMeisaiDomain> list = new ArrayList<UriageRirekiMeisaiDomain>();
		entities.forEach(e -> list.add(buildDomainByEntity(e)));
		return list;
	}

	@Override
	public boolean exsists(RUriageMeisaiPK pk) {
		return repo.existsById(pk);
	}

	/**
	 * entityからdomainをbuildします.
	 * 
	 * @param e
	 *            entity
	 * @return ドメイン
	 * 
	 */
	private UriageRirekiMeisaiDomain buildDomainByEntity(RUriageMeisai e) {
		// get 商品
		ShohinDomain shohinDomain = shohinCrudService.getDomain(e.getShohin().getCode());

		// build domain
		UriageRirekiMeisaiDomainBuilder b = new UriageRirekiMeisaiDomainBuilder();
		b.withHanbaiNumber(e.getHanbaiNumber());
		b.withHanbaiTanka(BigDecimal.valueOf(e.getHanbaiTanka()));
		b.withMeisaiNumber(e.getPk().getMeisaiNumber());
		b.withRecordId(e.getRecordId());
		b.withShohinDomain(shohinDomain);
		b.withUriageId(e.getPk().getUriageId());
		b.withVersion(e.getVersion());
		return b.build();
	}
}
