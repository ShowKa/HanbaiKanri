package com.showka.service.validator.u11.i;

import com.showka.domain.u11.Nyuka;
import com.showka.system.exception.validate.MeisaiUniqueConstraintException;
import com.showka.system.exception.validate.NotAllowedNumberException;
import com.showka.system.exception.validate.ValidateException;

public interface NyukaValidator {

	/**
	 * $商品入荷の業務整合性検証.
	 * 
	 * <pre>
	 * </pre>
	 * 
	 * @param nyuka
	 *            入荷
	 * @throws NotAllowedNumberException
	 *             入荷数は1以上を設定する必要があります。
	 * @throws MeisaiUniqueConstraintException
	 *             重複する明細が存在します。重複条件: 商品
	 */
	public void validate(Nyuka nyuka) throws NotAllowedNumberException, MeisaiUniqueConstraintException;

	/**
	 * $商品入荷の更新時整合性検証.
	 * 
	 * <pre>
	 * 1. 営業日が変わり、入荷日を過ぎた場合エラー
	 *     $商品入荷.入荷日 != $商品入荷.部署.営業日
	 * 2. 商品移動の更新の結果、商品の在庫数がマイナスになる場合エラー
	 * </pre>
	 * 
	 * @see ShohinZaikoValidator
	 * 
	 * @param nyuka
	 *            入荷
	 * @throws ValidateException
	 */
	public void validateForUpdate(Nyuka nyuka) throws ValidateException;

	public void validateForDelete(Nyuka nyuka);

}
