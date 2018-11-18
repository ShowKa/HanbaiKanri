package com.showka.service.persistence.u06.i;

import com.showka.domain.u06.Urikake;
import com.showka.service.persistence.Persistence;

public interface UrikakePersistence extends Persistence<Urikake, String> {

	/**
	 * 売上IDで売掛を取得.
	 * 
	 * <pre>
	 * 売<b style="color:red">掛</b>IDではないので要注意
	 * </pre>
	 * 
	 * @param uriageId
	 *            売上ID
	 * @return 売掛
	 */
	@Override
	Urikake getDomain(String uriageId);

	/**
	 * 売掛を前回の計上状態の値に差し戻す。
	 * 
	 * <pre>
	 * 売上履歴のデータを元に金額等を前回の状態に戻す。
	 * 売上履歴が1件（計上後、更新されたことがない。あるいは一度も計上されてない）の場合はなにもしない。
	 * </pre>
	 * 
	 * @param uriageId
	 *            売上ID
	 * @param version
	 *            バージョン for OCC
	 */
	void revert(String uriageId, Integer version);

	/**
	 * 売掛IDで売掛を取得.
	 * 
	 * @param urikakeId
	 *            売掛ID
	 * @return 売掛
	 */
	Urikake getDomainById(String urikakeId);
}
