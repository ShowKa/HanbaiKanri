package com.showka.domain;

import java.math.BigDecimal;
import java.util.List;

import com.showka.system.exception.SystemException;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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
	private String uriageId;

	/** 明細番号. */
	@Setter
	private Integer meisaiNumber;

	/** 商品. */
	private ShohinDomain shohinDomain;

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
		UriageMeisaiDomain o = (UriageMeisaiDomain) other;
		return (uriageId.equals(o.uriageId) && meisaiNumber.equals(o.meisaiNumber));
	}

	@Override
	public int hashCode() {
		return generateHashCode(uriageId, meisaiNumber);
	}

	public List<UriageMeisaiDomain> getUriageMeisai() {
		// TODO Auto-generated method stub
		return null;
	}

}
