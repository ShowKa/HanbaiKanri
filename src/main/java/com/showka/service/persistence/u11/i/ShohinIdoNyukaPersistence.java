package com.showka.service.persistence.u11.i;

import com.showka.domain.u11.Nyuka;
import com.showka.service.crud.u11.i.NyukaCrud;
import com.showka.service.crud.u11.i.ShohinIdoCrud;

/**
 * 商品移動入荷の永続化Service
 */
public interface ShohinIdoNyukaPersistence {

	/**
	 * 商品入荷の保存.
	 * 
	 * <pre>
	 * 対象テーブル
	 * - 商品移動 & 商品移動明細
	 * - 商品移動入荷
	 * 
	 * </pre>
	 * 
	 * @see ShohinIdoCrud
	 * @see NyukaCrud
	 * 
	 * @param nyuka
	 *            入荷
	 */
	public void save(Nyuka nyuka);

	/**
	 * 商品入荷の削除.
	 * 
	 * <pre>
	 * 対象テーブル
	 * - 商品移動 & 商品移動明細
	 * - 商品移動入荷
	 * 
	 * </pre>
	 * 
	 * @see ShohinIdoCrud
	 * @see NyukaCrud
	 * 
	 * @param nyuka
	 *            入荷
	 */
	public void delete(Nyuka nyuka);
}
