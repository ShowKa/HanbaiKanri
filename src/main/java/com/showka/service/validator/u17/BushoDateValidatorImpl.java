package com.showka.service.validator.u17;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.z00.Busho;
import com.showka.entity.MBushoDate;
import com.showka.repository.i.MBushoDateRepository;
import com.showka.service.validator.u17.i.BushoDateValidator;
import com.showka.system.exception.CanNotUpdateOrDeleteException;
import com.showka.value.EigyoDate;

@Service
public class BushoDateValidatorImpl implements BushoDateValidator {

	@Autowired
	private MBushoDateRepository repo;

	@Override
	public void validateForClosing(Busho busho, EigyoDate today) {
		MBushoDate dateEntity = repo.getOne(busho.getRecordId());
		EigyoDate trueDate = new EigyoDate(dateEntity.getEigyoDate());
		if (!trueDate.isEqual(today)) {
			throw new CanNotUpdateOrDeleteException("すでに営業日が更新されている可能性があります（現在の営業日 : " + trueDate + "）");
		}
	}
}
