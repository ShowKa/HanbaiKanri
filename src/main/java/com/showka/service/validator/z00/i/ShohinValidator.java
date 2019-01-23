package com.showka.service.validator.z00.i;

import com.showka.system.exception.validate.NotExistException;

public interface ShohinValidator {

	/**
	 * 存在検証.
	 * 
	 * <pre>
	 * 商品が存在しない場合、エラー
	 * </pre>
	 * 
	 * @param shohinCode
	 *            商品コード
	 * @throws NotExistException
	 *             商品コードは存在しません。
	 */
	void validateExistance(String shohinCode) throws NotExistException;

}
