package com.showka.service.crud.u05.i;

import com.showka.domain.UriageDomain;
import com.showka.domain.UriageRirekiListDomain;
import com.showka.entity.RUriagePK;

public interface UriageRirekiCrudService {

	/**
	 * 売上履歴取得.
	 * 
	 * @param uriageId
	 *            売上ID
	 * @return 売上履歴
	 */
	public UriageRirekiListDomain getUriageRirekiList(String uriageId);

	/**
	 * 売上履歴保存.
	 * 
	 * @param domain
	 *            売上
	 */
	public void save(UriageDomain domain);

	/**
	 * 売上履歴削除.
	 * 
	 * <pre>
	 * 排他制御対象外
	 * </pre>
	 * 
	 * @param domain
	 *            売上
	 */
	public void delete(UriageDomain domain);

	/**
	 * 
	 * 売上履歴削除
	 * 
	 * @param pk
	 *            売上履歴テーブル主キー
	 */
	public void delete(RUriagePK pk, Integer version);

	/**
	 * 売上キャンセルを履歴保存.
	 * 
	 * <pre>
	 * 排他制御対象外
	 * </pre>
	 * 
	 * @param domain
	 *            売上ドメイン
	 */
	public void cancel(UriageDomain domain);

}
