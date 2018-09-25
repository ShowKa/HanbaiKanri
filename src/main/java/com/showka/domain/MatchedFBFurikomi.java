package com.showka.domain;

import com.showka.system.exception.SystemException;
import com.showka.value.AmountOfMoney;
import com.showka.value.EigyoDate;
import com.showka.value.TheDate;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * マッチング済みFB振込
 */
@AllArgsConstructor
@Getter
public class MatchedFBFurikomi extends DomainAggregation {

	/** FB振込ID */
	private String fBFurikomiId;

	/** 伝送日付. */
	private TheDate transmissionDate;

	/** 振込金額 */
	private AmountOfMoney kingaku;

	/** 請求. */
	private Seikyu seikyu;

	// getter
	/**
	 * 請求担当部署の営業日取得.
	 * 
	 * @return 請求担当部署営業日.
	 */
	public EigyoDate getSeikyuTantoBushoEigyoDate() {
		return this.seikyu.getTantoBusho().getEigyoDate();
	}

	// override
	@Override
	public int hashCode() {
		return this.fBFurikomiId.hashCode();
	}

	@Override
	public void validate() throws SystemException {
	}

	@Override
	protected boolean equals(DomainAggregation other) {
		// if (this == other) {
		// return true;
		// }
		MatchedFBFurikomi o = (MatchedFBFurikomi) other;
		return this.fBFurikomiId.equals(o.fBFurikomiId);
	}
}
