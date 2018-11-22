package com.showka.service.persistence.z00.i;

import com.showka.domain.z00.Shohin;

public interface ShohinPersistence {

	/**
	 * 商品ドメイン取得.
	 * 
	 * @param pk
	 *            商品コード
	 * @return 商品ドメイン
	 */
	Shohin getDomain(String code);

}