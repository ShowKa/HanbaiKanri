package com.showka.domain;

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
public class ShohinIdoMeisaiDomain extends DomainBase implements Comparable<ShohinIdoMeisaiDomain> {

	// private member
	/** 明細番号. */
	private Integer meisaiNumber;

	/** 商品. */
	private ShohinDomain shohinDomain;

	/** 移動数. */
	private Integer number;

	// public method
	@Override
	public void validate() throws SystemException {
		// nothing to do
	}

	@Override
	protected boolean equals(DomainBase other) {
		ShohinIdoMeisaiDomain o = (ShohinIdoMeisaiDomain) other;
		return meisaiNumber.equals(o.meisaiNumber);
	}

	@Override
	public int hashCode() {
		return generateHashCode(meisaiNumber);
	}

	/**
	 * 明細番号で比較する.
	 * 
	 */
	@Override
	public int compareTo(ShohinIdoMeisaiDomain o) {
		return this.meisaiNumber.compareTo(o.meisaiNumber);
	}
}
