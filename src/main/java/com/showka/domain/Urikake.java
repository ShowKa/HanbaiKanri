package com.showka.domain;

import com.showka.system.exception.SystemException;
import com.showka.value.EigyoDate;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 売掛.
 *
 */
@AllArgsConstructor
public class Urikake extends DomainBase {

	// private members
	/** 売上. */
	@Getter
	private Uriage uriage;

	/** 残高. */
	@Getter
	private Integer zandaka;

	/** 入金予定日. */
	@Getter
	private EigyoDate nyukinYoteiDate;

	// public methods
	/**
	 * 現時点での入金額を取得.
	 * 
	 * @return 入金額
	 */
	public Integer getPresentNyukinKingaku() {
		return uriage.getUriageGokeiKakaku().getZeikomiKakaku().intValue() - zandaka;
	}

	// override methods
	@Override
	public void validate() throws SystemException {
		// do nothing
	}

	@Override
	protected boolean equals(DomainBase other) {
		Urikake o = (Urikake) other;
		return uriage.equals(o.uriage);
	}

	@Override
	public int hashCode() {
		return uriage.hashCode();
	}
}
