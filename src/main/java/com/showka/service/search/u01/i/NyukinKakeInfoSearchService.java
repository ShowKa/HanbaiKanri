package com.showka.service.search.u01.i;

import java.util.Collection;
import java.util.List;

import com.showka.domain.u01.Kokyaku;
import com.showka.domain.z00.Busho;
import com.showka.value.ShimeDate;

public interface NyukinKakeInfoSearchService {

	/**
	 * 締日の顧客取得.
	 * 
	 * <pre>
	 * - 締日が到来している顧客である。
	 *     - 顧客:-入金掛売情報.締日 ∈ $締日リスト
	 * - 顧客.主幹部署=引数.部署
	 * </pre>
	 * 
	 * @param busho
	 *            主管部署
	 * @param shimeDates
	 *            締日リスト
	 * @return 顧客 list
	 */
	public List<Kokyaku> getKokyakuOnShimeDate(Busho busho, Collection<ShimeDate> shimeDates);

	/**
	 * 締日の顧客取得.
	 * 
	 * <pre>
	 * - 締日が到来している顧客である。
	 *     - 顧客:-入金掛売情報.締日 = 締日
	 * - 顧客.主観部署=引数.部署
	 * </pre>
	 * 
	 * @param busho
	 *            主管部署
	 * @param shimeDate
	 *            締日
	 * @return 顧客 list
	 */
	public List<Kokyaku> getKokyakuOnShimeDate(Busho busho, ShimeDate shimeDate);
}
