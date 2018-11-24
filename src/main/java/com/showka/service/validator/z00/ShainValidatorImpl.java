package com.showka.service.validator.z00;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.service.crud.z00.i.ShainCrud;
import com.showka.service.validator.z00.i.ShainValidator;
import com.showka.system.exception.NotExistException;

@Service
public class ShainValidatorImpl implements ShainValidator {

	@Autowired
	private ShainCrud shainPersistence;

	@Override
	public void validateExistance(String shainCode) throws NotExistException {
		boolean exists = shainPersistence.exists(shainCode);
		if (!exists) {
			throw new NotExistException("社員", shainCode);
		}
	}

}
