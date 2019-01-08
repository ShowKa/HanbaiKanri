package com.showka.service.persistence.u11.i;

import com.showka.domain.u11.Nyuka;
import com.showka.service.crud.u11.i.NyukaCrud;
import com.showka.service.crud.u11.i.ShohinIdoCrud;
import com.showka.service.specification.u11.i.ShohinIdoNyukaSpecification;

public interface ShohinIdoNyukaPersistence {

	/**
	 * 保存.
	 * 
	 * <pre>
	 * 商品移動入荷仕様構築
	 * 商品移動
	 * 入荷登録（排他制御実施）
	 * </pre>
	 * 
	 * @see ShohinIdoNyukaSpecification
	 * @see ShohinIdoPersistence
	 * @see NyukaCrud
	 * @param nyuka
	 *            入荷
	 */
	public void save(Nyuka nyuka);

	/**
	 * 商品移動削除.
	 * 
	 * <pre>
	 * 商品移動削除
	 * 入荷削除
	 * </pre>
	 * 
	 * @see ShohinIdoCrud
	 * @see NyukaCrud
	 * 
	 * @param nyuka
	 *            入荷
	 */
	public void delete(Nyuka nyuka);

	/**
	 * 訂正.
	 * 
	 * @param nyuka
	 *            入荷
	 */
	public void teisei(Nyuka nyuka);

	/**
	 * 訂正削除.
	 * 
	 * @param nyuka
	 *            入荷
	 */
	public void deleteTeisei(Nyuka nyuka);

}
