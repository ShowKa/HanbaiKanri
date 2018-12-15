package com.showka.service.validator.u01;

import org.springframework.stereotype.Service;

import com.showka.domain.u01.NyukinKakeInfo;
import com.showka.service.validator.u01.i.NyukinKakeInfoValidator;
import com.showka.system.exception.validate.ValidateException;
import com.showka.system.exception.validate.WrongDateOrderException;

/**
 * 入金掛情報 Validate Service
 *
 * @author 25767
 *
 */
@Service
public class NyukinKakeInfoValidatorImpl implements NyukinKakeInfoValidator {

	@Override
	public void validate(NyukinKakeInfo domain) throws ValidateException {
		if (!domain.shimeDateBeforeNyukinDate()) {
			throw new WrongDateOrderException("締日", "入金日");
		}
	}

}
