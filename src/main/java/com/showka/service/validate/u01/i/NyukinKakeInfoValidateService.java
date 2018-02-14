package com.showka.service.validate.u01.i;

import com.showka.domain.NyukinKakeInfo;
import com.showka.system.exception.ValidateException;

public interface NyukinKakeInfoValidateService {
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
