package com.showka.service.crud.u05.i;

import com.showka.domain.UriageDomain;
import com.showka.entity.TUriagePK;
import com.showka.service.crud.CrudService;

public interface UriageCrudService extends CrudService<UriageDomain, TUriagePK> {

	/**
	 * 売上キャンセル.
	 * 
	 * @param domain
	 *            売上ドメイン
	 */
	public void cancel(UriageDomain domain);

}
