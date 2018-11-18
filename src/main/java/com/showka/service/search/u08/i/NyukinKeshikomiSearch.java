package com.showka.service.search.u08.i;

import java.util.List;

import com.showka.domain.u08.NyukinKeshikomi;

public interface NyukinKeshikomiSearch {

	/**
	 * 入金検索.
	 * 
	 * @param param
	 *            入金パラメータ.
	 * @return 入金
	 */
	public List<NyukinKeshikomi> search(NyukinKeshikomiSearchParm param);

}
