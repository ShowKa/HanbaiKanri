package com.showka.service.crud.u05.i;

import java.util.List;

import com.showka.domain.UriageMeisaiDomain;

public interface UriageRirekiMeisaiCrudService {

	/**
	 * ドメインをリストで取得.
	 * 
	 * @param uriageId
	 *            売上ID
	 * @return
	 */
	public List<UriageMeisaiDomain> getDomainList(String uriageId);

	/**
	 * 保存.
	 * 
	 * <pre>
	 * 排他制御対象外
	 * </pre>
	 * 
	 * @param uriageMeisai
	 *            売上明細
	 */
	public void save(UriageMeisaiDomain uriageMeisai);

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
	 * 削除
	 * 
	 * @param old
	 */
	public void delete(UriageMeisaiDomain old);

}
