package com.showka.domain.u11;

import com.showka.domain.DomainBase;
import com.showka.domain.z00.Shohin;
import com.showka.system.exception.SystemException;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 商品移動明細
 * 
 * @author ShowKa
 *
 */
@AllArgsConstructor
@Getter
public class ShohinIdoMeisai extends DomainBase implements Comparable<ShohinIdoMeisai> {

	// private member
	/** 明細番号. */
	private Integer meisaiNumber;

	/** 商品. */
	private Shohin shohinDomain;

	/**
	 * 移動数.
	 * 
	 * <pre>
	 * 在庫数の増減にかかわらず常に正の数。増減の正負は商品移動ドメインで管理
	 * </pre>
	 */
	private Integer number;

	// public method
	@Override
	public void validate() throws SystemException {
		// nothing to do
	}

	@Override
	protected boolean equals(DomainBase other) {
		ShohinIdoMeisai o = (ShohinIdoMeisai) other;
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
	public int compareTo(ShohinIdoMeisai o) {
		return this.meisaiNumber.compareTo(o.meisaiNumber);
	}
}