package com.showka.service.validate.z00;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.service.crud.z00.i.ShainCrudService;
import com.showka.service.validate.z00.i.ShainValidateService;
import com.showka.system.exception.NotExistException;

@Service
public class ShainValidateServiceImpl implements ShainValidateService {

	@Autowired
	private ShainCrudService shainCrudService;

	@Override
	public void validateExistance(String shainCode) throws NotExistException {
		boolean exists = shainCrudService.exists(shainCode);
		if (!exists) {
			throw new NotExistException("社員", shainCode);
		}
	}

}
