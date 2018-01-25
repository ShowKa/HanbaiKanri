package com.showka.service.crud.u05;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.KokyakuDomain;
import com.showka.domain.UriageDomain;
import com.showka.domain.UriageMeisaiDomain;
import com.showka.domain.UriageRirekiListDomain;
import com.showka.domain.builder.UriageDomainBuilder;
import com.showka.entity.MKokyaku;
import com.showka.entity.TUriage;
import com.showka.entity.TUriageMeisai;
import com.showka.entity.TUriagePK;
import com.showka.kubun.HanbaiKubun;
import com.showka.kubun.i.Kubun;
import com.showka.repository.i.MKokyakuRepository;
import com.showka.repository.i.TUriageMeisaiRepository;
import com.showka.repository.i.TUriageRepository;
import com.showka.service.crud.u01.i.KokyakuCrudService;
import com.showka.service.crud.u05.i.UriageCancelCrudService;
import com.showka.service.crud.u05.i.UriageCrudService;
import com.showka.service.crud.u05.i.UriageMeisaiCrudService;
import com.showka.service.crud.u05.i.UriageRirekiCrudService;
import com.showka.value.TaxRate;
import com.showka.value.TheDate;

@Service
public class UriageCrudServiceImpl implements UriageCrudService {

	@Autowired
	private TUriageRepository repo;

	@Autowired
	private MKokyakuRepository kokyakuRepo;

	@Autowired
	private TUriageMeisaiRepository meisaiRepository;

	@Autowired
	private UriageMeisaiCrudService uriageMeisaiCrudService;

	@Autowired
	private KokyakuCrudService kokyakuCrudService;

	@Autowired
	private UriageRirekiCrudService uriageRirekiCrudService;

	@Autowired
	private UriageCancelCrudService uriageCancelCrudService;

	@Override
	public void save(UriageDomain domain) {

		// entity
		TUriage e = getEntityFromDomain(domain);

		// save
		repo.saveAndFlush(e);

		// save 履歴
		uriageRirekiCrudService.save(domain);

		// 明細更新
		uriageMeisaiCrudService.overrideList(domain.getUriageMeisai());

		// set 顧客
		MKokyaku kokyaku = kokyakuRepo.findByRecordId(domain.getKokyaku().getRecordId());
		e.setKokyaku(kokyaku);

		// set 明細
		List<TUriageMeisai> m = new ArrayList<TUriageMeisai>();
		for (UriageMeisaiDomain meisai : domain.getUriageMeisai()) {
			m.add(meisaiRepository.findByRecordId(meisai.getRecordId()));
		}
		e.setMeisai(m);
	}

	@Override
	public void delete(TUriagePK pk, Integer version) {
		TUriage entity = repo.getOne(pk);
		entity.setPk(pk);
		entity.setVersion(version);
		repo.delete(entity);
	}

	@Override
	public UriageDomain getDomain(TUriagePK pk) {
		// get
		TUriage e = repo.findById(pk).get();

		// 顧客ドメイン
		KokyakuDomain kokyaku = kokyakuCrudService.getDomain(e.getKokyaku().getCode());

		// 売上明細ドメイン
		List<UriageMeisaiDomain> uriageMeisai = new ArrayList<UriageMeisaiDomain>();
		List<TUriageMeisai> meisaiEntityList = e.getMeisai();
		for (TUriageMeisai m : meisaiEntityList) {
			uriageMeisai.add(uriageMeisaiCrudService.getDomain(m.getPk()));
		}

		// set builder
		UriageDomainBuilder b = new UriageDomainBuilder();
		b.withDenpyoNumber(e.getPk().getDenpyoNumber());
		b.withHanbaiKubun(Kubun.get(HanbaiKubun.class, e.getHanbaiKubun()));
		b.withKokyaku(kokyaku);
		b.withRecordId(e.getRecordId());
		b.withShohizeiritsu(new TaxRate(e.getShohizeiritsu()));
		b.withUriageDate(new TheDate(e.getUriageDate()));
		b.withKeijoDate(new TheDate(e.getKeijoDate()));
		b.withUriageMeisai(uriageMeisai);
		b.withVersion(e.getVersion());

		return b.build();
	}

	@Override
	public boolean exsists(TUriagePK pk) {
		return repo.existsById(pk);
	}

	@Override
	public void delete(UriageDomain domain) {
		// 売上履歴削除
		uriageRirekiCrudService.delete(domain);

		// 残った履歴取得
		String uriageId = domain.getRecordId();
		UriageRirekiListDomain rirekiList = uriageRirekiCrudService.getUriageRirekiList(uriageId);

		// 計上済みの履歴があれば、それで売上を上書きし直す。
		// ない場合は、売上データ自体をすべて削除して終了。
		if (rirekiList.getList().size() > 0) {
			UriageDomain domainForOverride = rirekiList.getNewest();
			UriageDomainBuilder b = new UriageDomainBuilder();
			// ここで排他制御
			b.withVersion(domain.getVersion());
			this.save(b.apply(domainForOverride));
		} else {
			// 明細削除
			domain.getUriageMeisai().forEach(meisai -> {
				uriageMeisaiCrudService.delete(meisai);
			});
			// 売上削除
			TUriagePK pk = new TUriagePK();
			pk.setDenpyoNumber(domain.getDenpyoNumber());
			pk.setKokyakuId(domain.getKokyaku().getRecordId());
			this.delete(pk, domain.getVersion());
		}
	}

	@Override
	public void cancel(UriageDomain domain) {
		// delete meisai if exists
		uriageMeisaiCrudService.deleteAll(domain.getRecordId());

		// save entity
		TUriage e = getEntityFromDomain(domain);
		repo.saveAndFlush(e);

		// cancel 履歴
		uriageRirekiCrudService.cancel(domain);

		// cancel
		uriageCancelCrudService.save(domain);
	}

	/**
	 * Domain -> Entity
	 * 
	 * @param domain
	 *            売上ドメイン
	 * @return 売上エンティティ
	 */
	private TUriage getEntityFromDomain(UriageDomain domain) {
		// pk
		TUriagePK pk = new TUriagePK();
		pk.setDenpyoNumber(domain.getDenpyoNumber());
		pk.setKokyakuId(domain.getKokyaku().getRecordId());

		// 売上Entity
		TUriage e = repo.findById(pk).orElse(new TUriage());
		e.setHanbaiKubun(domain.getHanbaiKubun().getCode());
		e.setPk(pk);
		e.setShohizeiritsu(domain.getShohizeiritsu().getRate().doubleValue());
		e.setUriageDate(domain.getUriageDate().toDate());
		e.setKeijoDate(domain.getKeijoDate().toDate());
		e.setRecordId(domain.getRecordId());
		e.setVersion(domain.getVersion());
		return e;
	}

}
