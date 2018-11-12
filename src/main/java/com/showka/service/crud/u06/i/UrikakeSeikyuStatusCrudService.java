package com.showka.service.crud.u06.i;

/**
 * 売掛の請求状態を変更する。
 */
public interface UrikakeSeikyuStatusCrudService {

	/**
	 * 売掛を未請求状態とする。
	 * 
	 * @param urikakeId
	 *            売掛ID
	 */
	void toNotYet(String urikakeId);

	/**
	 * 売掛を未請求状態 - > 請求済 に変更する。
	 * 
	 * @param urikakeId
	 *            売掛ID
	 * @param seikyuId
	 *            請求ID
	 */
	void toDone(String urikakeId, String seikyuId);

	/**
	 * 売掛を settled(精算完了) -> 請求済or未請求状態 に戻す。
	 * 
	 * <pre>
	 * 消込完了等で削除対象となったレコードを復活させる。
	 * 売掛の最新請求を検索し、その請求を対象とする。
	 * ただし、対象レコードが既存の場合は何もせず処理終了。
	 * また、請求済みでない場合は未請求状態とする。
	 * </pre>
	 * 
	 * @param urikakeId
	 *            売掛ID
	 * @param seikyuId
	 *            請求ID
	 */
	void revert(String urikakeId);

	/**
	 * 売掛を settled（精算完了） とする。
	 * 
	 * @param urikakeId
	 *            売掛ID
	 */
	void toSettled(String urikakeId);
}
