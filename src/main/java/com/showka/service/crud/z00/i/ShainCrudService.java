package com.showka.service.crud.z00.i;

import com.showka.domain.z00.Shain;

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
	Shain getDomain(String code);

}
