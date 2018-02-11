package com.showka.service.crud.u17.i;

import com.showka.domain.BushoDomain;

public interface BushoDateCrudService {

	/**
	 * 部署の営業日を次の日に更新します。
	 * 
	 * @param busho
	 *            部署
	 */
	public void toNextEigyoDate(BushoDomain busho);

}
