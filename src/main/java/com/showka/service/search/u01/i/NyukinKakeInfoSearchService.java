package com.showka.service.search.u01.i;

import java.util.List;

import com.showka.domain.Busho;
import com.showka.domain.Kokyaku;
import com.showka.value.EigyoDate;

public interface NyukinKakeInfoSearchService {
	/**
	 * 締日の顧客取得.
	 * 
	 * @param busho
	 *            主管部署
	 * @param shimeDate
	 *            締日
	 * @return 顧客 entity list
	 */
	public List<Kokyaku> getKokyakuOnShimeDate(Busho busho, EigyoDate shimeDate);
}
