package com.showka.service.crud.u05;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.showka.domain.KokyakuDomain;
import com.showka.domain.UriageDomain;
import com.showka.domain.UriageRirekiDomain;
import com.showka.domain.builder.UriageDomainBuilder;
import com.showka.domain.builder.UriageRirekiDomainBuilder;
import com.showka.entity.TUriageRireki;
import com.showka.entity.TUriageRirekiPK;
import com.showka.kubun.HanbaiKubun;
import com.showka.kubun.i.Kubun;
import com.showka.repository.i.TUriageRirekiRepository;
import com.showka.service.crud.u01.i.KokyakuCrudService;
import com.showka.service.crud.u05.i.UriageRirekiCrudService;
import com.showka.value.TaxRate;
import com.showka.value.TheDate;

@Service
public class UriageRirekiCrudServiceImpl implements UriageRirekiCrudService {

	@Autowired
	private TUriageRirekiRepository repo;

	@Autowired
	private KokyakuCrudService kokyakuCrudService;

	@Override
	public UriageRirekiDomain getUriageRireki(String uriageId) {

		// 売上IDで履歴リスト検索
		TUriageRireki entity = new TUriageRireki();
		TUriageRirekiPK pk = new TUriageRirekiPK();
		pk.setUriageId(uriageId);
		entity.setPk(pk);
		Example<TUriageRireki> example = Example.of(entity);
		List<TUriageRireki> uriageRireki = repo.findAll(example);

		// 各履歴を売上ドメインとしてbuild
		List<UriageDomain> uriageList = new ArrayList<UriageDomain>();
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
		TUriageRirekiPK pk = new TUriageRirekiPK();
		pk.setUriageId(domain.getRecordId());
		pk.setKeijoDate(domain.getKeijoDate().toDate());

		// 売上履歴Entity
		TUriageRireki e = repo.findById(pk).orElse(new TUriageRireki());
		e.setHanbaiKubun(domain.getHanbaiKubun().getCode());
		e.setPk(pk);
		e.setShohizeiritsu(domain.getShohizeiritsu().getRate().doubleValue());
		e.setUriageDate(domain.getUriageDate().toDate());
		String rerocdId = e.getRecordId() != null ? e.getRecordId() : UUID.randomUUID().toString();
		e.setRecordId(rerocdId);

		// 排他制御省略（売上テーブルで行う）

		// save
		repo.saveAndFlush(e);
	}
}
