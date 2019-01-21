package com.showka.service.persistence.u11.i;

import com.showka.domain.u05.Uriage;
import com.showka.entity.TUriagePK;

public interface ShohinIdoUriagePersistence {

	/**
	 * 商品移動売上の保存.
	 * 
	 * @param uriage
	 *            売上
	 */
	public void save(Uriage uriage);

	/**
	 * 商品移動売上削除.
	 * 
	 * @param pk
	 *            売上の主キー
	 */
	public void delete(TUriagePK pk);

	/**
	 * 
	 * 商品移動売上（キャンセル時）の保存.
	 * 
	 * @param pk
	 *            売上の主キー
	 */
	public void cancel(TUriagePK pk);
}
