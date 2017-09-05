package com.showka.service.validate.u05.i;

import com.showka.domain.UriageMeisaiDomain;
import com.showka.system.exception.ValidateException;

public interface UriageMeisaiValidateService {

	/**
	 * 整合性検証.
	 * 
	 * @param domain
	 *            売上明細ドメイン
	 * @throws ValidateException
	 *             整合性例外
	 */
	public void validate(UriageMeisaiDomain domain) throws ValidateException;
}