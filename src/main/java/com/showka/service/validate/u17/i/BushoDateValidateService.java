package com.showka.service.validate.u17.i;

import com.showka.value.EigyoDate;

public interface BushoDateValidateService {

	/**
	 * 締め処理可能か検証する.
	 * 
	 * @param bushoCode
	 *            部署コード
	 * @param today
	 *            締め処理対象の営業日
	 */
	public void validateForClosing(String bushoCode, EigyoDate today);
}
