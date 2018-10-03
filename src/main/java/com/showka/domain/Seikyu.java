package com.showka.domain;

import java.util.List;

import com.showka.kubun.NyukinHohoKubun;
import com.showka.system.exception.SystemException;
import com.showka.value.AmountOfMoney;
import com.showka.value.EigyoDate;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 請求.
 *
 */
@AllArgsConstructor
@Getter
public class Seikyu extends DomainBase {

	// private members
	/** 顧客. */
	private Kokyaku kokyaku;

	/** 請求日. */
	private EigyoDate seikyuDate;

	/** 支払期日. */
	private EigyoDate shiharaiDate;

	/** 入金方法区分（請求時点での顧客の入金方法） */
	private NyukinHohoKubun nyukinHohoKubun;

	/** 請求担当部署. */
	private Busho tantoBusho;

	/** 請求明細. */
	private List<SeikyuMeisai> seikyuMeisai;

	// public methods
	/**
	 * 請求の合計金額を取得する.
	 * 
	 * <pre>
	 * 請求時の各売掛の残高の合計。
	 * </pre>
	 * 
	 * @return 合計金額
	 */
	public AmountOfMoney getGokeiKingaku() {
		int gokei = seikyuMeisai.stream().mapToInt(meisai -> {
			return meisai.getKingaku().intValue();
		}).sum();
		return new AmountOfMoney(gokei);
	}

	/**
	 * 顧客ID取得.
	 * 
	 * @return 顧客ID
	 */
	public String getKokyakuId() {
		return kokyaku.getRecordId();
	}

	// override
	@Override
	public void validate() throws SystemException {
		// nothing to do
	}

	@Override
	protected boolean equals(DomainBase other) {
		Seikyu o = (Seikyu) other;
		return kokyaku.equals(o.kokyaku) && seikyuDate.equals(o.seikyuDate);
	}

	@Override
	public int hashCode() {
		return generateHashCode(kokyaku, seikyuDate);
	}
}
