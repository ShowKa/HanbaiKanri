package com.showka.service.specification.u11.i;

import java.util.List;

import com.showka.domain.u11.ShohinIdo;
import com.showka.service.validator.u11.i.ShohinZaikoValidator;
import com.showka.system.exception.specification.UnsatisfiedSpecificationException;

public interface ShohinIdoNyukaSpecification extends ShohinIdoSpecification {

	/**
	 * 商品移動取得.
	 * 
	 * <pre>
	 * 
	 * 下記の通り、入荷から商品移動を構築&取得
	 * 
	 * 部署 = 入荷.部署
	 * 日付 = 入荷.日付
	 * 移動区分 = 商品入荷
	 * タイムスタンプ = 現在時刻
	 * 商品移動明細
	 *   - 商品 = 入荷.明細.商品
	 *   - 数量 = 入荷.明細.数量
	 * 
	 * レコードID
	 * - 商品移動未登録の場合、設定不要（自動採番）
	 * - 商品移動登録済の場合、既存レコードから取得
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
	 * なし
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
	 * 商品マイナス在庫検証 を実施する。
	 * </pre>
	 * 
	 * @see ShohinZaikoValidator
	 * 
	 * @throws UnsatisfiedSpecificationException
	 */
	@Override
	public void ascertainSatisfaction() throws UnsatisfiedSpecificationException;

}
