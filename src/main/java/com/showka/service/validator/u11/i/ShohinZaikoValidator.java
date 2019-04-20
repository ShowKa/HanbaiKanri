package com.showka.service.validator.u11.i;

import com.showka.domain.u11.ShohinIdo;
import com.showka.domain.u11.ShohinZaiko;
import com.showka.service.query.u11.i.ShohinZaikoQuery;
import com.showka.system.exception.specification.MinusZaikoException;

/**
 * 商品在庫整合性検証.
 */
public interface ShohinZaikoValidator {

	/**
	 * 商品移動登録・更新時の商品マイナス在庫検証.
	 * 
	 * <pre>
	 * 引数.商品移動:-商品移動明細.商品毎に下記の処理を実施する。
	 * - 商品在庫を取得する
	 * - 商品在庫に引数.商品移動をマージ
	 * - 商品在庫.商品在庫数<0の場合エラー
	 * </pre>
	 * 
	 * @see ShohinZaikoQuery
	 * @see ShohinZaiko
	 * 
	 * @param shohinIDo
	 *            商品移動
	 * @throws MinusZaikoException
	 *             マイナス在庫例外.
	 */
	public void validateMinusZaiko(ShohinIdo shohinIDo) throws MinusZaikoException;

	/**
	 * 商品移動削除時の商品マイナス在庫検証.
	 * 
	 * <pre>
	 * 引数.商品移動:-商品移動明細.商品毎に下記の処理を実施する。
	 * - 商品在庫を取得する
	 * - 商品在庫から引数.商品移動を除去
	 * - 商品在庫.商品在庫数<0の場合エラー
	 * </pre>
	 * 
	 * @see ShohinZaikoQuery
	 * @see ShohinZaiko
	 * 
	 * @param shohinIDo
	 *            商品移動
	 * @throws MinusZaikoException
	 *             マイナス在庫例外.
	 */
	public void validateMinusZaikoForDelete(ShohinIdo shohinIDo) throws MinusZaikoException;
}
