package com.showka.service.crud.u17;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.Busho;
import com.showka.entity.MBushoDate;
import com.showka.repository.i.MBushoDateRepository;
import com.showka.service.crud.u17.i.BushoDateCrudService;
import com.showka.value.TheDate;

@Service
public class BushoDateCrudServiceImpl implements BushoDateCrudService {

	@Autowired
	private MBushoDateRepository repo;

	@Override
	public void toNextEigyoDate(Busho busho) {
		MBushoDate dateEntity = repo.getOne(busho.getRecordId());
		TheDate today = new TheDate(dateEntity.getEigyoDate());
		TheDate next = today.plusDays(1);
		dateEntity.setEigyoDate(next.toDate());
	}
}
