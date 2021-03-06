package com.showka.service.validator.u05.i;

import com.showka.domain.u05.UriageMeisai;
import com.showka.system.exception.validate.ValidateException;

public interface UriageMeisaiValidator {

	/**
	 * 整合性検証.
	 * 
	 * @param domain
	 *            売上明細ドメイン
	 * @throws ValidateException
	 *             整合性例外
	 */
	public void validate(UriageMeisai domain) throws ValidateException;
}