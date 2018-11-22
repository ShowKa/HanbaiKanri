package com.showka.service.validator.z00;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.service.crud.z00.i.BushoCrud;
import com.showka.service.validator.z00.i.BushoValidator;
import com.showka.system.exception.NotExistException;

@Service
public class BushoValidatorImpl implements BushoValidator {

	@Autowired
	private BushoCrud bushoPersistence;

	@Override
	public void validateExistance(String bushoCode) throws NotExistException {
		boolean exists = bushoPersistence.exsists(bushoCode);
		if (!exists) {
			throw new NotExistException("部署", bushoCode);
		}
	}

}
