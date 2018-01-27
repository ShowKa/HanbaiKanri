package com.showka.service.crud.u05;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.showka.domain.KokyakuDomain;
import com.showka.domain.UriageDomain;
import com.showka.domain.UriageMeisaiDomain;
import com.showka.domain.UriageRirekiListDomain;
import com.showka.domain.builder.UriageDomainBuilder;
import com.showka.domain.builder.UriageMeisaiDomainBuilder;
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
		List<UriageDomain> domainList = new ArrayList<UriageDomain>();
		entityList.forEach(e -> {

			// 顧客ドメイン
			String kokyakuCode = e.getUriage().getKokyaku().getCode();
			KokyakuDomain kokyaku = kokyakuCrudService.getDomain(kokyakuCode);

			// entity -> domain
			UriageDomainBuilder b = new UriageDomainBuilder();
			b.withDenpyoNumber(e.getUriage().getPk().getDenpyoNumber());
			b.withKokyaku(kokyaku);
			b.withHanbaiKubun(Kubun.get(HanbaiKubun.class, e.getHanbaiKubun()));
			b.withRecordId(e.getRecordId());
			b.withShohizeiritsu(new TaxRate(e.getShohizeiritsu()));
			b.withUriageDate(new TheDate(e.getUriageDate()));
			b.withKeijoDate(new TheDate(e.getPk().getKeijoDate()));
			b.withVersion(e.getVersion());

			// meisai
			List<UriageMeisaiDomain> uriageMeisai = uriageRirekiMeisaiCrudService.getDomainList(e.getRecordId());
			b.withUriageMeisai(uriageMeisai);

			// add
			domainList.add(b.build());
		});

		// build
		UriageRirekiListDomainBuilder b = new UriageRirekiListDomainBuilder();
		b.withUriageId(uriageId);
		b.withList(domainList);
		return b.build();
	}

	@Override
	public void save(UriageDomain domain) {

		// pk
		RUriagePK pk = new RUriagePK();
		String uriageId = domain.getRecordId();
		pk.setUriageId(uriageId);
		pk.setKeijoDate(domain.getKeijoDate().toDate());

		// 売上履歴Entity
		Optional<RUriage> _e = repo.findById(pk);
		RUriage e = _e.orElse(new RUriage());
		e.setHanbaiKubun(domain.getHanbaiKubun().getCode());
		e.setPk(pk);
		e.setShohizeiritsu(domain.getShohizeiritsu().getRate().doubleValue());
		e.setUriageDate(domain.getUriageDate().toDate());

		// set common column
		String recordId = _e.isPresent() ? _e.get().getRecordId() : UUID.randomUUID().toString();
		e.setRecordId(recordId);

		// 排他制御省略

		// save
		repo.saveAndFlush(e);

		// 明細
		List<UriageMeisaiDomain> meisaiList = domain.getUriageMeisai().stream().map(meisai -> {
			UriageMeisaiDomainBuilder b = new UriageMeisaiDomainBuilder();
			b.withUriageId(recordId);
			return b.apply(meisai);
		}).collect(Collectors.toList());
		uriageRirekiMeisaiCrudService.overrideList(meisaiList);
	}

	@Override
	public void cancel(UriageDomain domain) {
		// pk
		RUriagePK pk = new RUriagePK();
		pk.setUriageId(domain.getRecordId());
		pk.setKeijoDate(domain.getKeijoDate().toDate());

		// 売上履歴Entity
		Optional<RUriage> _e = repo.findById(pk);
		String recordId = _e.isPresent() ? _e.get().getRecordId() : UUID.randomUUID().toString();
		RUriage e = _e.orElse(new RUriage());
		e.setPk(pk);
		e.setHanbaiKubun(domain.getHanbaiKubun().getCode());
		e.setShohizeiritsu(domain.getShohizeiritsu().getRate().doubleValue());
		e.setUriageDate(domain.getUriageDate().toDate());
		e.setRecordId(recordId);
		repo.saveAndFlush(e);

		// Delete 明細
		if (_e.isPresent()) {
			uriageRirekiMeisaiCrudService.deleteAll(recordId);
		}
	}

	@Override
	public void delete(UriageDomain domain) {
		// 既存取得
		String uriageId = domain.getRecordId();
		RUriagePK pk = new RUriagePK();
		pk.setUriageId(uriageId);
		pk.setKeijoDate(domain.getKeijoDate().toDate());
		RUriage rireki = repo.getOne(pk);
		// 明細削除
		uriageRirekiMeisaiCrudService.deleteAll(rireki.getRecordId());
		// 排他制御対象外
		this.delete(pk, rireki.getVersion());
	}

	@Override
	public void delete(RUriagePK pk, Integer version) {
		RUriage e = repo.getOne(pk);
		e.setPk(pk);
		e.setVersion(version);
		repo.delete(e);
	}
}
