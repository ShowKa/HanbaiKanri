package com.showka.service.validate.u17;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.entity.MBushoDate;
import com.showka.repository.i.MBushoDateRepository;
import com.showka.service.validate.u17.i.BushoDateValidateService;
import com.showka.system.exception.CanNotUpdateException;
import com.showka.value.EigyoDate;
import com.showka.value.TheDate;

@Service
public class BushoDateValidateServiceImpl implements BushoDateValidateService {

	@Autowired
	private MBushoDateRepository repo;

	@Override
	public void validateForClosing(String bushoCode, EigyoDate today) {
		MBushoDate dateEntity = repo.getOne(bushoCode);
		TheDate trueDate = new TheDate(dateEntity.getEigyoDate());
		if (!trueDate.isEqual(today)) {
			throw new CanNotUpdateException("すでに営業日が更新されている可能性があります（現在の営業日 : + " + trueDate + "）");
		}
	}
}
