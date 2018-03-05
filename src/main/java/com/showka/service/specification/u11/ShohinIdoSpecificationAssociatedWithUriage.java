package com.showka.service.specification.u11;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.showka.domain.Busho;
import com.showka.domain.Shohin;
import com.showka.domain.ShohinIdo;
import com.showka.domain.ShohinIdoMeisai;
import com.showka.domain.ShohinZaiko;
import com.showka.domain.Uriage;
import com.showka.domain.UriageMeisai;
import com.showka.domain.builder.ShohinIdoBuilder;
import com.showka.domain.builder.ShohinIdoMeisaiBuilder;
import com.showka.entity.TUriagePK;
import com.showka.kubun.ShohinIdoKubun;
import com.showka.service.crud.u05.UriageCrudServiceImpl;
import com.showka.service.crud.u05.i.UriageCrudService;
import com.showka.service.crud.u11.i.ShohinZaikoCrudService;
import com.showka.service.specification.u11.i.ShohinIdoSpecification;
import com.showka.system.exception.MinusZaikoException;
import com.showka.system.exception.MinusZaikoException.MinusZaiko;
import com.showka.system.exception.UnsatisfiedSpecificationException;
import com.showka.system.exception.ValidateException;
import com.showka.value.TheDate;
import com.showka.value.TheTimestamp;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 売上商品移動.
 * 
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShohinIdoSpecificationAssociatedWithUriage implements ShohinIdoSpecification {

	@Autowired
	private UriageCrudService uriageCrudService = new UriageCrudServiceImpl();

	@Autowired
	private ShohinZaikoCrudService shohinZaikoCrudService;

	/**
	 * 商品移動.
	 */
	private List<ShohinIdo> shohinIdo;

	protected void setUriage(Uriage uriage) {
		// 売上による商品移動
		List<ShohinIdo> shohinIdo = new ArrayList<ShohinIdo>();
		shohinIdo.add(buildShohinIdoFromUriageDomain(uriage, false));
		// 売上訂正による商品移動
		TUriagePK pk = new TUriagePK(uriage.getKokyaku().getRecordId(), uriage.getDenpyoNumber());
		boolean alredyExists = uriageCrudService.exsists(pk);
		if (alredyExists) {
			Uriage past = uriageCrudService.getDomain(pk);
			shohinIdo.add(buildShohinIdoFromUriageDomain(past, true));
		}
		this.shohinIdo = shohinIdo;
	}

	/**
	 * 売上による商品移動を取得.
	 * 
	 * <pre>
	 * 伝票訂正の場合、マイナス移動も含まれる。
	 * </pre>
	 * 
	 * @param uriage
	 *            売上
	 * @return 商品移動ドメイン
	 */
	@Override
	public List<ShohinIdo> getShohinIdo() {
		return shohinIdo;
	}

	/**
	 * マイナス在庫になる場合エラー.
	 * 
	 * @throws ValidateException
	 */
	@Override
	public void ascertainSatisfaction() throws UnsatisfiedSpecificationException {
		List<MinusZaiko> minusZaikoList = new ArrayList<MinusZaiko>();
		shohinIdo.forEach(ido -> {
			Busho busho = ido.getBusho();
			TheDate date = ido.getDate();
			ido.getMeisai().forEach(m -> {
				Shohin shohin = m.getShohinDomain();
				ShohinZaiko zaiko = shohinZaikoCrudService.getShohinZaiko(busho, date, shohin);
				int rest = zaiko.getNumber() - m.getNumber();
				if (rest < 0) {
					MinusZaiko mz = new MinusZaiko(shohin, zaiko.getNumber(), rest);
					minusZaikoList.add(mz);
				}
			});
		});
		if (!minusZaikoList.isEmpty()) {
			throw new MinusZaikoException(minusZaikoList);
		}
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
	private ShohinIdo buildShohinIdoFromUriageDomain(Uriage uriage, boolean teisei) {
		// 売上明細
		List<UriageMeisai> meisaiList = uriage.getUriageMeisai();
		// 商品移動明細
		AtomicInteger i = new AtomicInteger(1);
		List<ShohinIdoMeisai> shohinIdoMeisaiList = meisaiList.stream().map(meisai -> {
			ShohinIdoMeisaiBuilder b = new ShohinIdoMeisaiBuilder();
			b.withMeisaiNumber(i.getAndIncrement());
			b.withNumber(meisai.getHanbaiNumber());
			b.withShohinDomain(meisai.getShohinDomain());
			return b.build();
		}).collect(Collectors.toList());
		// 商品移動
		ShohinIdoBuilder b = new ShohinIdoBuilder();
		b.withBusho(uriage.getKokyaku().getShukanBusho());
		b.withDate(uriage.getKokyaku().getShukanBusho().getEigyoDate());
		ShohinIdoKubun _kubun = teisei ? ShohinIdoKubun.売上訂正 : ShohinIdoKubun.売上;
		b.withKubun(_kubun);
		b.withMeisai(shohinIdoMeisaiList);
		b.withTimestamp(new TheTimestamp());
		return b.build();
	}
}