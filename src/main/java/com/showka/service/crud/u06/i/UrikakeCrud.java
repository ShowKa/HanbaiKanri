package com.showka.service.crud.u06.i;

import com.showka.domain.u06.Urikake;
import com.showka.service.crud.Crud;

public interface UrikakeCrud extends Crud<Urikake, String> {

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
	 * 売掛IDで売掛を取得.
	 * 
	 * @param urikakeId
	 *            売掛ID
	 * @return 売掛
	 */
	Urikake getDomainById(String urikakeId);

	/**
	 * 存在すれば削除 .
	 * 
	 * @param uriageId
	 *            売上ID
	 * @param version
	 *            排他制御バージョン
	 */
	void deleteIfExists(String uriageId, Integer version);
}
