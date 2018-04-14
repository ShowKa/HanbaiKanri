package com.showka.domain;

import com.showka.kubun.NyukinHohoKubun;
import com.showka.system.exception.SystemException;
import com.showka.value.AmountOfMoney;
import com.showka.value.EigyoDate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Nyukin extends DomainBase {

	/** 顧客 */
	private Kokyaku kokyaku;

	/** 部署 */
	private Busho busho;

	/** 入金日. */
	private EigyoDate date;

	/** 入金方法区分. */
	private NyukinHohoKubun nyukinHohoKubun;

	/** 金額. */
	private AmountOfMoney kingaku;

	// override
	@Override
	public void validate() throws SystemException {
		// nothing to do
	}

	@Override
	protected boolean equals(DomainBase other) {
		Nyukin o = (Nyukin) other;
		return this.getRecordId().equals(o.getRecordId());
	}

	@Override
	public int hashCode() {
		return generateHashCode(this.getRecordId());
	}

	/**
	 * 部署ID取得.
	 * 
	 * @return 部署ID
	 */
	public String getBushoId() {
		return busho.getRecordId();
	}

	/**
	 * 顧客ID取得.
	 * 
	 * @return 顧客ID
	 */
	public String getKokyakuId() {
		return kokyaku.getRecordId();
	}
}
