package com.showka.service.crud.u05.i;

import java.util.List;

import com.showka.domain.UriageMeisaiDomain;
import com.showka.entity.RUriageMeisaiPK;
import com.showka.service.crud.CrudService;

public interface UriageRirekiMeisaiCrudService extends CrudService<UriageMeisaiDomain, RUriageMeisaiPK> {

	/**
	 * ドメインをリストで取得.
	 * 
	 * @param uriageId
	 *            売上ID
	 * @return
	 */
	public List<UriageMeisaiDomain> getDomainList(String uriageId);

	/**
	 * リスト保存.
	 * 
	 * <pre>
	 * 注意：引数のリストに含まれない古いレコードは削除されます。
	 * </pre>
	 * 
	 * @param uriageMeisai
	 *            売上明細
	 */
	public void overrideList(List<UriageMeisaiDomain> uriageMeisaiList);

	/**
	 * 明細全削除.
	 * 
	 * <pre>
	 * 排他制御対象外。
	 * </pre>
	 * 
	 * @param uriageId
	 *            売上ID
	 */
	public void deleteAll(String uriageId);
}
