package com.showka.domain.u07;

import com.showka.domain.DomainMeisai;
import com.showka.domain.u06.Urikake;
import com.showka.system.exception.SystemException;
import com.showka.value.AmountOfMoney;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 請求明細.
 * 
 */
@AllArgsConstructor
@Getter
public class SeikyuMeisai extends DomainMeisai {

	// private member
	/** 売掛. */
	private Urikake urikake;

	/** 請求金額(=請求時の売掛残高). */
	private AmountOfMoney kingaku;

	// getter
	/**
	 * 売掛のIDを取得.
	 * 
	 * @return 売掛ID
	 */
	public String getUrikakeId() {
		return urikake.getRecordId();
	}

	// override
	@Override
	public void validate() throws SystemException {
	}

	@Override
	protected Integer getMeisaiNumber() {
		// 明細番号を持たないため、一意となるhashを返却。
		return urikake.hashCode();
	}
}
