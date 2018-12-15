package com.showka.service.specification.u11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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
import com.showka.system.exception.specification.UnsatisfiedSpecificationException;
import com.showka.system.exception.specification.MinusZaikoException.MinusZaiko;
import com.showka.system.exception.validate.ValidateException;
import com.showka.value.EigyoDate;
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
	private ShohinIdoUriageQuery shohinIdoUriageQuery;

	@Autowired
	private ShohinZaikoQuery shohinZaikoQuery;

	/**
	 * 商品移動.
	 */
	private List<ShohinIdo> shohinIdo = new ArrayList<ShohinIdo>();

	/**
	 * 削除対象商品移動.
	 */
	private List<ShohinIdo> shohinIdoForDelete = new ArrayList<ShohinIdo>();

	/**
	 * 部署.
	 */
	private Busho busho;

	/**
	 * 売上設定.
	 * 
	 * @param uriage
	 *            売上
	 */
	protected void setUriage(Uriage uriage) {
		this.busho = uriage.getKokyaku().getShukanBusho();
		// 売上による商品移動
		this.shohinIdo.add(buildShohinIdoFromUriageDomain(uriage));
		// 売上訂正による商品移動
		Optional<ShohinIdo> pastIdo = shohinIdoUriageQuery.getNewest(uriage.getRecordId());
		if (pastIdo.isPresent()) {
			// get past
			ShohinIdo p = pastIdo.get();
			// add 商品移動
			// - 営業日がまだ同じ場合、前回の商品移動を削除対象とする。
			// - 営業日が更新されている場合、商品移動を差し戻すための商品移動を新たに設定する。
			EigyoDate eigyoDate = uriage.getKokyaku().getShukanBusho().getEigyoDate();
			if (eigyoDate.equals(p.getDate())) {
				this.shohinIdoForDelete.add(p);
			} else {
				// build 商品移動（売上訂正）
				ShohinIdoBuilder b = new ShohinIdoBuilder();
				b.withDate(eigyoDate);
				b.withKubun(ShohinIdoKubun.売上訂正);
				b.withBusho(p.getBusho());
				List<ShohinIdoMeisai> meisai = p.getMeisai().stream().map(m -> {
					ShohinIdoMeisaiBuilder mb = new ShohinIdoMeisaiBuilder();
					mb.withMeisaiNumber(m.getMeisaiNumber());
					mb.withNumber(m.getNumber());
					mb.withShohinDomain(m.getShohinDomain());
					return mb.build();
				}).collect(Collectors.toList());
				b.withMeisai(meisai);
				b.withTimestamp(new TheTimestamp());
				ShohinIdo si = b.build();
				this.shohinIdo.add(si);
			}
		}
	}

	/**
	 * 売上による商品移動を取得.
	 * 
	 * <pre>
	 * 伝票訂正の場合、マイナス移動も含まれる。
	 * </pre>
	 * 
	 * @return 商品移動ドメイン
	 */
	@Override
	public List<ShohinIdo> getShohinIdo() {
		return shohinIdo;
	}

	@Override
	public List<ShohinIdo> getShohinIdoForDelete() {
		return this.shohinIdoForDelete;
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
		List<MinusZaiko> minusZaikoList = new ArrayList<MinusZaiko>();
		Map<Shohin, Integer> shohinIdoNumberMap = new HashMap<Shohin, Integer>();
		this.putShohiIdoNumber(shohinIdoNumberMap, this.shohinIdo, false);
		this.putShohiIdoNumber(shohinIdoNumberMap, this.shohinIdoForDelete, true);
		shohinIdoNumberMap.forEach((s, n) -> {
			ShohinZaiko zaiko = shohinZaikoQuery.get(this.busho, this.busho.getEigyoDate(), s);
			Integer present = zaiko.getNumber();
			Integer after = present + n;
			if (after < 0) {
				minusZaikoList.add(new MinusZaiko(s, present, after));
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
	private ShohinIdo buildShohinIdoFromUriageDomain(Uriage uriage) {
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
		b.withKubun(ShohinIdoKubun.売上);
		b.withMeisai(shohinIdoMeisaiList);
		b.withTimestamp(new TheTimestamp());
		return b.build();
	}

	/**
	 * 商品移動数をマップにまとめます.
	 * 
	 * @param shohinIdoNumberMap
	 *            商品移動数のマップ（更新されます）。部署在庫が増える場合は正の数が加算されます。
	 * @param _shohinIdo
	 *            商品移動
	 * @param forDelete
	 *            商品移動が削除対象の場合true
	 */
	private void putShohiIdoNumber(Map<Shohin, Integer> shohinIdoNumberMap, List<ShohinIdo> _shohinIdo,
			boolean forDelete) {
		// 削除対象の場合、移動数の正負符号を反転させる。
		int sign = forDelete ? -1 : 1;
		_shohinIdo.forEach(ido -> {
			Set<Shohin> shohinSet = ido.getShohinSet();
			shohinSet.forEach(s -> {
				int absoluteIdoNumber = ido.getAbusoluteIdoNumberForBushoZaiko(s) * sign;
				int number;
				if (shohinIdoNumberMap.containsKey(s)) {
					Integer present = shohinIdoNumberMap.get(s);
					number = present + absoluteIdoNumber;
				} else {
					number = absoluteIdoNumber;
				}
				shohinIdoNumberMap.put(s, number);
			});
		});
	}
}
