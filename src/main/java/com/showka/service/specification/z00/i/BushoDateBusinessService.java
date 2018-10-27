package com.showka.service.specification.z00.i;

import com.showka.domain.z00.Busho;
import com.showka.value.EigyoDate;
import com.showka.value.TheDate;

public interface BushoDateBusinessService {

	/**
	 * 引数で指定した営業日の次の営業日を取得する。
	 * 
	 * @param busho
	 *            部署
	 * @param eigyoDate
	 *            営業日
	 * @return 次の営業日
	 */
	public EigyoDate getNext(Busho busho, EigyoDate eigyoDate);

	/**
	 * 営業日判定.
	 * 
	 * @param date
	 *            日付
	 * @return 引数.日付 = 部署の営業日として正しいならtrue
	 */
	public boolean isEigyoDate(Busho busho, TheDate date);
}
