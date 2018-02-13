package com.showka.service.crud.z00.i;

import com.showka.domain.Shohin;

public interface ShohinCrudService {

	/**
	 * 商品ドメイン取得.
	 * 
	 * @param pk
	 *            商品コード
	 * @return 商品ドメイン
	 */
	Shohin getDomain(String code);

}
