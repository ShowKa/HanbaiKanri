package com.showka.service.crud;

import java.util.List;

import com.showka.domain.DomainBase;

public interface MeisaiCrudService<T extends DomainBase, P> {

	/**
	 * 保存.
	 * 
	 * @param id
	 *            ID
	 * @param domain
	 *            ドメイン
	 */
	public void save(String id, T domain);

	/**
	 * 削除.
	 * 
	 * @param pk
	 *            primary key
	 * @param domain
	 *            ドメイン
	 */
	public void delete(P pk, int version);

	/**
	 * ドメインを取得.
	 * 
	 * @param pk
	 *            primary key
	 * @return
	 */
	public T getDomain(P pk);

	/**
	 * ドメインをリストで取得.
	 * 
	 * @param id
	 *            ID
	 * @return
	 */
	public List<T> getDomainList(String id);

	/**
	 * 全明細削除.
	 * 
	 * <pre>
	 * 排他制御対象外
	 * </pre>
	 * 
	 * @param id
	 *            ID
	 */
	public void deleteAll(String id);

	/**
	 * リスト保存.
	 * 
	 * <pre>
	 * 注意：引数のリストに含まれない古いレコードは削除されます。
	 * </pre>
	 * 
	 * @param meisai
	 *            明細のリスト
	 * 
	 */
	public void overrideList(String id, List<T> meisaiList);

}
