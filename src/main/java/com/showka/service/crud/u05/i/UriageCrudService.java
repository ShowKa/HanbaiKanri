package com.showka.service.crud.u05.i;

import com.showka.domain.u05.Uriage;
import com.showka.entity.TUriagePK;
import com.showka.service.crud.CrudService;

public interface UriageCrudService extends CrudService<Uriage, TUriagePK> {

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

	/**
	 * 売上を取得.
	 * 
	 * @param kokyakuCode
	 *            顧客コード
	 * @param denpyoNumber
	 *            伝票ナンバー
	 * @return 売上
	 */
	public Uriage getDomain(String kokyakuCode, String denpyoNumber);

}
