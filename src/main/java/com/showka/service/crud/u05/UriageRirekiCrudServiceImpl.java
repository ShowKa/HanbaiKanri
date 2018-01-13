package com.showka.service.crud.u05;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.showka.domain.KokyakuDomain;
import com.showka.domain.UriageRirekiDomain;
import com.showka.domain.UriageRirekiListDomain;
import com.showka.domain.UriageRirekiMeisaiDomain;
import com.showka.domain.builder.UriageRirekiDomainBuilder;
import com.showka.domain.builder.UriageRirekiListDomainBuilder;
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
	public UriageRirekiListDomain getUriageRirekiList(String uriageId) {

		// 売上IDで履歴リスト検索
		RUriage entity = new RUriage();
		RUriagePK pk = new RUriagePK();
		pk.setUriageId(uriageId);
		entity.setPk(pk);
		Example<RUriage> example = Example.of(entity);
		List<RUriage> entityList = repo.findAll(example);

		// 各履歴を売上ドメインとしてbuild
		Set<UriageRirekiDomain> domainList = new HashSet<UriageRirekiDomain>();
		entityList.forEach(e -> {

			// 顧客ドメイン
			String kokyakuCode = e.getUriage().getKokyaku().getCode();
			KokyakuDomain kokyaku = kokyakuCrudService.getDomain(kokyakuCode);

			// entity -> domain
			UriageRirekiDomainBuilder b = new UriageRirekiDomainBuilder();
			b.withUriageId(e.getPk().getUriageId());
			b.withDenpyoNumber(e.getUriage().getPk().getDenpyoNumber());
			b.withKokyaku(kokyaku);
			b.withHanbaiKubun(Kubun.get(HanbaiKubun.class, e.getHanbaiKubun()));
			b.withRecordId(e.getRecordId());
			b.withShohizeiritsu(new TaxRate(e.getShohizeiritsu()));
			b.withUriageDate(new TheDate(e.getUriageDate()));
			b.withKeijoDate(new TheDate(e.getPk().getKeijoDate()));
			b.withVersion(e.getVersion());

			// meisai
			List<UriageRirekiMeisaiDomain> uriageMeisai = uriageRirekiMeisaiCrudService.getDomainList(e.getRecordId());
			b.withUriageMeisai(uriageMeisai);

			// add
			domainList.add(b.build());
		});

		// build
		UriageRirekiListDomainBuilder b = new UriageRirekiListDomainBuilder();
		b.withUriageRirekiList(domainList);
		return b.build();
	}

	@Override
	public void save(UriageRirekiDomain domain) {

		// pk
		RUriagePK pk = new RUriagePK();
		pk.setUriageId(domain.getUriageId());
		pk.setKeijoDate(domain.getKeijoDate().toDate());

		// 売上履歴Entity
		RUriage e = repo.findById(pk).orElse(new RUriage());
		e.setHanbaiKubun(domain.getHanbaiKubun().getCode());
		e.setPk(pk);
		e.setShohizeiritsu(domain.getShohizeiritsu().getRate().doubleValue());
		e.setUriageDate(domain.getUriageDate().toDate());
		String recordId = domain.getRecordId();
		e.setRecordId(recordId);
		e.setVersion(domain.getVersion());

		// save
		repo.saveAndFlush(e);
		uriageRirekiMeisaiCrudService.overrideList(domain.getUriageRirekiMeisai());
	}
}
