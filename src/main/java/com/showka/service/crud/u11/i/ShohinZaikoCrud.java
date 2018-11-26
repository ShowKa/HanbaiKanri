package com.showka.service.crud.u11.i;

import com.showka.domain.u11.ShohinZaiko;
import com.showka.entity.TShohinZaikoPK;
import com.showka.service.crud.Crud;

public interface ShohinZaikoCrud extends Crud<ShohinZaiko, TShohinZaikoPK> {
	/**
	 * 商品在庫登録.
	 * 
	 * <pre>
	 * 現時点の在庫数でレコードの在庫数の値を登録します。
	 * なおinsertのみを想定しています。
	 * 排他制御対象外です。
	 * </pre>
	 * 
	 * @param zaiko
	 *            商品在庫
	 */
	@Override
	public void save(ShohinZaiko zaiko);
}
