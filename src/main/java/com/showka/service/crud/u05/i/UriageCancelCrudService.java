package com.showka.service.crud.u05.i;

import com.showka.domain.UriageCancel;
import com.showka.domain.Uriage;

public interface UriageCancelCrudService {

	/**
	 * 売上取消の登録.
	 * 
	 * @param domain
	 *            売上ドメイン
	 */
	public void save(Uriage domain);

	/**
	 * ドメイン取得
	 * 
	 * @param pk
	 *            主キー
	 * @return 売上キャンセルドメイン
	 */
	public UriageCancel getDomain(String uriageId);

}
