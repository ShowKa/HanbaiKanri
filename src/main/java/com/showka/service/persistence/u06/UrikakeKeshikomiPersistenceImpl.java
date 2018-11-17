package com.showka.service.persistence.u06;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.builder.UrikakeKeshikomiBuilder;
import com.showka.domain.u06.Urikake;
import com.showka.domain.u06.UrikakeKeshikomi;
import com.showka.domain.u08.Keshikomi;
import com.showka.service.persistence.u06.i.UrikakeKeshikomiPersistence;
import com.showka.service.persistence.u06.i.UrikakePersistence;
import com.showka.service.persistence.u08.i.KeshikomiPersistence;

@Service
public class UrikakeKeshikomiPersistenceImpl implements UrikakeKeshikomiPersistence {

	@Autowired
	private UrikakePersistence urikakePersistence;

	@Autowired
	private KeshikomiPersistence keshikomiPersistence;

	@Override
	public UrikakeKeshikomi getDomain(String urikakeId) {
		// get 売掛
		Urikake urikake = urikakePersistence.getDomainById(urikakeId);
		// get 消込リスト
		Set<Keshikomi> keshikomiList = keshikomiPersistence.getKeshikomiSetOfUrikake(urikakeId);
		// build 売掛消込
		UrikakeKeshikomiBuilder ub = new UrikakeKeshikomiBuilder();
		ub.withUrikake(urikake);
		ub.withKeshikomiSet(keshikomiList);
		return ub.build();
	}
}
