package com.showka.service.search.u08.i;

import java.util.List;

import com.showka.domain.Busho;
import com.showka.domain.FBFurikomiMatched;
import com.showka.value.TheDate;

public interface FirmBankFurikomiSearchService {

	/**
	 * 引数.部署におけるFB振込のうち、振分とマッチするものを検索
	 * 
	 * @param busho
	 *            部署
	 * @return FB振込IDのリスト
	 */
	public List<FBFurikomiMatched> searchMatched(Busho busho, TheDate date);

	/**
	 * 引数.部署,伝送日付のFB振込において振込依頼人が重複しているものを検索
	 * 
	 * @param busho
	 *            部署
	 * @param date
	 *            伝送日付
	 * @return FB振込IDのリスト
	 */
	public List<String> searchIraininRepetition(Busho busho, TheDate date);

	/**
	 * 引数.部署におけるFB振込のうち、マッチする振り分けデータがないものを検索
	 * 
	 * @param busho
	 *            部署
	 * @return FB振込IDのリスト
	 */

	public List<String> searchNotMatched(Busho busho, TheDate date);

	/**
	 * 引数.部署におけるFB振込のうち、マッチする振分データが複数存在するものを検索
	 * 
	 * @param busho
	 *            部署
	 * @return FB振込IDのリスト
	 */
	public List<String> searchMultipleMatched(Busho busho, TheDate date);
}
