package com.showka.service.crud.u05.i;

import com.showka.domain.Urikake;
import com.showka.service.crud.CrudService;
import com.showka.value.EigyoDate;

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
	 * 入金予定日更新.
	 * 
	 * @param urikake
	 *            売掛
	 * @param updatedNyukinYoteiDate
	 *            更新後入金予定日
	 */
	void updateNyukinYoteiDate(Urikake urikake, EigyoDate updatedNyukinYoteiDate);
}
