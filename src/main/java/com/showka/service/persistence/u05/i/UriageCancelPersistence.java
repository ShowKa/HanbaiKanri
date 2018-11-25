package com.showka.service.persistence.u05.i;

import com.showka.domain.u05.Uriage;

public interface UriageCancelPersistence {

	/**
	 * 売上取消の登録.
	 * 
	 * @param domain
	 *            売上ドメイン
	 */
	public void save(Uriage domain);
}
