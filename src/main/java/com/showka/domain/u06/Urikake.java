package com.showka.domain.u06;

import com.showka.domain.DomainBase;
import com.showka.domain.u05.Uriage;
import com.showka.system.exception.SystemException;
import com.showka.value.AmountOfMoney;
import com.showka.value.TheDate;

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

	/** 金額（消込後の残高ではない！）. */
	private AmountOfMoney kingaku;

	/** 入金予定日（営業日である必要はない）. */
	private TheDate nyukinYoteiDate;

	// public methods
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
