package com.showka.domain;

import com.showka.system.exception.SystemException;
import com.showka.value.AmountOfMoney;
import com.showka.value.EigyoDate;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 売掛.
 *
 */
@AllArgsConstructor
@Getter
public class Urikake extends DomainBase {

	// private members
	/** 売上. */
	private Uriage uriage;

	/** 残高. */
	private AmountOfMoney zandaka;

	/** 入金予定日. */
	private EigyoDate nyukinYoteiDate;

	// public methods
	/**
	 * 現時点での入金額を取得.
	 * 
	 * @return 入金額
	 */
	public AmountOfMoney getPresentNyukinKingaku() {
		return uriage.getUriageGokeiKakaku().getZeikomi().subtract(zandaka);
	}

	/**
	 * 売上ID取得.
	 * 
	 * @return 売上ID
	 */
	public String getUriageId() {
		return uriage.getRecordId();
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
