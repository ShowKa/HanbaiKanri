package com.showka.service.search.u05.i;

import java.util.List;

import com.showka.domain.Busho;
import com.showka.entity.RUriage;
import com.showka.value.TheDate;

public interface UriageRirekiSearchService {
	/**
	 * 計上日と部署を指定しての売上を検索します。
	 * 
	 * @param busho
	 *            顧客の主管理部署
	 * @param date
	 *            計上日
	 * @return 未計上売上リスト（売上履歴）
	 */
	public List<RUriage> search(Busho busho, TheDate date);
}
