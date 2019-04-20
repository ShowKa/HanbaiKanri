package com.showka.service.crud.u11.i;

import java.util.List;

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
	 * @param nyukaId
	 *            入荷ID
	 * @param teiseiShohinIdo
	 *            訂正商品移動
	 */
	public void save(String nyukaId, ShohinIdo teiseiShohinIdo);

	/**
	 * 削除
	 * 
	 * @param nyukaId
	 *            入荷ID
	 * @param teiseiShohinIdo
	 *            訂正商品移動
	 */
	public void delete(String nyukaId, ShohinIdo teiseiShohinIdo);

	/**
	 * 訂正商品移動取得.
	 * 
	 * @param nyukaId
	 *            入荷ID
	 * @return
	 */
	public List<ShohinIdo> getAll(String nyukaId);
}
