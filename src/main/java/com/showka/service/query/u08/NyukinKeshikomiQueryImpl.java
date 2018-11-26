package com.showka.service.query.u08;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.service.query.u08.i.KeshikomiQuery;
import com.showka.service.query.u08.i.NyukinKeshikomiQuery;

@Service
public class NyukinKeshikomiQueryImpl implements NyukinKeshikomiQuery {

	@Autowired
	private KeshikomiQuery keshikomiQuery;

	@Override
	public boolean hasKeshikomi(String nyukinId) {
		return keshikomiQuery.getOfNyukin(nyukinId).size() > 0;
	}
}
