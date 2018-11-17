package com.showka.service.persistence.u08.i;

import java.util.Optional;

import com.showka.domain.u01.Kokyaku;
import com.showka.domain.u08.Shukin;
import com.showka.service.persistence.Persistence;
import com.showka.value.EigyoDate;

public interface ShukinPersistence extends Persistence<Shukin, String> {

	/**
	 * 集金取得.
	 * 
	 * @param kokyaku
	 *            顧客
	 * @param nyukinDate
	 *            入金日
	 * @param denpyoNumber
	 *            伝票番号
	 * @return 集金
	 */
	public Shukin get(Kokyaku kokyaku, EigyoDate nyukinDate, String denpyoNumber);

	/**
	 * 集金取得.
	 * 
	 * @param kokyaku
	 *            顧客
	 * @param nyukinDate
	 *            入金日
	 * @param denpyoNumber
	 *            伝票番号
	 * @return 集金
	 */
	public Optional<Shukin> getIfExists(Kokyaku kokyaku, EigyoDate nyukinDate, String denpyoNumber);

	/**
	 * 存在検証.
	 * 
	 * @param kokyaku
	 *            顧客
	 * @param nyukinDate
	 *            入金日
	 * @param denpyoNumber
	 *            伝票番号
	 * @return 存在する場合、true
	 */
	public boolean exists(Kokyaku kokyaku, EigyoDate nyukinDate, String denpyoNumber);

}
