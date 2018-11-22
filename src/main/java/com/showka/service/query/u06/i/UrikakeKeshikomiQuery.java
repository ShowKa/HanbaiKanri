package com.showka.service.query.u06.i;

import com.showka.domain.u06.Urikake;
import com.showka.domain.u06.UrikakeKeshikomi;
import com.showka.domain.u08.Keshikomi;
import com.showka.value.AmountOfMoney;

public interface UrikakeKeshikomiQuery {

	/**
	 * 売掛消込取得.
	 * 
	 * @param urikakeId
	 *            売掛ID
	 * @return 売掛消込
	 */
	public UrikakeKeshikomi get(String urikakeId);

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
	public AmountOfMoney getAsOf(Urikake urikake, Keshikomi keshikomi);
}
