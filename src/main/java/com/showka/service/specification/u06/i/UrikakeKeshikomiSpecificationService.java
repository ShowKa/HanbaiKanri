package com.showka.service.specification.u06.i;

import com.showka.domain.u06.Urikake;
import com.showka.domain.u08.Keshikomi;
import com.showka.value.AmountOfMoney;

public interface UrikakeKeshikomiSpecificationService {
	/**
	 * 消込後の残高を取得する。
	 * 
	 * <pre>
	 * ただし確定（DB登録済み）した消込のみが残高計算の対象となる。
	 * </pre>
	 * 
	 * @param urikake
	 *            売掛
	 * @return 売掛金額.残高
	 */
	public AmountOfMoney getZandakaOf(Urikake urikake);

	/**
	 * 消込時の残高を取得する。
	 * 
	 * <pre>
	 * 引数で指定した消込を行う直前の残高。
	 * 確定（DB登録済み）した消込のみが残高計算の対象となる。
	 * </pre>
	 * 
	 * @param urikake
	 *            売掛
	 * @return 売掛金額.残高
	 */
	public AmountOfMoney getZandakaAsOfKeshikomi(Urikake urikake, Keshikomi keshikomi);
}
