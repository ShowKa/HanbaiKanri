package com.showka.service.crud.u05.i;

import com.showka.domain.Urikake;
import com.showka.service.crud.CrudService;

public interface UrikakeCrudService extends CrudService<Urikake, String> {

	/**
	 * 売掛が存在する場合のみ削除する。
	 * 
	 * <pre>
	 * 存在しない場合はなにもしない。
	 * </pre>
	 * 
	 * @param uriageId
	 *            売上ID
	 * @param version
	 *            排他制御用バージョン番号
	 */
	void deleteIfExists(String uriageId, Integer version);

	/**
	 * 売掛を前回の計上状態の値に差し戻す。
	 * 
	 * <pre>
	 * ただし未計上の場合はそのままレコード削除。
	 * 売上をrevertする前に呼び出す必要がある。
	 * </pre>
	 * 
	 * @param recordId
	 * @param urikakeVersion
	 */
	void revert(String uriageId, Integer version);
}
