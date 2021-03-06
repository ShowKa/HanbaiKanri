package com.showka.service.crud;

import com.showka.domain.DomainRoot;

public interface Crud<T extends DomainRoot, P> {

	/**
	 * 登録・更新.
	 *
	 * @param domain
	 *            ドメイン
	 */
	void save(T domain);

	/**
	 * 削除.
	 *
	 * @param pk
	 *            主キー
	 * @param version
	 *            バージョン
	 */
	void delete(T domain);

	/**
	 * ドメイン取得
	 *
	 * @param pk
	 *            主キー
	 * @return ドメイン
	 */
	T getDomain(P pk);

	/**
	 * 存在チェック.
	 *
	 * @param pk
	 * @return 主キーでテーブルを検索し、存在すればtrue
	 */
	boolean exists(P pk);

}
