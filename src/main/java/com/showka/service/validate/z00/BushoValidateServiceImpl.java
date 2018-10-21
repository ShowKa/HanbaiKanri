package com.showka.service.validate.z00;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.service.crud.z00.i.BushoCrudService;
import com.showka.service.validate.z00.i.BushoValidateService;
import com.showka.system.exception.NotExistException;

@Service
public class BushoValidateServiceImpl implements BushoValidateService {

	@Autowired
	private BushoCrudService bushoCrudService;

	@Override
	public void validateExistance(String bushoCode) throws NotExistException {
		boolean exists = bushoCrudService.exists(bushoCode);
		if (!exists) {
			throw new NotExistException("部署", bushoCode);
		}
	}

}
