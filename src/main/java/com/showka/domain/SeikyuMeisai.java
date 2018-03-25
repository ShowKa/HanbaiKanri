package com.showka.domain;

import com.showka.system.exception.SystemException;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 請求明細.
 * 
 */
@AllArgsConstructor
@Getter
public class SeikyuMeisai extends DomainBase {

	// private member
	/** 売掛. */
	private Urikake urikake;

	/** 請求金額(=請求時の売掛残高). */
	private Integer kingaku;

	// public method

	// override
	@Override
	public void validate() throws SystemException {
		// nothing to do
	}

	@Override
	protected boolean equals(DomainBase other) {
		SeikyuMeisai o = (SeikyuMeisai) other;
		return urikake.equals(o.urikake);
	}

	@Override
	public int hashCode() {
		return urikake.hashCode();
	}
}
