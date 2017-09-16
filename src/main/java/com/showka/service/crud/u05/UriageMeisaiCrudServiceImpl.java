package com.showka.service.crud.u05;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.ShohinDomain;
import com.showka.domain.UriageMeisaiDomain;
import com.showka.domain.builder.UriageMeisaiDomainBuilder;
import com.showka.entity.TUriageMeisai;
import com.showka.entity.TUriageMeisaiPK;
import com.showka.repository.i.TUriageMeisaiRepository;
import com.showka.service.crud.u05.i.UriageMeisaiCrudService;
import com.showka.service.crud.z00.i.MShohinCrudService;

/**
 * 売上明細CrudeService
 * 
 * @author ShowKa
 *
 */
@Service
public class UriageMeisaiCrudServiceImpl implements UriageMeisaiCrudService {

	/**
	 * 売上明細リポジトリ.
	 */
	@Autowired
	private TUriageMeisaiRepository repo;

	/**
	 * 商品なマスタCRUDサービス.
	 */
	@Autowired
	private MShohinCrudService shohinService;

	/**
	 * ドメイン保存.
	 */
	@Override
	public void save(UriageMeisaiDomain domain) {
		// set primary key
		TUriageMeisaiPK pk = new TUriageMeisaiPK();
		pk.setUriageId(domain.getUriageId());
		pk.setMeisaiNumber(domain.getMeisaiNumber());

		// get entity
		TUriageMeisai e = repo.findById(pk).orElse(new TUriageMeisai());

		// override entity
		e.setPk(pk);
		e.setHanbaiNumber(domain.getHanbaiNumber());
		e.setHanbaiTanka(domain.getHanbaiTanka().intValue());
		e.setRecordId(domain.getRecordId());
		e.setShohinId(domain.getShohinDomain().getRecordId());
		e.setVersion(e.getVersion());

		// save
		repo.save(e);
	}

	/**
	 * 削除
	 */
	@Override
	public void delete(TUriageMeisaiPK pk, Integer version) {
		TUriageMeisai entity = new TUriageMeisai();
		entity.setPk(pk);
		entity.setVersion(version);
		repo.delete(entity);
	}

	/**
	 * ドメイン取得
	 */
	@Override
	public UriageMeisaiDomain getDomain(TUriageMeisaiPK pk) {

		// get entity
		TUriageMeisai e = repo.findById(pk).get();

		// get shohin domain
		ShohinDomain shohin = shohinService.getDomain(e.getShohin().getCode());

		// set builder
		UriageMeisaiDomainBuilder b = new UriageMeisaiDomainBuilder();
		b.withHanbaiNumber(e.getHanbaiNumber());
		b.withHanbaiTanka(BigDecimal.valueOf(e.getHanbaiTanka()));
		b.withMeisaiNumber(e.getPk().getMeisaiNumber());
		b.withRecordId(e.getRecordId());
		b.withShohinDomain(shohin);
		b.withUriageId(e.getPk().getUriageId());
		b.withVersion(e.getVersion());

		// build domain
		UriageMeisaiDomain d = b.build();
		return d;
	}

	/**
	 * 存在チェック.
	 */
	@Override
	public boolean exsists(TUriageMeisaiPK pk) {
		return repo.findById(pk).isPresent();
	}

}
