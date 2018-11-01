package com.showka.service.specification.u07.i;

import java.util.Set;

import com.showka.domain.z00.Busho;
import com.showka.value.EigyoDate;
import com.showka.value.ShimeDate;

public interface ShimeDateBusinessService {

	/**
	 * 営業日を基準にして$締日リスト取得.
	 * 
	 * <pre>
	 * - $営業日を含む。 
	 *     - ただし$営業日.年月日.日=31の場合、31の値は含めない。 
	 * - $営業日.翌日が非営業日の場合、翌日も$締日リストに含む。
	 * - $営業日.翌々日が非営業日の場合、翌々日も$締日リストに含める（以降繰り返し）。
	 *     - ただし営業日となった場合は、それ以上含める必要はない。
	 *     - 31も含めない。
	 * - $営業日=月末日 & $営業日<30の場合、$営業日〜30までの値も含める。
	 * </pre>
	 * 
	 * @param busho
	 *            対象の部署
	 * @param date
	 *            基準の営業日
	 * @return 締日リスト
	 */
	Set<ShimeDate> getShimeDate(Busho busho, EigyoDate date);
}
