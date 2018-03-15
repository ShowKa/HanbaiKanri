package com.showka.service.crud.u17.i;

import com.showka.domain.Busho;
import com.showka.value.EigyoDate;

public interface BushoDateCrudService {

	/**
	 * 部署の営業日を次の日に更新します。
	 * 
	 * @param busho
	 *            部署
	 */
	public void toNextEigyoDate(Busho busho);

	/**
	 * 引数で指定した次の営業日を取得する。
	 * 
	 * @param busho
	 *            部署
	 * @param eigyoDate
	 *            営業日
	 * @return 次の営業日
	 */
	public EigyoDate getNext(Busho busho, EigyoDate eigyoDate);

}
