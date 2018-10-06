package com.showka.service.crud.u05.i;

import com.showka.domain.u17.BushoUriage;
import com.showka.domain.z00.Busho;
import com.showka.value.TheDate;

public interface UriageKeijoCrudService {
	/**
	 * 
	 * 売上計上.
	 * 
	 * <pre>
	 * 1. 通常の売上の計上（プラス円）
	 * -> 売上キャンセルも含むが、0円で計上される。
	 * 2. 売上訂正の計上（マイナス円）
	 * </pre>
	 * 
	 * @param busho
	 *            計上部署.
	 * @param date
	 *            計上日
	 */
	public void keijo(Busho busho, TheDate date);

	/**
	 * 部署売上の集計を取得する.
	 * 
	 * @param busho
	 *            部署
	 * @param date
	 *            計上日
	 * @return 部署売上
	 */
	public BushoUriage getBushoUriage(Busho busho, TheDate date);
}