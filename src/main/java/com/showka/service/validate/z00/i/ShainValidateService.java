package com.showka.service.validate.z00.i;

import com.showka.system.exception.NotExistException;

public interface ShainValidateService {

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
