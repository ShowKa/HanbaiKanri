package com.showka.service.specification.z00;

import org.springframework.stereotype.Service;

import com.showka.domain.z00.Busho;
import com.showka.service.specification.z00.i.BushoDateBusinessService;
import com.showka.value.EigyoDate;
import com.showka.value.TheDate;

@Service
public class BushoDateBusinessServiceImpl implements BushoDateBusinessService {

	@Override
	public EigyoDate getNext(Busho busho, EigyoDate eigyoDate) {
		TheDate next = eigyoDate.plusDays(1);
		while (next.isWeekend()) {
			next = next.plusDays(1);
		}
		return new EigyoDate(next);
	}

	@Override
	public boolean isEigyoDate(Busho busho, TheDate date) {
		return date.isWeekDay();
	}

}
