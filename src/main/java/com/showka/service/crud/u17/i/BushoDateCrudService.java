package com.showka.service.crud.u17.i;

public interface BushoDateCrudService {

	/**
	 * 部署の営業日を次の日に更新します。
	 * 
	 * @param bushoCode
	 *            部署コード
	 */
	public void toNextEigyoDate(String bushoCode);

}
