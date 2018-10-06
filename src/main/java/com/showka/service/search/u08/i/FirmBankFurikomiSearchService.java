package com.showka.service.search.u08.i;

import java.util.List;

import com.showka.domain.u08.FBFurikomiMatchingResult;
import com.showka.domain.z00.Busho;
import com.showka.value.TheDate;

public interface FirmBankFurikomiSearchService {

	/**
	 * 引数.部署におけるFB振込のうち、振分とマッチするものを検索
	 * 
	 * @param busho
	 *            部署
	 * @param date
	 *            伝送日付
	 * @return FB振込IDのリスト
	 */
	public FBFurikomiMatchingResult searchMatched(Busho busho, TheDate date);

	/**
	 * FB振込のうち、マッチする振り分けデータがないものを検索
	 * 
	 * <pre>
	 * 前提条件：
	 * 振分とマッチ失敗したデータのみを抽出。
	 * 本処理を呼ぶ前に、全部署のマッチング処理を完了させる必要がある。
	 * </pre>
	 * 
	 * @param date
	 *            伝送日付
	 * @return FB振込IDのリスト
	 */
	public List<String> searchUnmatched(TheDate date);
}
