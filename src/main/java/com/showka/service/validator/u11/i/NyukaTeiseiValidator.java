package com.showka.service.validator.u11.i;

import com.showka.domain.u11.Nyuka;
import com.showka.system.exception.validate.CanNotUpdateOrDeleteException;

public interface NyukaTeiseiValidator {

	/**
	 * 商品入荷の訂正登録時整合性検証.
	 * 
	 * <pre>
	 * エラー条件
	 * - 入荷登録してからまだ営業日が更新されていない（締前）
	 * - 入荷日以降に棚卸を実施済
	 * - 商品移動の更新の結果、商品の在庫数がマイナスになる
	 * </pre>
	 * 
	 * @param nyuka
	 *            入荷
	 * @param teiseiShohinIdoId
	 *            訂正商品移動ID
	 * @see ShohinZaikoValidator 商品在庫検証
	 * @throws CanNotUpdateOrDeleteException
	 *             更新・削除できません。（理由：締処理が未実施のため本伝票は訂正処理の対象外です）
	 *             更新・削除できません。（理由：入荷日以降に棚卸が実施済）
	 */
	public void validateForTeisei(Nyuka nyuka, String teiseiShohinIdoId) throws CanNotUpdateOrDeleteException;

	/**
	 * 商品入荷の訂正更新時整合性検証.
	 * 
	 * <pre>
	 * エラー条件
	 * - 当日営業日に入荷訂正を行っていない。
	 * - 商品移動の更新の結果、商品の在庫数がマイナスになる
	 * </pre>
	 * 
	 * @param nyuka
	 *            入荷
	 * @param teiseiShohinIdoId
	 *            訂正商品移動ID
	 * @see ShohinZaikoValidator 商品在庫検証
	 * @throws CanNotUpdateOrDeleteException
	 *             更新・削除できません。（理由：当日に入荷訂正を行っていないため本伝票は入荷訂正の更新処理の対象外です）
	 */
	public void validateForTeiseiUpdate(Nyuka nyuka, String teiseiShohinIdoId) throws CanNotUpdateOrDeleteException;

	/**
	 * 商品入荷の訂正削除時整合性検証.
	 * 
	 * <pre>
	 * エラー条件
	 * - 当日営業日に入荷訂正を行っていない。
	 * </pre>
	 * 
	 * @param nyuka
	 *            入荷
	 * @param teiseiShohinIdoId
	 *            訂正商品移動ID
	 * @throws CanNotUpdateOrDeleteException
	 *             更新・削除できません。（理由：当日に入荷訂正を行っていないため本伝票は入荷訂正削除処理の対象外です）
	 */
	public void validateForTeiseiDelete(Nyuka nyuka, String teiseiShohinIdoId) throws CanNotUpdateOrDeleteException;
}
