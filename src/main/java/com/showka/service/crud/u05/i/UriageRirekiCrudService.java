package com.showka.service.crud.u05.i;

import com.showka.domain.UriageRirekiDomain;
import com.showka.domain.UriageRirekiListDomain;

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
	public void save(UriageRirekiDomain domain);

}