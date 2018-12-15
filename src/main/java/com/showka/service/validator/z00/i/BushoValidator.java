package com.showka.service.validator.z00.i;

import com.showka.system.exception.validate.NotExistException;

public interface BushoValidator {

	/**
	 * 存在検証.
	 * 
	 * <pre>
	 * 部署が存在しない場合、エラー
	 * </pre>
	 * 
	 * @param bushoCode
	 *            部署コード
	 * @throws NotExistException
	 *             部署は存在しません。
	 */
	public void validateExistance(String bushoCode) throws NotExistException;
}
