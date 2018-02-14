package com.showka.service.validate.u01;

import org.springframework.stereotype.Service;

import com.showka.domain.NyukinKakeInfo;
import com.showka.service.validate.u01.i.NyukinKakeInfoValidateService;
import com.showka.system.exception.ValidateException;
import com.showka.system.exception.WrongDateOrderException;

/**
 * 入金掛情報 Validate Service
 *
 * @author 25767
 *
 */
@Service
public class NyukinKakeInfoValidateServiceImpl implements NyukinKakeInfoValidateService {

	@Override
	public void validate(NyukinKakeInfo domain) throws ValidateException {
		if (!domain.shimeDateBeforeNyukinDate()) {
			throw new WrongDateOrderException("締日", "入金日");
		}
	}

}
