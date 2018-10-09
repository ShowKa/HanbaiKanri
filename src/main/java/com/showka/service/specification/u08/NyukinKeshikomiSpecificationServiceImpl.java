package com.showka.service.specification.u08;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.service.crud.u08.i.KeshikomiCrudService;
import com.showka.service.specification.u08.i.NyukinKeshikomiSpecificationService;

@Service
public class NyukinKeshikomiSpecificationServiceImpl implements NyukinKeshikomiSpecificationService {

	@Autowired
	private KeshikomiCrudService keshikomiCrudService;

	@Override
	public boolean hasKeshikomi(String nyukinId) {
		return keshikomiCrudService.getKeshikomiSetOfNyukin(nyukinId).size() > 0;
	}

}
