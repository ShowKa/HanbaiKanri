package com.showka.service.crud.u05;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.showka.domain.builder.UriageMeisaiBuilder;
import com.showka.domain.u05.UriageMeisai;
import com.showka.domain.z00.Shohin;
import com.showka.entity.RUriageMeisai;
import com.showka.entity.RUriageMeisaiPK;
import com.showka.repository.i.RUriageMeisaiRepository;
import com.showka.service.crud.u05.i.UriageRirekiMeisaiCrud;
import com.showka.service.crud.z00.i.ShohinCrud;

@Service
public class UriageRirekiMeisaiCrudImpl implements UriageRirekiMeisaiCrud {

	@Autowired
	private RUriageMeisaiRepository repo;

	@Autowired
	private ShohinCrud shohinPersistence;

	@Override
	public void save(String uriageId, UriageMeisai domain) {
		// set pk
		RUriageMeisaiPK pk = new RUriageMeisaiPK();
		pk.setUriageId(uriageId);
		pk.setMeisaiNumber(domain.getMeisaiNumber());
		// get entity
		Optional<RUriageMeisai> _e = repo.findById(pk);
		RUriageMeisai e = _e.orElse(new RUriageMeisai());
		// override entity
		e.setPk(pk);
		e.setHanbaiNumber(domain.getHanbaiNumber());
		e.setHanbaiTanka(domain.getHanbaiTanka().intValue());
		e.setShohinId(domain.getShohinDomain().getRecordId());
		// set record id
		String recordId = _e.isPresent() ? e.getRecordId() : UUID.randomUUID().toString();
		e.setRecordId(recordId);
		// save
		repo.save(e);
	}

	@Override
	public void delete(RUriageMeisaiPK pk, int version) {
		RUriageMeisai entity = repo.getOne(pk);
		// occ
		entity.setVersion(version);
		repo.delete(entity);
	}

	@Override
	public void overrideList(String uriageId, List<UriageMeisai> meisaiList) {
		// delete all if empty
		if (meisaiList.isEmpty()) {
			this.deleteAll(uriageId);
			return;
		}
		// delete removed 明細
		List<UriageMeisai> oldList = getDomainList(uriageId);
		oldList.stream().filter(o -> {
			return !meisaiList.contains(o);
		}).forEach(o -> {
			RUriageMeisaiPK pk = new RUriageMeisaiPK();
			pk.setUriageId(uriageId);
			pk.setMeisaiNumber(o.getMeisaiNumber());
			this.delete(pk, o.getVersion());
		});
		// save
		meisaiList.forEach(m -> save(uriageId, m));
	}

	@Override
	public UriageMeisai getDomain(RUriageMeisaiPK pk) {
		RUriageMeisai e = repo.getOne(pk);
		return buildDomainByEntity(e);
	}

	@Override
	public List<UriageMeisai> getDomainList(String uriageId) {
		List<RUriageMeisai> entities = getUriageMeisaiList(uriageId);
		List<UriageMeisai> list = new ArrayList<UriageMeisai>();
		entities.forEach(e -> list.add(buildDomainByEntity(e)));
		Collections.sort(list);
		return list;
	}

	@Override
	public void deleteAll(String uriageId) {
		List<RUriageMeisai> meisaiList = getUriageMeisaiList(uriageId);
		repo.deleteAll(meisaiList);
	}

	/**
	 * 履歴売上明細のEntityのリストを取得する。
	 * 
	 * @param uriageId
	 * @return 履歴売上明細Entityのリスト
	 */
	private List<RUriageMeisai> getUriageMeisaiList(String uriageId) {
		// search
		RUriageMeisaiPK pk = new RUriageMeisaiPK();
		pk.setUriageId(uriageId);
		RUriageMeisai ex = new RUriageMeisai();
		ex.setPk(pk);
		Example<RUriageMeisai> example = Example.of(ex);
		// build domain list
		return repo.findAll(example);
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
		Shohin shohinDomain = shohinPersistence.getDomain(e.getShohin().getCode());
		// build domain
		UriageMeisaiBuilder b = new UriageMeisaiBuilder();
		b.withHanbaiNumber(e.getHanbaiNumber());
		b.withHanbaiTanka(BigDecimal.valueOf(e.getHanbaiTanka()));
		b.withMeisaiNumber(e.getPk().getMeisaiNumber());
		b.withRecordId(e.getRecordId());
		b.withShohinDomain(shohinDomain);
		b.withVersion(e.getVersion());
		return b.build();
	}
}
