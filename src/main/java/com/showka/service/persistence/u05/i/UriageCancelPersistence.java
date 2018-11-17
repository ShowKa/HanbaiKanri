package com.showka.service.persistence.u05.i;

import com.showka.domain.u05.Uriage;
import com.showka.domain.u05.UriageCancel;

public interface UriageCancelPersistence {

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
