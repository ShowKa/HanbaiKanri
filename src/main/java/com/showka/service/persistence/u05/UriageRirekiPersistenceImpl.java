package com.showka.service.persistence.u05;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.showka.domain.builder.UriageBuilder;
import com.showka.domain.builder.UriageRirekiBuilder;
import com.showka.domain.u01.Kokyaku;
import com.showka.domain.u05.Uriage;
import com.showka.domain.u05.UriageMeisai;
import com.showka.domain.u05.UriageRireki;
import com.showka.entity.RUriage;
import com.showka.entity.RUriagePK;
import com.showka.kubun.HanbaiKubun;
import com.showka.kubun.i.Kubun;
import com.showka.repository.i.RUriageRepository;
import com.showka.service.persistence.u01.i.KokyakuPersistence;
import com.showka.service.persistence.u05.i.UriageRirekiMeisaiPersistence;
import com.showka.service.persistence.u05.i.UriageRirekiPersistence;
import com.showka.value.EigyoDate;
import com.showka.value.TaxRate;

@Service
public class UriageRirekiPersistenceImpl implements UriageRirekiPersistence {

	@Autowired
	private RUriageRepository repo;

	@Autowired
	private KokyakuPersistence kokyakuPersistence;

	@Autowired
	private UriageRirekiMeisaiPersistence uriageRirekiMeisaiPersistence;

	@Override
	public UriageRireki getUriageRirekiList(String uriageId) {

		// 売上IDで履歴リスト検索
		RUriage entity = new RUriage();
		RUriagePK pk = new RUriagePK();
		pk.setUriageId(uriageId);
		entity.setPk(pk);
		Example<RUriage> example = Example.of(entity);
		List<RUriage> entityList = repo.findAll(example);

		// 各履歴を売上ドメインとしてbuild
		List<Uriage> domainList = new ArrayList<Uriage>();
		entityList.forEach(e -> {

			// 顧客ドメイン
			String kokyakuCode = e.getUriage().getKokyaku().getCode();
			Kokyaku kokyaku = kokyakuPersistence.getDomain(kokyakuCode);

			// entity -> domain
			UriageBuilder b = new UriageBuilder();
			b.withDenpyoNumber(e.getUriage().getPk().getDenpyoNumber());
			b.withKokyaku(kokyaku);
			b.withHanbaiKubun(Kubun.get(HanbaiKubun.class, e.getHanbaiKubun()));
			b.withRecordId(e.getRecordId());
			b.withShohizeiritsu(new TaxRate(e.getShohizeiritsu()));
			b.withUriageDate(new EigyoDate(e.getUriageDate()));
			b.withKeijoDate(new EigyoDate(e.getPk().getKeijoDate()));
			b.withVersion(e.getVersion());

			// meisai
			List<UriageMeisai> uriageMeisai = uriageRirekiMeisaiPersistence.getDomainList(e.getRecordId());
			b.withUriageMeisai(uriageMeisai);

			// add
			domainList.add(b.build());
		});

		// build
		UriageRirekiBuilder b = new UriageRirekiBuilder();
		b.withUriageId(uriageId);
		b.withList(domainList);
		return b.build();
	}

	@Override
	public void save(Uriage domain) {

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
		uriageRirekiMeisaiPersistence.overrideList(recordId, domain.getUriageMeisai());
	}

	@Override
	public void delete(RUriagePK pk) {
		// entity
		RUriage rireki = repo.getOne(pk);
		// 明細削除
		uriageRirekiMeisaiPersistence.deleteAll(rireki.getRecordId());
		// 削除
		repo.delete(rireki);
	}
}
