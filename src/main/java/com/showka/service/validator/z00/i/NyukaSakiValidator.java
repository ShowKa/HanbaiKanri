package com.showka.service.validator.z00.i;

import com.showka.system.exception.validate.NotExistException;

public interface NyukaSakiValidator {
	/**
	 * 存在検証.
	 * 
	 * <pre>
	 * 入荷先が存在しない場合、エラー
	 * </pre>
	 * 
	 * @param nyukaSakiCode
	 *            入荷先コード
	 * @throws NotExistException
	 *             入荷先コードは存在しません。
	 */
	void validateExistance(String nyukaSakiCode) throws NotExistException;
}
