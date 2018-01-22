package com.showka.service.crud.u05;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
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
		TUriageMeisai entity = repo.getOne(pk);
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

	@Override
	public void delete(UriageMeisaiDomain domain) {
		TUriageMeisaiPK pk = new TUriageMeisaiPK();
		pk.setUriageId(domain.getUriageId());
		pk.setMeisaiNumber(domain.getMeisaiNumber());
		this.delete(pk, domain.getVersion());
	}

	@Override
	public List<UriageMeisaiDomain> getDomainList(String uriageId) {
		// 明細
		List<TUriageMeisai> meisaiList = getUriageMeisaiList(uriageId);

		// ドメイン取得
		List<UriageMeisaiDomain> meisaiDomainList = new ArrayList<UriageMeisaiDomain>();
		for (TUriageMeisai meisai : meisaiList) {
			UriageMeisaiDomain md = this.getDomain(meisai.getPk());
			meisaiDomainList.add(md);
		}
		return meisaiDomainList;
	}

	@Override
	public Integer getMaxMeisaiNumber(String uriageId) {
		// 明細
		List<TUriageMeisai> meisaiList = getUriageMeisaiList(uriageId);
		Optional<TUriageMeisai> max = meisaiList.stream().max((m1, m2) -> {
			return m1.getPk().getMeisaiNumber().compareTo(m2.getPk().getMeisaiNumber());
		});
		return max.isPresent() ? max.get().getPk().getMeisaiNumber() : 0;
	}

	/**
	 * 売上明細のEntityのリストを取得する。
	 * 
	 * @param uriageId
	 * @return 売上明細Entityのリスト
	 */
	private List<TUriageMeisai> getUriageMeisaiList(String uriageId) {
		// 売上IDで検索
		TUriageMeisai e = new TUriageMeisai();
		TUriageMeisaiPK pk = new TUriageMeisaiPK();
		pk.setUriageId(uriageId);
		e.setPk(pk);
		Example<TUriageMeisai> example = Example.of(e);

		// 明細
		return repo.findAll(example);
	}

	@Override
	public void deleteAll(String uriageId) {
		List<TUriageMeisai> meisaiList = getUriageMeisaiList(uriageId);
		repo.deleteAll(meisaiList);
	}
}
