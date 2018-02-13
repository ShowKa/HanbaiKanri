package com.showka.domain;

import java.math.BigDecimal;
import java.util.Comparator;

import com.showka.system.exception.SystemException;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 売上明細
 * 
 * @author ShowKa
 *
 */
@AllArgsConstructor
@Getter
public class UriageMeisai extends DomainBase {

	// private member
	/** ID. */
	private String uriageId;

	/** 明細番号. */
	private Integer meisaiNumber;

	/** 商品. */
	private Shohin shohinDomain;

	/** 売上数. */
	private Integer hanbaiNumber;

	/** 商品販売単価. */
	private BigDecimal hanbaiTanka;

	// public method
	/**
	 * 明細合計金額取得
	 * 
	 * @return 明細合計金額
	 */
	public BigDecimal getMeisaiGokeiKakaku() {
		return hanbaiTanka.multiply(new BigDecimal(hanbaiNumber));
	}

	@Override
	public void validate() throws SystemException {
		// nothing to do
	}

	@Override
	protected boolean equals(DomainBase other) {
		UriageMeisai o = (UriageMeisai) other;
		return (uriageId.equals(o.uriageId) && meisaiNumber.equals(o.meisaiNumber));
	}

	@Override
	public int hashCode() {
		return generateHashCode(uriageId, meisaiNumber);
	}

	/**
	 * 明細番号による売上明細比較クラス.
	 * 
	 */
	public static class UriageMeisaiComparatorByMeisaiNumber implements Comparator<UriageMeisai> {
		@Override
		public int compare(UriageMeisai o1, UriageMeisai o2) {
			return o1.meisaiNumber.compareTo(o2.meisaiNumber);
		}
	}
}
