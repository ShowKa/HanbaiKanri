package com.showka.service.persistence.u05.i;

import com.showka.entity.TUriagePK;

public interface UriagePersistence {

	/**
	 * 売上キャンセル.
	 * 
	 * @param pk
	 *            売上主キー
	 * @param version
	 *            version for OCC
	 */
	public void cancel(TUriagePK pk, int version);

	/**
	 * 売上を前回の計上状態の値に差し戻す。
	 * 
	 * <pre>
	 * 売上履歴のデータを元に前回の状態に戻す。
	 * </pre>
	 * 
	 * @param pk
	 *            売上主キー
	 * @param version
	 *            version for OCC
	 */
	public void revert(TUriagePK pk, int version);

}
