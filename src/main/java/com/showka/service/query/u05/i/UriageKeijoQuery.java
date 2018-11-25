package com.showka.service.query.u05.i;

import com.showka.domain.u05.Uriage;
import com.showka.domain.u17.BushoUriage;
import com.showka.domain.z00.Busho;
import com.showka.value.EigyoDate;

public interface UriageKeijoQuery {
	/**
	 * 計上済みか否かを判定する。
	 * 
	 * <pre>
	 * 計上済み判定の仕様
	 * 顧客の主観部署の営業日 > 売上の計上日
	 * </pre>
	 * 
	 * @param uriage
	 *            売上ドメイン
	 * @return true=計上済み
	 */
	public boolean hasDone(Uriage uriage);

	/**
	 * 部署売上の集計を取得する.
	 * 
	 * @param busho
	 *            部署
	 * @param date
	 *            計上日
	 * @return 部署売上
	 */
	public BushoUriage getBushoUriage(Busho busho, EigyoDate date);
}
