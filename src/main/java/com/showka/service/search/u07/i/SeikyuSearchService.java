package com.showka.service.search.u07.i;

import java.util.List;
import java.util.Optional;

import com.showka.domain.u01.Kokyaku;
import com.showka.domain.u07.Seikyu;
import com.showka.domain.z00.Busho;

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

	/**
	 * 売掛の請求履歴を取得する。
	 * 
	 * @param urikakeId
	 *            売掛ID
	 * @return 請求リスト
	 */
	public List<Seikyu> getHistoryOf(String urikakeId);

	/**
	 * 売掛の請求履歴のうち、最近のものを取得する。
	 * 
	 * <pre>
	 * 最近 = 請求日が最も大きい
	 * </pre>
	 * 
	 * @param urikakeId
	 *            売掛ID
	 * @return 最新の請求
	 */
	public Optional<Seikyu> getNewestOf(String urikakeId);
}
