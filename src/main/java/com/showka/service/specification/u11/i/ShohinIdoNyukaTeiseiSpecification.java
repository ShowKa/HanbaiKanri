package com.showka.service.specification.u11.i;

import java.util.List;

import com.showka.domain.u11.ShohinIdo;
import com.showka.service.validator.u11.i.ShohinZaikoValidator;
import com.showka.system.exception.specification.UnsatisfiedSpecificationException;

/**
 * 商品移動入荷訂正仕様.
 */
public interface ShohinIdoNyukaTeiseiSpecification extends ShohinIdoSpecification {

	/**
	 * 商品移動取得.
	 * 
	 * <pre>
	 * 下記条件を満たす入荷.入荷訂正商品移動を返却する。
	 * - 商品移動.日付 = 入荷.部署.営業日
	 * </pre>
	 * 
	 * @return 商品移動のリスト
	 */
	@Override
	public List<ShohinIdo> getShohinIdo();

	/**
	 * 削除対象商品移動取得.
	 * 
	 * <pre>
	 * size=0のリストを返却
	 * </pre>
	 * 
	 * @return 削除対象の商品移動のリスト
	 */
	@Override
	public List<ShohinIdo> getShohinIdoForDelete();

	/**
	 * 業務ルールに則っているか否かを検証する.
	 * 
	 * <pre>
	 * 商品マイナス在庫検証(商品移動)を実施
	 * 商品移動 = getShohinIdo()
	 * </pre>
	 * 
	 * @see ShohinZaikoValidator
	 * 
	 * @throws UnsatisfiedSpecificationException
	 */
	@Override
	public void ascertainSatisfaction() throws UnsatisfiedSpecificationException;

}
