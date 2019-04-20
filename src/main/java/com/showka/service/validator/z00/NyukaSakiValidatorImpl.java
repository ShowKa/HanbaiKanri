package com.showka.service.validator.z00;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.service.crud.u11.i.NyukaSakiCrud;
import com.showka.service.validator.z00.i.NyukaSakiValidator;
import com.showka.system.exception.validate.NotExistException;

@Service
public class NyukaSakiValidatorImpl implements NyukaSakiValidator {

	@Autowired
	private NyukaSakiCrud nyukaSakiCrud;

	@Override
	public void validateExistance(String nyukaSakiCode) throws NotExistException {
		boolean exists = nyukaSakiCrud.exists(nyukaSakiCode);
		if (!exists) {
			throw new NotExistException("入荷先コード", nyukaSakiCode);
		}
	}
}
