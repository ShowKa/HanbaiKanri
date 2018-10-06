package com.showka.service.crud.u05;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.showka.domain.builder.UriageMeisaiBuilder;
import com.showka.domain.u05.UriageMeisai;
import com.showka.domain.z00.Shohin;
import com.showka.entity.TUriageMeisai;
import com.showka.entity.TUriageMeisaiPK;
import com.showka.repository.i.TUriageMeisaiRepository;
import com.showka.service.crud.u05.i.UriageMeisaiCrudService;
import com.showka.service.crud.z00.i.ShohinCrudService;

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
	private ShohinCrudService shohinService;

	/**
	 * ドメイン保存.
	 */
	@Override
	public void save(String uriageId, UriageMeisai domain) {
		// set primary key
		TUriageMeisaiPK pk = new TUriageMeisaiPK();
		pk.setUriageId(uriageId);
		pk.setMeisaiNumber(domain.getMeisaiNumber());
		// get entity
		Optional<TUriageMeisai> _e = repo.findById(pk);
		TUriageMeisai e = _e.isPresent() ? _e.get() : new TUriageMeisai();
		// override entity
		e.setPk(pk);
		e.setHanbaiNumber(domain.getHanbaiNumber());
		e.setHanbaiTanka(domain.getHanbaiTanka().intValue());
		e.setRecordId(domain.getRecordId());
		e.setShohinId(domain.getShohinDomain().getRecordId());
		// record id
		String recordId = _e.isPresent() ? e.getRecordId() : UUID.randomUUID().toString();
		e.setRecordId(recordId);
		domain.setRecordId(e.getRecordId());
		// save
		repo.save(e);
	}

	/**
	 * 削除
	 */
	@Override
	public void delete(TUriageMeisaiPK pk, int version) {
		TUriageMeisai entity = repo.getOne(pk);
		entity.setPk(pk);
		// occ
		entity.setVersion(version);
		repo.delete(entity);
	}

	/**
	 * ドメイン取得
	 */
	@Override
	public UriageMeisai getDomain(TUriageMeisaiPK pk) {
		// get entity
		TUriageMeisai e = repo.findById(pk).get();
		// get shohin domain
		Shohin shohin = shohinService.getDomain(e.getShohin().getCode());
		// set builder
		UriageMeisaiBuilder b = new UriageMeisaiBuilder();
		b.withHanbaiNumber(e.getHanbaiNumber());
		b.withHanbaiTanka(BigDecimal.valueOf(e.getHanbaiTanka()));
		b.withMeisaiNumber(e.getPk().getMeisaiNumber());
		b.withRecordId(e.getRecordId());
		b.withShohinDomain(shohin);
		b.withVersion(e.getVersion());
		// build domain
		return b.build();
	}

	@Override
	public List<UriageMeisai> getDomainList(String uriageId) {
		// 明細
		List<TUriageMeisai> meisaiList = getUriageMeisaiList(uriageId);
		// ドメイン取得
		List<UriageMeisai> meisaiDomainList = new ArrayList<UriageMeisai>();
		for (TUriageMeisai meisai : meisaiList) {
			UriageMeisai md = this.getDomain(meisai.getPk());
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

	@Override
	public void deleteAll(String uriageId) {
		List<TUriageMeisai> meisaiList = getUriageMeisaiList(uriageId);
		repo.deleteAll(meisaiList);
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
			TUriageMeisaiPK pk = new TUriageMeisaiPK();
			pk.setUriageId(uriageId);
			pk.setMeisaiNumber(o.getMeisaiNumber());
			delete(pk, o.getVersion());
		});
		// save
		meisaiList.forEach(m -> save(uriageId, m));
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
		// 明細取得
		return repo.findAll(example);
	}
}
