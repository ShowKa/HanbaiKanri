package com.showka.domain.u05;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.showka.domain.DomainRoot;
import com.showka.domain.u01.Kokyaku;
import com.showka.domain.u01.NyukinKakeInfo;
import com.showka.kubun.HanbaiKubun;
import com.showka.system.exception.SystemException;
import com.showka.value.EigyoDate;
import com.showka.value.Kakaku;
import com.showka.value.TaxRate;
import com.showka.value.TheDate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Uriage extends DomainRoot {

	/** 顧客. */
	private Kokyaku kokyaku;

	/** 伝票番号. */
	private String denpyoNumber;

	/** 売上日. */
	private EigyoDate uriageDate;

	/** 計上日. */
	private EigyoDate keijoDate;

	/** 販売区分. */
	private HanbaiKubun hanbaiKubun;

	/** 消費税率. */
	private TaxRate shohizeiritsu;

	/** 売上明細. */
	private List<UriageMeisai> uriageMeisai;

	/**
	 * 売上合計価格取得.
	 * 
	 * @return
	 */
	public Kakaku getUriageGokeiKakaku() {
		BigDecimal gokei = BigDecimal.ZERO;
		for (UriageMeisai m : uriageMeisai) {
			gokei = gokei.add(m.getMeisaiGokeiKakaku());
		}
		return new Kakaku(gokei, shohizeiritsu);
	}

	/**
	 * 入金予定日初期値取得.
	 * 
	 * <pre>
	 * 顧客の掛売情報から計上日を基準にして入金予定日を取得する。
	 * ただし販売区分=現金の場合、売上の計上日をそのまま返却する。
	 * 顧客に入金掛け情報がない場合は、empty
	 * </pre>
	 * 
	 * @return 入金予定日
	 */
	public Optional<TheDate> getDefaultNyukinYoteiDate() {
		if (hanbaiKubun == HanbaiKubun.現金) {
			return Optional.of(new EigyoDate(keijoDate));
		}
		Optional<NyukinKakeInfo> _nki = kokyaku.getNyukinKakeInfo();
		if (_nki.isPresent()) {
			NyukinKakeInfo nyukinKakeInfo = _nki.get();
			return Optional.of(nyukinKakeInfo.getNyukinYoteiDate(keijoDate));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void validate() throws SystemException {
		// nothing to do
	}

	@Override
	protected boolean equals(DomainRoot other) {
		Uriage o = (Uriage) other;
		return kokyaku.equals(o.getKokyaku()) && denpyoNumber.equals(o.getDenpyoNumber());
	}

	@Override
	public int hashCode() {
		return generateHashCode(kokyaku, denpyoNumber);
	}

	/**
	 * 計上日による売上比較クラス.
	 *
	 */
	public static class UriageComparatorByKejoDate implements Comparator<Uriage> {
		@Override
		public int compare(Uriage o1, Uriage o2) {
			return o1.getKeijoDate().compareTo(o2.getKeijoDate());
		}
	}
}
