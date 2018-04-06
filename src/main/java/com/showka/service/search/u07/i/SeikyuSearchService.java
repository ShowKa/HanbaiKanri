package com.showka.service.search.u07.i;

import java.util.List;

import com.showka.domain.Kokyaku;
import com.showka.domain.Seikyu;

public interface SeikyuSearchService {

	/**
	 * 引数.顧客への請求を全て取得する
	 * 
	 * @param kokyaku
	 *            顧客
	 * @return 請求リスト
	 */
	public List<Seikyu> getAllOf(Kokyaku kokyaku);
}
