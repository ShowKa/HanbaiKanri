package com.showka.service.validate.u05.i;

import com.showka.domain.UriageDomain;
import com.showka.system.exception.ValidateException;

public interface UriageValidateService {

	/**
	 * 整合性検証.
	 * 
	 * @param domain
	 *            売上ドメイン
	 * @throws ValidateException
	 *             整合性例外
	 */
	public void validate(UriageDomain domain) throws ValidateException;

	/**
	 * 初回登録時整合性検証.
	 * 
	 * @param domain
	 *            売上ドメイン
	 * @throws ValidateException
	 *             整合性例外
	 */
	public void validateForRegister(UriageDomain domain) throws ValidateException;
}
