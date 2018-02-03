package com.showka.service.specification.u11;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.ShohinIdoDomain;
import com.showka.domain.ShohinIdoMeisaiDomain;
import com.showka.domain.UriageDomain;
import com.showka.domain.UriageMeisaiDomain;
import com.showka.domain.builder.ShohinIdoDomainBuilder;
import com.showka.domain.builder.ShohinIdoMeisaiDomainBuilder;
import com.showka.entity.TUriagePK;
import com.showka.kubun.ShohinIdoKubun;
import com.showka.service.crud.u05.i.UriageCrudService;
import com.showka.service.specification.u11.i.ShohinIdoSpecification;
import com.showka.value.TheTimestamp;

@Service
public class ShohinIdoSpecificationImpl implements ShohinIdoSpecification {

	@Autowired
	private UriageCrudService uriageCrudService;

	@Override
	public List<ShohinIdoDomain> buildShohinIdo(UriageDomain uriage) {
		// 売上による商品移動
		List<ShohinIdoDomain> shohinIdo = new ArrayList<ShohinIdoDomain>();
		shohinIdo.add(buildShohinIdoFromUriageDomain(uriage, false));
		// 売上訂正による商品移動
		TUriagePK pk = new TUriagePK(uriage.getKokyaku().getRecordId(), uriage.getDenpyoNumber());
		boolean alredyExists = uriageCrudService.exsists(pk);
		if (alredyExists) {
			UriageDomain past = uriageCrudService.getDomain(pk);
			shohinIdo.add(buildShohinIdoFromUriageDomain(past, true));
		}
		return shohinIdo;
	}

	/**
	 * 売上から商品移動をビルド.
	 * 
	 * @param uriage
	 *            売上
	 * @param teisei
	 *            売上訂正による商品移動にしたい場合はtrue
	 * @return 商品移動
	 */
	private ShohinIdoDomain buildShohinIdoFromUriageDomain(UriageDomain uriage, boolean teisei) {
		// 売上明細
		List<UriageMeisaiDomain> meisaiList = uriage.getUriageMeisai();
		// 商品移動明細
		AtomicInteger i = new AtomicInteger(1);
		List<ShohinIdoMeisaiDomain> shohinIdoMeisaiList = meisaiList.stream().map(meisai -> {
			ShohinIdoMeisaiDomainBuilder b = new ShohinIdoMeisaiDomainBuilder();
			b.withMeisaiNumber(i.getAndIncrement());
			b.withNumber(meisai.getHanbaiNumber());
			b.withShohinDomain(meisai.getShohinDomain());
			return b.build();
		}).collect(Collectors.toList());
		// 商品移動
		ShohinIdoDomainBuilder b = new ShohinIdoDomainBuilder();
		b.withBusho(uriage.getKokyaku().getShukanBusho());
		b.withDate(uriage.getKokyaku().getShukanBusho().getEigyoDate());
		ShohinIdoKubun _kubun = teisei ? ShohinIdoKubun.売上訂正 : ShohinIdoKubun.売上;
		b.withKubun(_kubun);
		b.withMeisai(shohinIdoMeisaiList);
		b.withTimestamp(new TheTimestamp());
		return b.build();
	}

}
