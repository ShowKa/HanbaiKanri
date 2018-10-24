package com.showka.service.search.u06.i;

import java.util.List;

import com.showka.domain.u06.Urikake;

public interface UrikakeSearchService {
	/**
	 * 顧客の売掛を取得.
	 * 
	 * <pre>
	 * 消込未完了の売掛を全て取得する。
	 * 
	 * <pre>
	 * 
	 * @param kokyakuId
	 *            顧客ID
	 * @return 売掛リスト
	 */
	public List<Urikake> getUrikakeOfKokyaku(String kokyakuId);
}
