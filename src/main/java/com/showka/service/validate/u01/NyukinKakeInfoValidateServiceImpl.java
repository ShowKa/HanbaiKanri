package com.showka.service.validate.u01;

import org.springframework.stereotype.Service;

import com.showka.domain.NyukinKakeInfoDomain;
import com.showka.service.validate.u01.i.NyukinKakeInfoValidateService;
import com.showka.system.exception.ValidateException;

@Service
public class NyukinKakeInfoValidateServiceImpl implements NyukinKakeInfoValidateService {

	@Override
	public void validate(NyukinKakeInfoDomain domain) throws ValidateException {
		if (domain.shimeDateBeforeNyukinDate()) {
			return;
		} else {
			throw new ValidateException("入金日が締日より早く設定されています");
		}
	}

}
