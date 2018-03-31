package com.showka.service.specification.u07.i;

import com.showka.domain.Busho;
import com.showka.domain.Kokyaku;
import com.showka.value.EigyoDate;

public interface SeikyuSpecification {
	/**
	 * 請求を行う.
	 * 
	 * <pre>
	 * ・締日を迎えた顧客を取得
	 * ・顧客の売掛を取得
	 * ・請求データ作成
	 * ・請求した売掛の入金予定日を更新
	 * </pre>
	 * 
	 * @param busho
	 *            対象部署
	 * @param shimeDate
	 *            締日
	 */
	public void seikyu(Busho busho, EigyoDate shimeDate);

	/**
	 * 請求を行う.
	 * 
	 * <pre>
	 * ・顧客の売掛を取得
	 * ・請求データ作成
	 * ・請求した売掛の入金予定日を更新
	 * </pre>
	 * 
	 * @param kokyaku
	 *            対象顧客
	 * @param shimeDate
	 *            締日
	 */
	public void seikyu(Kokyaku kokyaku, EigyoDate shimeDate);
}
