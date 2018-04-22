package com.showka.service.search.u05.i;

import java.util.List;

import com.showka.domain.Uriage;
import com.showka.service.search.u05.UriageSearchCriteria;

public interface UriageSearchService {
	/**
	 * 売上検索.
	 * 
	 * @param criteria
	 *            検索条件
	 * @return 売上リスト
	 */
	public List<Uriage> search(UriageSearchCriteria criteria);
}
