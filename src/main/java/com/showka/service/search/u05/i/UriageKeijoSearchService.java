package com.showka.service.search.u05.i;

import java.util.List;

import com.showka.domain.Busho;
import com.showka.entity.RUriageKeijo;
import com.showka.value.TheDate;

public interface UriageKeijoSearchService {
	/**
	 * 指定した計上日の部署売上計上を取得.
	 * 
	 * @param busho
	 *            部署
	 * @param date
	 *            計上日
	 * @return 売上計上
	 */
	List<RUriageKeijo> search(Busho busho, TheDate date);
}
