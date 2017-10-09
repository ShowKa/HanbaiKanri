package com.showka.domain;

import java.math.BigDecimal;

import com.showka.system.exception.SystemException;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ShohinDomain extends DomainBase {

	// private member
	/** 商品コード. */
	private String code;

	/** 商品名. */
	private String name;

	/** 商品標準単価. */
	private BigDecimal hyojunTanka;

	@Override
	public void validate() throws SystemException {
		// nothing to do
	}

	@Override
	protected boolean equals(DomainBase other) {
		ShohinDomain o = (ShohinDomain) other;
		return code.equals(o.code);
	}

	@Override
	public int hashCode() {
		return code.hashCode();
	}

}
