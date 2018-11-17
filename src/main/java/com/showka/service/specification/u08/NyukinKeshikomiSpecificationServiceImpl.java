package com.showka.service.specification.u08;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.service.persistence.u08.i.KeshikomiPersistence;
import com.showka.service.specification.u08.i.NyukinKeshikomiSpecificationService;

@Service
public class NyukinKeshikomiSpecificationServiceImpl implements NyukinKeshikomiSpecificationService {

	@Autowired
	private KeshikomiPersistence keshikomiPersistence;

	@Override
	public boolean hasKeshikomi(String nyukinId) {
		return keshikomiPersistence.getKeshikomiSetOfNyukin(nyukinId).size() > 0;
	}

}
