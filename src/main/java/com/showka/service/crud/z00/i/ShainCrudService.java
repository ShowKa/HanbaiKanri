package com.showka.service.crud.z00.i;

import com.showka.domain.ShainDomain;

/**
 * 社員CrudService
 * 
 * @author ShowKa
 *
 */
public interface ShainCrudService {

	/**
	 * 社員ドメイン取得.
	 * 
	 * @param code
	 *            社員コード
	 * @return 社員ドメイン
	 */
	ShainDomain getDomain(String code);

}
