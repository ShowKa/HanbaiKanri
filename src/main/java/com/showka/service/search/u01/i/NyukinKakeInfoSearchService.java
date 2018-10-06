package com.showka.service.search.u01.i;

import java.util.List;

import com.showka.domain.u01.Kokyaku;
import com.showka.domain.z00.Busho;
import com.showka.value.EigyoDate;

public interface NyukinKakeInfoSearchService {
	/**
	 * 締日の顧客取得.
	 * 
	 * @param busho
	 *            主管部署
	 * @param shimeDate
	 *            締日
	 * @return 顧客 list
	 */
	public List<Kokyaku> getKokyakuOnShimeDate(Busho busho, EigyoDate shimeDate);
}
