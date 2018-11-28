package com.showka.service.query.u11.i;

import java.util.Optional;

import com.showka.domain.u11.ShohinIdo;

public interface ShohinIdoUriageQuery {

	/**
	 * 商品移動取得.
	 * 
	 * @param uriageId
	 *            売上ID
	 * @return 商品移動
	 */
	public Optional<ShohinIdo> getNewest(String uriageId);
}
