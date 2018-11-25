package com.showka.service.persistence.u05.i;

import com.showka.domain.z00.Busho;
import com.showka.value.EigyoDate;

public interface UriageKeijoPersistence {
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
	public void keijo(Busho busho, EigyoDate date);
}