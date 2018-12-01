package com.showka.service.persistence.u06.i;

import com.showka.domain.u06.Urikake;

public interface UrikakePersistence {

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
	// 引数は売掛型にすべき....
	void revert(String uriageId, Integer version);

	/**
	 * 売掛をキャンセル.
	 * 
	 * <pre>
	 * 下記の通り更新.
	 * 売掛金 = 0円
	 * </pre>
	 * 
	 * @param urikake
	 *            売掛
	 */
	void cancel(Urikake urikake);
}
