package com.showka.service.query.z00;

import org.springframework.stereotype.Service;

import com.showka.domain.z00.Busho;
import com.showka.service.query.z00.i.BushoDateQuery;
import com.showka.value.EigyoDate;
import com.showka.value.TheDate;

@Service
public class BushoDateQueryImpl implements BushoDateQuery {

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
