package com.showka.service.specification.u07.i;

import java.util.List;

import com.showka.domain.u01.Kokyaku;
import com.showka.domain.u07.SeikyuMeisai;
import com.showka.value.EigyoDate;

public interface SeikyuSpecification {
	/**
	 * 請求先の顧客取得.
	 * 
	 * @return 顧客
	 */
	public Kokyaku getKokyaku();

	/**
	 * 請求日取得.
	 * 
	 * @return 請求日
	 */
	public EigyoDate getSeikyuDate();

	/**
	 * 支払日取得.
	 * 
	 * @return 支払日
	 */
	public EigyoDate getShiharaiDate();

	/**
	 * 請求明細取得.
	 * 
	 * @return 請求明細
	 */
	public List<SeikyuMeisai> getSeikyuMeisai();
}
