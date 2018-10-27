package com.showka.service.crud.u17;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.z00.Busho;
import com.showka.entity.MBushoDate;
import com.showka.repository.i.MBushoDateRepository;
import com.showka.service.crud.u17.i.BushoDateCrudService;
import com.showka.service.specification.z00.i.BushoDateBusinessService;
import com.showka.value.EigyoDate;

@Service
public class BushoDateCrudServiceImpl implements BushoDateCrudService {

	@Autowired
	private BushoDateBusinessService bushoDateBusinessService;

	@Autowired
	private MBushoDateRepository repo;

	@Override
	public void toNextEigyoDate(Busho busho) {
		MBushoDate dateEntity = repo.getOne(busho.getRecordId());
		EigyoDate today = new EigyoDate(dateEntity.getEigyoDate());
		EigyoDate next = bushoDateBusinessService.getNext(busho, today);
		dateEntity.setEigyoDate(next.toDate());
	}
}
