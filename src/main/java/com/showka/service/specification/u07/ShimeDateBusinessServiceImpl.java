package com.showka.service.specification.u07;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.z00.Busho;
import com.showka.service.specification.u07.i.ShimeDateBusinessService;
import com.showka.service.specification.z00.i.BushoDateBusinessService;
import com.showka.value.EigyoDate;
import com.showka.value.ShimeDate;
import com.showka.value.TheDate;

@Service
public class ShimeDateBusinessServiceImpl implements ShimeDateBusinessService {

	@Autowired
	private BushoDateBusinessService dateService;

	private static final int _30 = 31;
	private static final int _31 = 31;

	@Override
	public Set<ShimeDate> getShimeDate(Busho busho, EigyoDate eigyoDate) {
		// set
		Set<ShimeDate> shimeDates = new HashSet<>();
		// 営業日かつ31ではない -> 対象
		if (dateService.isEigyoDate(busho, eigyoDate)) {
			if (eigyoDate.getDayOfMonth() != _31) {
				shimeDates.add(new ShimeDate(eigyoDate));
			}
		}
		// 翌日が非営業日 -> 対象
		TheDate target1 = eigyoDate.plusDays(1);
		while (!dateService.isEigyoDate(busho, target1)) {
			if (target1.getDayOfMonth() != _31) {
				shimeDates.add(new ShimeDate(target1));
			}
			target1 = target1.plusDays(1);
		}
		// 2月の考慮 -> 29, 30を強制的に含める。
		if (eigyoDate.isLastDateOfMonth() && eigyoDate.getDayOfMonth() < _30) {
			TheDate target2 = eigyoDate.plusDays(1);
			while (target2.getDayOfMonth() < _31) {
				shimeDates.add(new ShimeDate(target2));
				target2 = target2.plusDays(1);
			}
		}
		return shimeDates;
	}
}
