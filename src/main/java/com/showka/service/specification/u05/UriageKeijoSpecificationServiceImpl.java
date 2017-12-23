package com.showka.service.specification.u05;

import org.springframework.stereotype.Service;

import com.showka.domain.BushoDomain;
import com.showka.domain.UriageDomain;
import com.showka.service.specification.u05.i.UriageKeijoSpecificationService;

@Service
public class UriageKeijoSpecificationServiceImpl implements UriageKeijoSpecificationService {

	@Override
	public boolean isKeijoZumi(UriageDomain uriage) {
		BushoDomain busho = uriage.getKokyaku().getShukanBusho();
		if (busho.getEigyoDate().isAfter(uriage.getKeijoDate())) {
			return true;
		} else {
			return false;
		}
	}
}
