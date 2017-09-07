package com.showka.service.validate.u01;

import org.springframework.stereotype.Service;

import com.showka.domain.NyukinKakeInfoDomain;
import com.showka.service.validate.u01.i.NyukinKakeInfoValidateService;
import com.showka.system.exception.ValidateException;

/**
 * 入金掛情報 Validate Service
 *
 * @author 25767
 *
 */
@Service
public class NyukinKakeInfoValidateServiceImpl implements NyukinKakeInfoValidateService {

	@Override
	public void validate(NyukinKakeInfoDomain domain) throws ValidateException {
		if (!domain.shimeDateBeforeNyukinDate()) {
			throw new ValidateException("入金日が締日より早く設定されています");
		}
	}

}
