package com.showka.service.specification.u11;

import static java.util.stream.Collectors.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;

import com.showka.domain.builder.ShohinIdoBuilder;
import com.showka.domain.builder.ShohinIdoMeisaiBuilder;
import com.showka.domain.u05.Uriage;
import com.showka.domain.u05.UriageMeisai;
import com.showka.domain.u11.ShohinIdo;
import com.showka.domain.u11.ShohinIdoMeisai;
import com.showka.domain.u11.ShohinZaiko;
import com.showka.domain.z00.Busho;
import com.showka.domain.z00.Shohin;
import com.showka.kubun.ShohinIdoKubun;
import com.showka.service.query.u11.i.ShohinIdoUriageQuery;
import com.showka.service.query.u11.i.ShohinZaikoQuery;
import com.showka.service.specification.u11.i.ShohinIdoSpecification;
import com.showka.system.exception.specification.MinusZaikoException;
import com.showka.system.exception.specification.MinusZaikoException.MinusZaiko;
import com.showka.system.exception.specification.UnsatisfiedSpecificationException;
import com.showka.system.exception.validate.ValidateException;
import com.showka.value.EigyoDate;
import com.showka.value.TheTimestamp;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 売上商品移動.
 * 
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShohinIdoSpecificationAssociatedWithUriage implements ShohinIdoSpecification {

	@Autowired
	private ShohinIdoUriageQuery shohinIdoUriageQuery;

	@Autowired
	private ShohinZaikoQuery shohinZaikoQuery;

	/** 売上. */
	@Setter(value = AccessLevel.PROTECTED)
	private Uriage uriage;

	/** cache */
	private List<ShohinIdo> shohinIdoList = new ArrayList<ShohinIdo>();

	@Override
	public List<ShohinIdo> getShohinIdo() {
		if (!shohinIdoList.isEmpty()) {
			return shohinIdoList;
		}
		// 商品移動
		// 売上ID
		String uriageId = this.uriage.getRecordId();
		// 営業日
		EigyoDate eigyoDate = this.uriage.getKokyaku().getShukanBusho().getEigyoDate();
		// 新規登録の場合
		// 条件: 商品移動がない
		// やること: 売上から商品移動（売上）をビルド
		Optional<ShohinIdo> _newest = shohinIdoUriageQuery.getNewest(uriageId);
		if (!_newest.isPresent()) {
			ShohinIdo ido = this.build();
			shohinIdoList.add(ido);
			return shohinIdoList;
		}
		// 訂正の更新の場合
		// 条件: 商品移動がある。ただし当日営業日の商品移動がない。
		// やること：直前の移動から商品移動（売上訂正）をビルド
		List<ShohinIdo> idoList = shohinIdoUriageQuery.get(uriageId, eigyoDate);
		if (idoList.isEmpty()) {
			ShohinIdo past = _newest.get();
			ShohinIdo teisei = this.buildTeisei(past);
			shohinIdoList.add(teisei);
			ShohinIdo ido = this.build();
			shohinIdoList.add(ido);
			return shohinIdoList;
		}
		// すでに登録or訂正済みで、これを更新する場合
		// 条件: 商品移動がある。当日営業日の商品移動が存在する。
		// やること: 既存の商品移動（売上）の明細を更新する。
		idoList.stream().forEach(i -> {
			switch (i.getKubun()) {
			case 売上:
				// 明細のみ上書きする。
				ShohinIdoBuilder b = new ShohinIdoBuilder();
				b.withMeisai(this.build().getMeisai());
				shohinIdoList.add(b.apply(i));
				break;
			case 売上訂正:
				shohinIdoList.add(i);
				break;
			default:
				break;
			}
		});
		return shohinIdoList;
	}

	/**
	 * 仕様を満たすか検証.
	 * 
	 * <pre>
	 * マイナス在庫になる場合エラー
	 * </pre>
	 * 
	 * @throws ValidateException
	 */
	@Override
	public void ascertainSatisfaction() throws UnsatisfiedSpecificationException {
		List<ShohinIdo> _shohinIdoList = this.getShohinIdo();
		Set<Shohin> shohinSet = _shohinIdoList.stream().map(i -> {
			return i.getShohinSet();
		}).flatMap(Set::stream).collect(toSet());
		List<MinusZaiko> minusZaikoList = new ArrayList<MinusZaiko>();
		shohinSet.forEach(s -> {
			Busho busho = this.uriage.getKokyaku().getShukanBusho();
			EigyoDate date = busho.getEigyoDate();
			ShohinZaiko zaiko = shohinZaikoQuery.get(busho, date, s);
			ShohinZaiko zaikoMerged = zaiko.merge(_shohinIdoList);
			if (zaikoMerged.isNegative()) {
				MinusZaiko minusZaiko = new MinusZaiko(s, zaiko.getNumber(), zaikoMerged.getNumber());
				minusZaikoList.add(minusZaiko);
			}
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
	private ShohinIdo build() {
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
		}).collect(toList());
		// 商品移動
		ShohinIdoBuilder b = new ShohinIdoBuilder();
		b.withBusho(uriage.getKokyaku().getShukanBusho());
		b.withDate(uriage.getKokyaku().getShukanBusho().getEigyoDate());
		b.withKubun(ShohinIdoKubun.売上);
		b.withMeisai(shohinIdoMeisaiList);
		b.withTimestamp(new TheTimestamp());
		return b.build();
	}

	/**
	 * 売上訂正の商品移動をビルド.
	 * 
	 * @param past
	 *            訂正対象の商品移動
	 * @return 商品移動
	 */
	private ShohinIdo buildTeisei(ShohinIdo past) {
		// 営業日
		Busho shukanBusho = this.uriage.getKokyaku().getShukanBusho();
		EigyoDate eigyoDate = shukanBusho.getEigyoDate();
		// build 商品移動（売上訂正）
		ShohinIdoBuilder b = new ShohinIdoBuilder();
		b.withBusho(past.getBusho());
		b.withKubun(ShohinIdoKubun.売上訂正);
		b.withDate(eigyoDate);
		List<ShohinIdoMeisai> meisai = past.getMeisai().stream().map(m -> {
			ShohinIdoMeisaiBuilder mb = new ShohinIdoMeisaiBuilder();
			mb.withMeisaiNumber(m.getMeisaiNumber());
			mb.withNumber(m.getNumber());
			mb.withShohinDomain(m.getShohinDomain());
			return mb.build();
		}).collect(toList());
		b.withMeisai(meisai);
		b.withTimestamp(new TheTimestamp());
		return b.build();
	}
}
