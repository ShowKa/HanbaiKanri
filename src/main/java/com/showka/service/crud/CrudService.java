package com.showka.service.crud;

import com.showka.domain.DomainBase;

public interface CrudService<T extends DomainBase, P> {

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
	boolean exsists(P pk);

	/**
	 * 存在する場合、削除する.
	 * 
	 * <pre>
	 * 排他制御対象外.
	 * </pre>
	 * 
	 * @param pk
	 *            主キー
	 */
	default void deleteIfExists(P pk) {
		if (exsists(pk)) {
			T domain = getDomain(pk);
			delete(domain);
		}
	}

	/**
	 * 存在する場合、削除する.
	 * 
	 * @param pk
	 *            主キー
	 */
	default void deleteIfExists(P pk, Integer version) {
		if (exsists(pk)) {
			T domain = getDomain(pk);
			domain.setVersion(version);
			delete(domain);
		}
	}
}
