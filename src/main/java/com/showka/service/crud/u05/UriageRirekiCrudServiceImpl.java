package com.showka.service.crud.u05;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.showka.domain.KokyakuDomain;
import com.showka.domain.UriageDomain;
import com.showka.domain.UriageMeisaiDomain;
import com.showka.domain.UriageRirekiDomain;
import com.showka.domain.builder.UriageDomainBuilder;
import com.showka.domain.builder.UriageMeisaiDomainBuilder;
import com.showka.domain.builder.UriageRirekiDomainBuilder;
import com.showka.entity.RUriage;
import com.showka.entity.RUriagePK;
import com.showka.kubun.HanbaiKubun;
import com.showka.kubun.i.Kubun;
import com.showka.repository.i.RUriageRepository;
import com.showka.service.crud.u01.i.KokyakuCrudService;
import com.showka.service.crud.u05.i.UriageRirekiCrudService;
import com.showka.service.crud.u05.i.UriageRirekiMeisaiCrudService;
import com.showka.value.TaxRate;
import com.showka.value.TheDate;

@Service
public class UriageRirekiCrudServiceImpl implements UriageRirekiCrudService {

	@Autowired
	private RUriageRepository repo;

	@Autowired
	private KokyakuCrudService kokyakuCrudService;

	@Autowired
	private UriageRirekiMeisaiCrudService uriageRirekiMeisaiCrudService;

	@Override
	public UriageRirekiDomain getUriageRireki(String uriageId) {

		// 売上IDで履歴リスト検索
		RUriage entity = new RUriage();
		RUriagePK pk = new RUriagePK();
		pk.setUriageId(uriageId);
		entity.setPk(pk);
		Example<RUriage> example = Example.of(entity);
		List<RUriage> uriageRireki = repo.findAll(example);

		// 各履歴を売上ドメインとしてbuild
		Set<UriageDomain> uriageList = new HashSet<UriageDomain>();
		uriageRireki.forEach(e -> {

			// 顧客ドメイン
			String kokyakuCode = e.getUriage().getKokyaku().getCode();
			KokyakuDomain kokyaku = kokyakuCrudService.getDomain(kokyakuCode);

			UriageDomainBuilder b = new UriageDomainBuilder();
			b.withDenpyoNumber(e.getUriage().getPk().getDenpyoNumber());
			b.withHanbaiKubun(Kubun.get(HanbaiKubun.class, e.getHanbaiKubun()));
			b.withKokyaku(kokyaku);
			b.withRecordId(e.getRecordId());
			b.withShohizeiritsu(new TaxRate(e.getShohizeiritsu()));
			b.withUriageDate(new TheDate(e.getUriageDate()));
			b.withKeijoDate(new TheDate(e.getPk().getKeijoDate()));
			b.withVersion(e.getVersion());
			uriageList.add(b.build());
		});

		// build
		UriageRirekiDomainBuilder b = new UriageRirekiDomainBuilder();
		b.withUriageRireki(uriageList);
		return b.build();
	}

	@Override
	public void save(UriageDomain domain) {

		// pk
		RUriagePK pk = new RUriagePK();
		pk.setUriageId(domain.getRecordId());
		pk.setKeijoDate(domain.getKeijoDate().toDate());

		// 売上履歴Entity
		RUriage e = repo.findById(pk).orElse(new RUriage());
		e.setHanbaiKubun(domain.getHanbaiKubun().getCode());
		e.setPk(pk);
		e.setShohizeiritsu(domain.getShohizeiritsu().getRate().doubleValue());
		e.setUriageDate(domain.getUriageDate().toDate());
		String uriageId = e.getRecordId() != null ? e.getRecordId() : UUID.randomUUID().toString();
		e.setRecordId(uriageId);

		// save
		// 排他制御省略（売上テーブルで行う）
		repo.saveAndFlush(e);

		// 明細 TODO Serviceが煩雑な仕事をしている。売上履歴ドメインに仕事を以上できまいか？
		List<UriageMeisaiDomain> meisaiList = domain.getUriageMeisai();
		List<UriageMeisaiDomain> meisaiRirekiList = new ArrayList<UriageMeisaiDomain>();
		meisaiList.forEach(m -> {
			// 売上履歴のレコードIDを適用し、売上履歴明細ドメインを生成
			UriageMeisaiDomainBuilder b = new UriageMeisaiDomainBuilder();
			b.withUriageId(uriageId);
			UriageMeisaiDomain rm = b.apply(m);
			meisaiRirekiList.add(rm);
		});

		uriageRirekiMeisaiCrudService.overrideList(meisaiRirekiList);
	}
}
