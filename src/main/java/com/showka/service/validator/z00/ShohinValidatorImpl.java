package com.showka.service.validator.z00;

import com.showka.service.crud.z00.i.ShohinCrud;
import com.showka.service.validator.z00.i.ShohinValidator;
import com.showka.system.exception.validate.NotExistException;

public class ShohinValidatorImpl implements ShohinValidator {

	/** 商品CRUD */
	private ShohinCrud shohinCrud;

	@Override
	public void validateExistance(String shohinCode) throws NotExistException {
		boolean exists = shohinCrud.exists(shohinCode);
		if (!exists) {
			throw new NotExistException("商品", shohinCode);
		}
	}
}
