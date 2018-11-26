package com.showka.service.query.u08;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.builder.NyukinKeshikomiBuilder;
import com.showka.domain.u08.Keshikomi;
import com.showka.domain.u08.Nyukin;
import com.showka.domain.u08.NyukinKeshikomi;
import com.showka.service.crud.u08.i.NyukinCrud;
import com.showka.service.query.u08.i.KeshikomiQuery;
import com.showka.service.query.u08.i.NyukinKeshikomiQuery;

@Service
public class NyukinKeshikomiQueryImpl implements NyukinKeshikomiQuery {

	@Autowired
	private NyukinCrud nyukinCrud;

	@Autowired
	private KeshikomiQuery keshikomiQuery;

	@Override
	public boolean hasKeshikomi(String nyukinId) {
		return keshikomiQuery.getOfNyukin(nyukinId).size() > 0;
	}

	@Override
	public NyukinKeshikomi getDomain(String nyukinId) {
		// 入金
		Nyukin nyukin = nyukinCrud.getDomain(nyukinId);
		// 消込マップ
		Set<Keshikomi> keshikomiList = keshikomiQuery.getOfNyukin(nyukinId);
		// set builder
		NyukinKeshikomiBuilder b = new NyukinKeshikomiBuilder();
		b.withNyukin(nyukin);
		b.withKeshikomiSet(keshikomiList);
		// return 入金消込
		return b.build();
	}
}
