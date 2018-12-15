package com.showka.service.validator.u01.i;

import com.showka.domain.u01.NyukinKakeInfo;
import com.showka.system.exception.validate.ValidateException;

public interface NyukinKakeInfoValidator {
	/**
	 *
	 * 整合性検証.
	 *
	 * <pre>
	 * 入金・請求締日の整合性検証
	 * </pre>
	 *
	 * @param domain
	 * @throws ValidateException
	 */
	public void validate(NyukinKakeInfo domain) throws ValidateException;
}
