package com.showka.service.validator.z00.i;

import com.showka.system.exception.validate.NotExistException;

public interface ShainValidator {

	/**
	 * 存在検証.
	 * 
	 * <pre>
	 * 社員が存在しない場合、エラー
	 * </pre>
	 * 
	 * @param shainCode
	 *            社員コード
	 * @throws NotExistException
	 *             社員は存在しません。
	 */
	public void validateExistance(String shainCode) throws NotExistException;
}
