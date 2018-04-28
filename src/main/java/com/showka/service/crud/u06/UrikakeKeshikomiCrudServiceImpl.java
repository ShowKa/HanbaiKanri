package com.showka.service.crud.u06;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.Keshikomi;
import com.showka.domain.Urikake;
import com.showka.domain.UrikakeKeshikomi;
import com.showka.domain.builder.UrikakeKeshikomiBuilder;
import com.showka.service.crud.u05.i.UrikakeCrudService;
import com.showka.service.crud.u06.i.UrikakeKeshikomiCrudService;
import com.showka.service.crud.u08.i.KeshikomiCrudService;

@Service
public class UrikakeKeshikomiCrudServiceImpl implements UrikakeKeshikomiCrudService {

	@Autowired
	private UrikakeCrudService urikakeCrudService;

	@Autowired
	private KeshikomiCrudService keshikomiCrudService;

	@Override
	public UrikakeKeshikomi getDomain(String urikakeId) {
		// get 売掛
		Urikake urikake = urikakeCrudService.getDomain(urikakeId);
		// get 消込リスト
		Set<Keshikomi> keshikomiList = keshikomiCrudService.getKeshikomiSetOfUrikake(urikakeId);
		// build 売掛消込
		UrikakeKeshikomiBuilder ub = new UrikakeKeshikomiBuilder();
		ub.withUrikake(urikake);
		ub.withKeshikomiSet(keshikomiList);
		return ub.build();
	}
}
