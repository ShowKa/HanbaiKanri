package com.showka.domain.z00;

import java.math.BigDecimal;

import com.showka.domain.DomainBase;
import com.showka.system.exception.SystemException;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Shohin extends DomainBase {

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
		Shohin o = (Shohin) other;
		return code.equals(o.code);
	}

	@Override
	public int hashCode() {
		return code.hashCode();
	}

}
