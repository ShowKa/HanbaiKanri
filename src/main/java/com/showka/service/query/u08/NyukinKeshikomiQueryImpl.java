package com.showka.service.query.u08;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.service.persistence.u08.i.KeshikomiPersistence;
import com.showka.service.query.u08.i.NyukinKeshikomiQuery;

@Service
public class NyukinKeshikomiQueryImpl implements NyukinKeshikomiQuery {

	@Autowired
	private KeshikomiPersistence keshikomiPersistence;

	@Override
	public boolean hasKeshikomi(String nyukinId) {
		return keshikomiPersistence.getKeshikomiSetOfNyukin(nyukinId).size() > 0;
	}

}
