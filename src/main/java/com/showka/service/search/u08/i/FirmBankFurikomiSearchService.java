package com.showka.service.search.u08.i;

import java.util.List;

import com.showka.domain.Busho;
import com.showka.domain.FBFurikomiMatchingResult;
import com.showka.value.TheDate;

public interface FirmBankFurikomiSearchService {

	/**
	 * 引数.部署におけるFB振込のうち、振分とマッチするものを検索
	 * 
	 * @param busho
	 *            部署
	 * @return FB振込IDのリスト
	 */
	public FBFurikomiMatchingResult searchMatched(Busho busho, TheDate date);

	/**
	 * 引数.部署におけるFB振込のうち、マッチする振り分けデータがないものを検索
	 * 
	 * @param busho
	 *            部署
	 * @return FB振込IDのリスト
	 */

	public List<String> searchUnmatched(Busho busho, TheDate date);
}
