package com.showka.service.crud.u05;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.showka.domain.Shohin;
import com.showka.domain.UriageMeisai;
import com.showka.domain.UriageMeisai.UriageMeisaiComparatorByMeisaiNumber;
import com.showka.domain.builder.UriageMeisaiBuilder;
import com.showka.entity.RUriageMeisai;
import com.showka.entity.RUriageMeisaiPK;
import com.showka.repository.i.RUriageMeisaiRepository;
import com.showka.service.crud.u05.i.UriageRirekiMeisaiCrudService;
import com.showka.service.crud.z00.i.ShohinCrudService;

@Service
public class UriageRirekiMeisaiCrudServiceImpl implements UriageRirekiMeisaiCrudService {

	@Autowired
	private RUriageMeisaiRepository repo;

	@Autowired
	private ShohinCrudService shohinCrudService;

	@Override
	public void save(UriageMeisai domain) {
		// set pk
		RUriageMeisaiPK pk = new RUriageMeisaiPK();
		pk.setUriageId(domain.getUriageId());
		pk.setMeisaiNumber(domain.getMeisaiNumber());

		// get entity
		Optional<RUriageMeisai> _e = repo.findById(pk);
		RUriageMeisai e = _e.orElse(new RUriageMeisai());

		// override entity
		e.setPk(pk);
		e.setHanbaiNumber(domain.getHanbaiNumber());
		e.setHanbaiTanka(domain.getHanbaiTanka().intValue());
		e.setShohinId(domain.getShohinDomain().getRecordId());

		// set base column
		String recordId = _e.isPresent() ? _e.get().getRecordId() : UUID.randomUUID().toString();
		e.setRecordId(recordId);

		// no optimistic lock

		// save
		repo.save(e);
	}

	@Override
	public void overrideList(List<UriageMeisai> meisaiList) {
		if (meisaiList.isEmpty()) {
			// TODO 全削除したいが...
			return;
		}
		// delete removed
		List<UriageMeisai> oldList = getDomainList(meisaiList.get(0).getUriageId());
		oldList.stream().filter(o -> {
			return !meisaiList.contains(o);
		}).forEach(o -> {
			delete(o);
		});
		// save
		meisaiList.forEach(m -> save(m));
	}

	@Override
	public void delete(UriageMeisai domain) {
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
	public UriageMeisai getDomain(RUriageMeisaiPK pk) {
		RUriageMeisai e = repo.getOne(pk);
		return buildDomainByEntity(e);
	}

	@Override
	public List<UriageMeisai> getDomainList(String uriageId) {
		// search
		RUriageMeisaiPK pk = new RUriageMeisaiPK();
		pk.setUriageId(uriageId);
		RUriageMeisai ex = new RUriageMeisai();
		ex.setPk(pk);
		Example<RUriageMeisai> example = Example.of(ex);

		// build domain list
		List<RUriageMeisai> entities = repo.findAll(example);
		List<UriageMeisai> list = new ArrayList<UriageMeisai>();
		entities.forEach(e -> list.add(buildDomainByEntity(e)));
		list.sort(new UriageMeisaiComparatorByMeisaiNumber());
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
	private UriageMeisai buildDomainByEntity(RUriageMeisai e) {
		// get 商品
		Shohin shohinDomain = shohinCrudService.getDomain(e.getShohin().getCode());

		// build domain
		UriageMeisaiBuilder b = new UriageMeisaiBuilder();
		b.withHanbaiNumber(e.getHanbaiNumber());
		b.withHanbaiTanka(BigDecimal.valueOf(e.getHanbaiTanka()));
		b.withMeisaiNumber(e.getPk().getMeisaiNumber());
		b.withRecordId(e.getRecordId());
		b.withShohinDomain(shohinDomain);
		b.withUriageId(e.getPk().getUriageId());
		b.withVersion(e.getVersion());
		return b.build();
	}

	@Override
	public void deleteAll(String uriageId) {
		List<UriageMeisai> domains = getDomainList(uriageId);
		domains.forEach(d -> delete(d));
	}
}
