package com.showka.service.persistence.u17.i;

import com.showka.domain.z00.Busho;

public interface BushoDatePersistence {

	/**
	 * 部署の営業日を次の日に更新します。
	 * 
	 * @param busho
	 *            部署
	 */
	public void toNextEigyoDate(Busho busho);
}
