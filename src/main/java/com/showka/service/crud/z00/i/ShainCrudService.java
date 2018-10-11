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

	/**
	 * 社員存在チェック.
	 * 
	 * @param shainCode
	 *            社員コード
	 * @return 存在する場合true
	 */
	boolean exists(String shainCode);

}
