package com.showka.service.validator.z00;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.service.persistence.z00.i.BushoPersistence;
import com.showka.service.validator.z00.i.BushoValidator;
import com.showka.system.exception.NotExistException;

@Service
public class BushoValidatorImpl implements BushoValidator {

	@Autowired
	private BushoPersistence bushoPersistence;

	@Override
	public void validateExistance(String bushoCode) throws NotExistException {
		boolean exists = bushoPersistence.exists(bushoCode);
		if (!exists) {
			throw new NotExistException("部署", bushoCode);
		}
	}

}
