package com.showka.service.persistence.u11.i;

import com.showka.domain.u11.Nyuka;
import com.showka.service.crud.u11.i.NyukaTeiseiCrud;
import com.showka.service.crud.u11.i.ShohinIdoCrud;

public interface ShohinIdoNyukaTeiseiPersistence {
	/**
	 * 訂正.
	 * 
	 * <pre>
	 * 商品移動
	 * 入荷訂正登録
	 * </pre>
	 * 
	 * @see ShohinIdoNyukaTeiseiSpecification
	 * @see ShohinIdoPersistence
	 * @see NyukaTeiseiCrud
	 * 
	 * @param nyuka
	 *            入荷
	 */
	public void save(Nyuka nyuka);

	/**
	 * 訂正削除.
	 * 
	 * <pre>
	 * 商品移動削除
	 * 入荷訂正削除
	 * </pre>
	 * 
	 * @see ShohinIdoCrud
	 * @see NyukaTeiseiCrud
	 * 
	 * @param nyuka
	 *            入荷
	 */
	public void delete(Nyuka nyuka);

}
