package com.showka.service.persistence.u05.i;

import com.showka.domain.u05.Uriage;
import com.showka.entity.RUriagePK;

public interface UriageRirekiPersistence {

	/**
	 * 売上履歴保存.
	 * 
	 * @param domain
	 *            売上
	 */
	public void save(Uriage domain);

	/**
	 * 
	 * 売上履歴削除
	 * 
	 * @param pk
	 *            売上履歴テーブル主キー
	 */
	public void delete(RUriagePK pk);
}
