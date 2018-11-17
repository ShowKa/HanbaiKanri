package com.showka.service.search.u06.i;

import java.util.List;

import com.showka.domain.u01.Kokyaku;
import com.showka.domain.u06.Urikake;

public interface UrikakeSearchService {

	/**
	 * 売掛Tableから$顧客の請求対象の$売掛リスト取得.
	 * 
	 * <pre>
	 * - a & (b or c)
	 * 
	 * - a.$顧客の売掛である
	 *     - 売掛:-売上:-顧客 = $顧客
	 * - b.未請求
	 * - c.請求中 & 入金延滞している
	 *    - 請求中売掛:-請求.支払日 <= $営業日
	 * 
	 * </pre>
	 * 
	 * @param kokyaku
	 *            顧客
	 * @return 売掛リスト
	 */
	public List<Urikake> getUrikakeForSeikyu(Kokyaku kokyaku);

	/**
	 * 顧客の未済売掛を取得する.
	 * 
	 * @param kokyaku
	 *            顧客
	 * @return 売掛リスト
	 */
	public List<Urikake> getUrikakeNotSettled(Kokyaku kokyaku);
}
