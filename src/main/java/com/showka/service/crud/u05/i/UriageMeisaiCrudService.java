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

}
