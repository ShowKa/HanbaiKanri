package com.showka.domain.u08;

import com.showka.domain.DomainBase;
import com.showka.domain.u06.Urikake;
import com.showka.system.exception.SystemException;
import com.showka.value.AmountOfMoney;
import com.showka.value.EigyoDate;
import com.showka.value.TheTimestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Keshikomi extends DomainBase {

	/** 入金. */
	private Nyukin nyukin;

	/** 売掛. */
	private Urikake urikake;

	/** 消込日. */
	private EigyoDate date;

	/** タイムスタンプ(営業日とは関係のないシステム日付). */
	private TheTimestamp timestamp;

	/** 消込額. */
	private AmountOfMoney kingaku;

	// public method
	// getter
	/**
	 * 入金ID取得.
	 * 
	 * @return 入金ID
	 */
	public String getNyukinId() {
		return nyukin.getRecordId();
	}

	/**
	 * 売掛ID取得.
	 * 
	 * @return 売掛ID
	 */
	public String getUrikakeId() {
		return urikake.getRecordId();
	}

	// override
	@Override
	public void validate() throws SystemException {
		// nothing to do
	}

	@Override
	protected boolean equals(DomainBase other) {
		Keshikomi o = (Keshikomi) other;
		return this.getRecordId().equals(o.getRecordId());
	}

	@Override
	public int hashCode() {
		return generateHashCode(this.getRecordId());
	}
}
