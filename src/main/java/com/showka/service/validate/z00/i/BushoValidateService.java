package com.showka.service.validate.z00.i;

import com.showka.system.exception.NotExistException;

public interface BushoValidateService {

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
