package com.showka.service.search.u05.i;

import java.util.List;

import com.showka.domain.Busho;
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
	 * @return 売掛 list
	 */
	public List<Urikake> getUrikakeOfKokyaku(String kokyakuCode);

	/**
	 * 残高あり、請求済みの売掛をすべて取得する.
	 * 
	 * @param busho
	 *            主管部署
	 */
	public List<Urikake> search(Busho busho);

}
