package com.showka.service.query.u05;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.showka.domain.builder.UriageBuilder;
import com.showka.domain.builder.UriageRirekiBuilder;
import com.showka.domain.u01.Kokyaku;
import com.showka.domain.u05.Uriage;
import com.showka.domain.u05.UriageMeisai;
import com.showka.domain.u05.UriageRireki;
import com.showka.domain.z00.Busho;
import com.showka.entity.MKokyaku;
import com.showka.entity.RUriage;
import com.showka.entity.RUriagePK;
import com.showka.entity.TUriage;
import com.showka.kubun.HanbaiKubun;
import com.showka.kubun.i.Kubun;
import com.showka.repository.i.RUriageRepository;
import com.showka.service.crud.u01.i.KokyakuCrud;
import com.showka.service.crud.u05.i.UriageRirekiMeisaiCrud;
import com.showka.service.query.u05.i.UriageRirekiQuery;
import com.showka.value.EigyoDate;
import com.showka.value.TaxRate;
import com.showka.value.TheDate;

@Service
public class UriageRirekiQueryImpl implements UriageRirekiQuery {

	@Autowired
	private RUriageRepository repo;

	@Autowired
	private KokyakuCrud kokyakuCrud;

	@Autowired
	private UriageRirekiMeisaiCrud uriageRirekiMeisaiCrud;

	@Override
	public List<RUriage> getEntityList(Busho busho, TheDate date) {
		// entity
		RUriage e = new RUriage();
		// pk
		RUriagePK pk = new RUriagePK();
		// 計上日
		pk.setKeijoDate(date.toDate());
		e.setPk(pk);
		// 顧客+主管部署
		MKokyaku kokyaku = new MKokyaku();
		kokyaku.setShukanBushoId(busho.getRecordId());
		// 売上
		TUriage uriage = new TUriage();
		uriage.setKokyaku(kokyaku);
		e.setUriage(uriage);
		// search
		Example<RUriage> example = Example.of(e);
		return repo.findAll(example);
	}

	@Override
	public UriageRireki get(String uriageId) {
		// entity
		List<RUriage> entityList = this.findAllById(uriageId);
		// 各履歴を売上ドメインとしてbuild
		List<Uriage> domainList = this.buildBy(entityList);
		// build
		UriageRirekiBuilder b = new UriageRirekiBuilder();
		b.withUriageId(uriageId);
		b.withList(domainList);
		return b.build();
	}

	// 売上IDで履歴リスト検索
	List<RUriage> findAllById(String uriageId) {
		RUriage entity = new RUriage();
		RUriagePK pk = new RUriagePK();
		pk.setUriageId(uriageId);
		entity.setPk(pk);
		Example<RUriage> example = Example.of(entity);
		return repo.findAll(example);
	}

	// 各履歴を売上ドメインとしてbuild
	List<Uriage> buildBy(List<RUriage> entityList) {
		return entityList.stream().map(e -> {
			// 顧客ドメイン
			String kokyakuCode = e.getUriage().getKokyaku().getCode();
			Kokyaku kokyaku = kokyakuCrud.getDomain(kokyakuCode);
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
			List<UriageMeisai> uriageMeisai = uriageRirekiMeisaiCrud.getDomainList(e.getRecordId());
			b.withUriageMeisai(uriageMeisai);
			// build
			return b.build();
		}).collect(Collectors.toList());
	}
}
