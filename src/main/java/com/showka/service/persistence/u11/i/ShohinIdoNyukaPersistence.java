package com.showka.service.persistence.u11.i;

import com.showka.domain.u11.Nyuka;

public interface ShohinIdoNyukaPersistence {

	/**
	 * 保存.
	 * 
	 * @param nyuka
	 *            入荷
	 */
	public void save(Nyuka nyuka);

	/**
	 * 商品移動削除.
	 * 
	 * @param nyuka
	 *            入荷
	 */
	public void delete(Nyuka nyuka);

	/**
	 * 訂正
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
