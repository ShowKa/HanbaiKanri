package com.showka.service.search.u05.i;

import java.util.List;

import com.showka.domain.Urikake;

public interface UrikakeSearchService {
	/**
	 * 顧客の売掛を取得.
	 * 
	 * <pre>
	 * 残高>0の売掛を全て取得する。
	 * 
	 * <pre>
	 * 
	 * @param kokyakuCode
	 *            顧客コード
	 * @return 売掛 entity list
	 */
	public List<Urikake> getUrikakeOfKokyaku(String kokyakuCode);
}
