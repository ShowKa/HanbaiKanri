package com.showka.service.crud.u11.i;

import java.util.List;

import com.showka.domain.u11.Nyuka;
import com.showka.domain.u11.ShohinIdo;

/**
 * 入荷訂正CRUD
 * 
 * <pre>
 * 商品移動入荷訂正テーブルのCRUD
 * </pre>
 */
public interface NyukaTeiseiCrud {

	/**
	 * 保存.
	 * 
	 * @param teiseiShohinIdo
	 *            訂正商品移動
	 * @param nyuka
	 *            入荷
	 */
	public void save(ShohinIdo teiseiShohinIdo, Nyuka nyuka);

	/**
	 * 削除
	 * 
	 * @param teiseiShohinIdo
	 *            訂正商品移動
	 * @param nyuka
	 *            入荷
	 */
	public void delete(ShohinIdo teiseiShohinIdo, Nyuka nyuka);

	/**
	 * 訂正商品移動取得.
	 * 
	 * @param nyukaId
	 *            入荷ID
	 * @return
	 */
	public List<ShohinIdo> getAll(String nyukaId);
}
