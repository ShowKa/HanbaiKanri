package com.showka.service.crud.u05.i;

import com.showka.domain.UriageCancelDomain;
import com.showka.domain.UriageDomain;

public interface UriageCancelCrudService {

	/**
	 * 売上取消の登録.
	 * 
	 * @param domain
	 *            売上ドメイン
	 */
	public void save(UriageDomain domain);

	/**
	 * ドメイン取得
	 * 
	 * @param pk
	 *            主キー
	 * @return 売上キャンセルドメイン
	 */
	public UriageCancelDomain getDomain(String uriageId);

}
