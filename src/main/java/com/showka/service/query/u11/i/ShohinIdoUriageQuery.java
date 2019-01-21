package com.showka.service.query.u11.i;

import java.util.List;
import java.util.Optional;

import com.showka.domain.u11.ShohinIdo;
import com.showka.value.EigyoDate;

public interface ShohinIdoUriageQuery {

	/**
	 * 売上の最新の商品移動取得.
	 * 
	 * @param uriageId
	 *            売上ID
	 * @return 商品移動
	 */
	public Optional<ShohinIdo> getNewest(String uriageId);

	/**
	 * 引数.日付の売上の商品移動取得.
	 * 
	 * <pre>
	 * 売上訂正の商品移動も取得する（あれば）。
	 * </pre>
	 * 
	 * @param uriageId
	 *            売上ID
	 * @param date
	 *            日付
	 * @return 商品移動
	 */
	public List<ShohinIdo> get(String uriageId, EigyoDate date);
}
