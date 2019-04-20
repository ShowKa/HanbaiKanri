package com.showka.service.persistence.u11.i;

import com.showka.domain.u11.Nyuka;
import com.showka.service.crud.u11.i.ShohinIdoNyukaCrud;
import com.showka.service.crud.u11.i.NyukaTeiseiCrud;
import com.showka.service.crud.u11.i.ShohinIdoCrud;

/**
 * 商品移動入荷訂正の永続化Service
 */
public interface ShohinIdoNyukaTeiseiPersistence {

	/**
	 * 入荷訂正の保存.
	 * 
	 * <pre>
	 * 対象テーブル
	 * - 商品移動 & 商品移動明細
	 * - 商品移動入荷
	 * - 商品移動入荷訂正
	 * 
	 * 注意点：
	 * 商品移動入荷テーブルは楽観排他制御のため更新を行う。
	 * </pre>
	 * 
	 * @see ShohinIdoCrud
	 * @see ShohinIdoNyukaCrud
	 * @see NyukaTeiseiCrud
	 * 
	 * @param nyuka
	 *            入荷
	 */
	public void save(Nyuka nyuka);

	/**
	 * 入荷訂正の削除.
	 * 
	 * <pre>
	 * 削除対象テーブル
	 * - 商品移動 & 商品移動明細
	 * - 商品移動入荷訂正
	 * 
	 * 更新対象テーブル
	 * - 商品移動入荷
	 * 
	 * 注意点：
	 * 商品移動入荷テーブルは楽観排他制御のため更新を行う。
	 * </pre>
	 * 
	 * @see ShohinIdoCrud
	 * @see ShohinIdoNyukaCrud
	 * @see NyukaTeiseiCrud
	 * 
	 * @param nyuka
	 *            入荷
	 */
	public void delete(Nyuka nyuka);
}
