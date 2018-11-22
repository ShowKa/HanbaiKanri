package com.showka.service.persistence.u11.i;

import java.util.Optional;

import com.showka.domain.u05.Uriage;
import com.showka.domain.u11.ShohinIdo;
import com.showka.entity.TUriagePK;

public interface ShohinIdoUriagePersistence {

	/**
	 * 保存.
	 * 
	 * @param uriage
	 *            売上
	 */
	public void save(Uriage uriage);

	/**
	 * 商品移動取得.
	 * 
	 * @param uriageId
	 *            売上ID
	 * @return 商品移動
	 */
	public Optional<ShohinIdo> getNewestShohinIdo(String uriageId);

	/**
	 * 商品移動削除.
	 * 
	 * <pre>
	 * 移動日=営業日の商品移動のレコードのみ削除し、前日移動分はそのまま残す。
	 * </pre>
	 * 
	 * @param pk
	 *            売上の主キー
	 */
	public void delete(TUriagePK pk);
}