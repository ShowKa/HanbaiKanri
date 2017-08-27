package com.showka.service.crud.u01.i;

import com.showka.domain.ShohinDomain;

public interface MShohinCrudService {

	/**
	 * 商品ドメイン取得.
	 * 
	 * @param pk
	 *            商品コード
	 * @return 商品ドメイン
	 */
	ShohinDomain getDomain(String code);

}
