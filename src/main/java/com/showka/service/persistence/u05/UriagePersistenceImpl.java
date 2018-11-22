package com.showka.service.persistence.u05;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.builder.UriageBuilder;
import com.showka.domain.u01.Kokyaku;
import com.showka.domain.u05.Uriage;
import com.showka.domain.u05.UriageMeisai;
import com.showka.domain.u05.UriageRireki;
import com.showka.entity.RUriagePK;
import com.showka.entity.TUriage;
import com.showka.entity.TUriageMeisai;
import com.showka.entity.TUriageMeisaiPK;
import com.showka.entity.TUriagePK;
import com.showka.kubun.HanbaiKubun;
import com.showka.kubun.i.Kubun;
import com.showka.repository.i.TUriageRepository;
import com.showka.service.persistence.u01.i.KokyakuPersistence;
import com.showka.service.persistence.u05.i.UriageCancelPersistence;
import com.showka.service.persistence.u05.i.UriageMeisaiPersistence;
import com.showka.service.persistence.u05.i.UriagePersistence;
import com.showka.service.persistence.u05.i.UriageRirekiPersistence;
import com.showka.value.EigyoDate;
import com.showka.value.TaxRate;

@Service
public class UriagePersistenceImpl implements UriagePersistence {

	@Autowired
	private TUriageRepository repo;

	@Autowired
	private UriageMeisaiPersistence uriageMeisaiPersistence;

	@Autowired
	private KokyakuPersistence kokyakuPersistence;

	@Autowired
	private UriageRirekiPersistence uriageRirekiPersistence;

	@Autowired
	private UriageCancelPersistence uriageCancelPersistence;

	@Override
	public void save(Uriage domain) {
		// entity
		TUriage e = getEntityFromDomain(domain);
		// save
		repo.saveAndFlush(e);
		// save 履歴
		uriageRirekiPersistence.save(domain);
		// 明細更新
		uriageMeisaiPersistence.overrideList(domain.getRecordId(), domain.getUriageMeisai());
	}

	@Override
	public Uriage getDomain(TUriagePK pk) {
		// get
		TUriage e = repo.findById(pk).get();
		return getDomainFromEntity(e);
	}

	@Override
	public boolean exsists(TUriagePK pk) {
		return repo.existsById(pk);
	}

	@Override
	public void delete(Uriage domain) {
		TUriagePK pk = new TUriagePK();
		pk.setDenpyoNumber(domain.getDenpyoNumber());
		pk.setKokyakuId(domain.getKokyaku().getRecordId());
		Integer version = domain.getVersion();
		// OCC
		TUriage entity = repo.getOne(pk);
		entity.setVersion(version);
		// 売上履歴削除
		uriageRirekiPersistence.delete(new RUriagePK(entity.getRecordId(), entity.getKeijoDate()));
		String uriageId = domain.getRecordId();
		domain.getUriageMeisai().forEach(meisai -> {
			TUriageMeisaiPK mpk = new TUriageMeisaiPK();
			mpk.setUriageId(uriageId);
			mpk.setMeisaiNumber(meisai.getMeisaiNumber());
			uriageMeisaiPersistence.delete(mpk, meisai.getVersion());
		});
		// 売上削除
		repo.deleteById(pk);
	}

	@Override
	public void cancel(TUriagePK pk, int version) {
		// occ
		TUriage e = repo.getOne(pk);
		e.setVersion(version);
		// update 計上日
		Uriage _d = this.getDomainFromEntity(e);
		UriageBuilder b = new UriageBuilder();
		EigyoDate eigyoDate = _d.getKokyaku().getShukanBusho().getEigyoDate();
		b.withKeijoDate(eigyoDate);
		// empty 売上明細
		b.withUriageMeisai(new ArrayList<>());
		// save 売上
		Uriage domain = b.apply(_d);
		this.save(domain);
		// cancel
		uriageCancelPersistence.save(domain);
	}

	@Override
	public void revert(TUriagePK pk, int version) {
		// OCC
		TUriage entity = repo.getOne(pk);
		entity.setVersion(version);
		// 売上履歴削除
		RUriagePK rpk = new RUriagePK(entity.getRecordId(), entity.getKeijoDate());
		uriageRirekiPersistence.delete(rpk);
		// 残った履歴取得
		String uriageId = entity.getRecordId();
		UriageRireki rirekiList = uriageRirekiPersistence.getUriageRirekiList(uriageId);
		// 計上済みの履歴で売上を上書きし直す。
		Uriage domainForOverride = rirekiList.getNewest();
		domainForOverride.setVersion(version);
		this.save(domainForOverride);
	}

	@Override
	public Uriage getDomain(String kokyakuCode, String denpyoNumber) {
		Kokyaku kokyaku = kokyakuPersistence.getDomain(kokyakuCode);
		TUriagePK pk = new TUriagePK();
		pk.setKokyakuId(kokyaku.getRecordId());
		pk.setDenpyoNumber(denpyoNumber);
		return this.getDomain(pk);
	}

	/**
	 * Domain -> Entity
	 * 
	 * @param domain
	 *            売上ドメイン
	 * @return 売上エンティティ
	 */
	private TUriage getEntityFromDomain(Uriage domain) {
		// pk
		TUriagePK pk = new TUriagePK();
		pk.setDenpyoNumber(domain.getDenpyoNumber());
		pk.setKokyakuId(domain.getKokyaku().getRecordId());

		// 売上Entity
		Optional<TUriage> _e = repo.findById(pk);
		TUriage e = _e.isPresent() ? _e.get() : new TUriage();
		e.setHanbaiKubun(domain.getHanbaiKubun().getCode());
		e.setPk(pk);
		e.setShohizeiritsu(domain.getShohizeiritsu().getRate().doubleValue());
		e.setUriageDate(domain.getUriageDate().toDate());
		e.setKeijoDate(domain.getKeijoDate().toDate());
		// record id
		String recordId = _e.isPresent() ? e.getRecordId() : UUID.randomUUID().toString();
		e.setRecordId(recordId);
		domain.setRecordId(recordId);
		// occ
		e.setVersion(domain.getVersion());
		return e;
	}

	/**
	 * Entityからドメインを生成します.
	 * 
	 * @param e
	 *            Entity
	 * @return 売上ドメイン
	 */
	private Uriage getDomainFromEntity(TUriage e) {
		// 顧客
		Kokyaku kokyaku = kokyakuPersistence.getDomain(e.getKokyaku().getCode());
		// 売上明細ドメイン
		List<UriageMeisai> uriageMeisai = new ArrayList<UriageMeisai>();
		List<TUriageMeisai> meisaiEntityList = e.getMeisai();
		for (TUriageMeisai m : meisaiEntityList) {
			uriageMeisai.add(uriageMeisaiPersistence.getDomain(m.getPk()));
		}
		// sort
		Collections.sort(uriageMeisai);
		// set builder
		UriageBuilder b = new UriageBuilder();
		b.withDenpyoNumber(e.getPk().getDenpyoNumber());
		b.withHanbaiKubun(Kubun.get(HanbaiKubun.class, e.getHanbaiKubun()));
		b.withKokyaku(kokyaku);
		b.withRecordId(e.getRecordId());
		b.withShohizeiritsu(new TaxRate(e.getShohizeiritsu()));
		b.withUriageDate(new EigyoDate(e.getUriageDate()));
		b.withKeijoDate(new EigyoDate(e.getKeijoDate()));
		b.withUriageMeisai(uriageMeisai);
		b.withVersion(e.getVersion());
		return b.build();
	}
}