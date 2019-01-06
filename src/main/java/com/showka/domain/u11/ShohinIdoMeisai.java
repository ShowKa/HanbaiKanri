package com.showka.domain.u11;

import com.showka.domain.DomainMeisai;
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
public class ShohinIdoMeisai extends DomainMeisai {

	// private member
	/** 明細番号. */
	private Integer meisaiNumber;

	/** 商品. */
	private Shohin shohinDomain;

	/**
	 * 移動数.
	 * 
	 * <pre>
	 * 移動数が正の値のときに、在庫数が増減するか否かは商品移動区分に依存する。
	 * </pre>
	 */
	private Integer number;

	// override
	@Override
	public void validate() throws SystemException {
	}

}
