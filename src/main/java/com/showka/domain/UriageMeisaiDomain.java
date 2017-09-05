package com.showka.domain;

import java.math.BigDecimal;

import com.showka.system.EmptyProxy;
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
public class UriageMeisaiDomain extends DomainBase {

	// private member
	/** ID. */
	private String uriageId = STRING_EMPTY;

	/** 明細番号. */
	private Integer meisaiNumber = INTEGER_EMPTY;

	/** 商品. */
	private ShohinDomain shohinDomain = EmptyProxy.domain(ShohinDomain.class);

	/** 売上数. */
	private Integer hanbaiNumber = INTEGER_EMPTY;

	/** 商品販売単価. */
	private BigDecimal hanbaiTanka = BigDecimal.ZERO;

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
		UriageMeisaiDomain o = (UriageMeisaiDomain) other;
		return (uriageId.equals(o.uriageId) && meisaiNumber.equals(o.meisaiNumber));
	}

	@Override
	public int hashCode() {
		return generateHashCode(uriageId, meisaiNumber);
	}

}