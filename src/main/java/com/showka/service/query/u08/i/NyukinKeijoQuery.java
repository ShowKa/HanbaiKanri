package com.showka.service.query.u08.i;

import java.util.List;

import com.showka.domain.u08.Nyukin;
import com.showka.domain.z00.Busho;
import com.showka.value.EigyoDate;

public interface NyukinKeijoQuery {
	/**
	 * 未計上入金検索.
	 * 
	 * <pre>
	 * 下記条件を満たす入金を検索する。
	 * 入金部署 = 引数.入金部署
	 * 入金日 <= 引数.基準日
	 * </pre>
	 * 
	 * @param busho
	 *            入金部署
	 * @param kijunDate
	 *            基準日
	 * @return 未計上の入金リスト.
	 */
	List<Nyukin> searchNotDone(Busho busho, EigyoDate kijunDate);

	/**
	 * 計上済み入金検索.
	 * 
	 * <pre>
	 * 下記条件を満たす入金を検索する。
	 * 入金部署 = 引数.入金部署
	 * 計上日 = 引数.計上日
	 * </pre>
	 * 
	 * @param busho
	 *            入金部署
	 * @param keijoDate
	 *            計上日
	 * @return 計上済み入金リスト
	 */
	List<Nyukin> seach(Busho busho, EigyoDate keijoDate);

	/**
	 * 計上済判定.
	 * 
	 * @param nyukinId
	 *            入金ID
	 * @return 計上済の場合true
	 */
	public boolean keijoDone(String nyukinId);
}
