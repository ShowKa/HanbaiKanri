package com.showka.service.search.u07.i;

import java.util.List;

import com.showka.domain.Busho;
import com.showka.domain.Kokyaku;
import com.showka.domain.Seikyu;

public interface SeikyuSearchService {

	/**
	 * 引数.部署の請求を全て取得する
	 * 
	 * <pre>
	 * 対象:
	 * ・入金完了しておらず、現在有効の請求
	 * </pre>
	 * 
	 * @param kokyaku
	 *            顧客
	 * @return 請求リスト
	 */
	public List<Seikyu> getAllOf(Busho busho);

	/**
	 * 引数.顧客への請求を全て取得する
	 * 
	 * @param kokyaku
	 *            顧客
	 * @return 請求リスト
	 */
	public List<Seikyu> getAllOf(Kokyaku kokyaku);
}
