package com.showka.domain;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

import com.showka.kubun.HanbaiKubun;
import com.showka.system.exception.SystemException;
import com.showka.value.Kakaku;
import com.showka.value.TaxRate;
import com.showka.value.TheDate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Uriage extends DomainBase {

	/** 顧客. */
	private Kokyaku kokyaku;

	/** 伝票番号. */
	private String denpyoNumber;

	/** 売上日. */
	private TheDate uriageDate;

	/** 計上日. */
	private TheDate keijoDate;

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

	@Override
	public void validate() throws SystemException {
		// nothing to do
	}

	@Override
	protected boolean equals(DomainBase other) {
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
