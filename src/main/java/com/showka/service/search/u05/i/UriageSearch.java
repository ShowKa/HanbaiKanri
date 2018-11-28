package com.showka.service.search.u05.i;

import java.util.List;

import com.showka.domain.u05.Uriage;

public interface UriageSearch {
	/**
	 * 売上検索.
	 * 
	 * @param criteria
	 *            検索条件
	 * @return 売上リスト
	 */
	public List<Uriage> search(UriageSearchCriteria criteria);
}
