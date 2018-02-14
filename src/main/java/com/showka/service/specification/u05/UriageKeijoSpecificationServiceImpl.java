package com.showka.service.specification.u05;

import org.springframework.stereotype.Service;

import com.showka.domain.Busho;
import com.showka.domain.Uriage;
import com.showka.service.specification.u05.i.UriageKeijoSpecificationService;

@Service
public class UriageKeijoSpecificationServiceImpl implements UriageKeijoSpecificationService {

	@Override
	public boolean isKeijoZumi(Uriage uriage) {
		Busho busho = uriage.getKokyaku().getShukanBusho();
		if (busho.getEigyoDate().isAfter(uriage.getKeijoDate())) {
			return true;
		} else {
			return false;
		}
	}
}
