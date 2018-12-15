package com.showka.service.validator.z00;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.service.crud.z00.i.BushoCrud;
import com.showka.service.validator.z00.i.BushoValidator;
import com.showka.system.exception.validate.NotExistException;

@Service
public class BushoValidatorImpl implements BushoValidator {

	@Autowired
	private BushoCrud bushoPersistence;

	@Override
	public void validateExistance(String bushoCode) throws NotExistException {
		boolean exists = bushoPersistence.exists(bushoCode);
		if (!exists) {
			throw new NotExistException("部署", bushoCode);
		}
	}

}
