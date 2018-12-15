package com.showka.domain.u08;

import com.showka.domain.DomainRoot;
import com.showka.domain.u01.Kokyaku;
import com.showka.domain.z00.Busho;
import com.showka.kubun.NyukinHohoKubun;
import com.showka.system.exception.SystemException;
import com.showka.value.AmountOfMoney;
import com.showka.value.EigyoDate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Nyukin extends DomainRoot {

	/** 顧客 */
	private Kokyaku kokyaku;

	/** 部署 */
	private Busho busho;

	/** 入金日（入金された営業日、計上日ではない。）. */
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
	protected boolean equals(DomainRoot other) {
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
