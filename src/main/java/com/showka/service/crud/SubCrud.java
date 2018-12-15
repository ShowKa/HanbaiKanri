package com.showka.service.crud;

import com.showka.domain.DomainRoot;

public interface SubCrud<T extends DomainRoot> {

	/**
	 * 登録・更新.
	 *
	 * @param id
	 *            ID
	 * @param domain
	 *            ドメイン
	 */
	void save(String id, T domain);

	/**
	 * 削除.
	 *
	 * @param id
	 *            ID
	 */
	void delete(String id);

	/**
	 * ドメイン取得
	 *
	 * @param id
	 *            ID
	 * @return ドメイン
	 */
	T getDomain(String id);

	/**
	 * 存在チェック.
	 *
	 * @param id
	 *            ID
	 * @return 存在すればtrue
	 */
	boolean exists(String id);

}
