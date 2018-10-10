package com.showka.service.crud.u08.i;

import com.showka.domain.u17.BushoNyukin;
import com.showka.domain.z00.Busho;
import com.showka.value.EigyoDate;

public interface NyukinKeijoCrudService {

	/**
	 * 入金計上.
	 * 
	 * <pre>
	 * 下記条件を満たす入金を入金計上テーブルに登録する。
	 * - 部署=引数.部署
	 * - 入金日<= 引数.日付
	 * - 未計上
	 * </pre>
	 * 
	 * @param busho
	 *            部署
	 * @param date
	 *            日付
	 */
	public void keijo(Busho busho, EigyoDate keijoDate);

	/**
	 * 部署の入金を取得する。
	 * 
	 * @param busho
	 *            部署
	 * @param date
	 *            計上日
	 * @return
	 */
	public BushoNyukin getBushoNyukin(Busho busho, EigyoDate keijoDate);
}
