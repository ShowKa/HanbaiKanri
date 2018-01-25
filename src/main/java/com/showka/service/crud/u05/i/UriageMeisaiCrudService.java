package com.showka.service.crud.u05.i;

import java.util.List;

import com.showka.domain.UriageMeisaiDomain;
import com.showka.entity.TUriageMeisaiPK;
import com.showka.service.crud.CrudService;

public interface UriageMeisaiCrudService extends CrudService<UriageMeisaiDomain, TUriageMeisaiPK> {

	/**
	 * ドメインをリストで取得.
	 * 
	 * @param uriageId
	 *            売上ID
	 * @return
	 */
	public List<UriageMeisaiDomain> getDomainList(String uriageId);

	/**
	 * 明細番号の最大値を取得する。
	 * 
	 * @param uriageId
	 *            売上ID
	 * @return 明細番号の最大値
	 */
	public Integer getMaxMeisaiNumber(String uriageId);

	/**
	 * 全明細削除.
	 * 
	 * <pre>
	 * 排他制御対象外
	 * </pre>
	 * 
	 * @param uriageId
	 *            売上ID
	 */
	public void deleteAll(String uriageId);

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
	public void overrideList(List<UriageMeisaiDomain> meisaiList);

}
