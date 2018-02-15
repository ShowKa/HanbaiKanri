package com.showka.domain;

import java.math.BigDecimal;

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
public class UriageMeisai extends DomainBase implements Comparable<UriageMeisai> {

	// private member
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
		return meisaiNumber.equals(o.meisaiNumber);
	}

	@Override
	public int hashCode() {
		return meisaiNumber.hashCode();
	}

	@Override
	public int compareTo(UriageMeisai o) {
		return this.meisaiNumber.compareTo(o.meisaiNumber);
	}
}
