package com.showka.service.crud.u08;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.Keshikomi;
import com.showka.domain.Nyukin;
import com.showka.domain.NyukinKeshikomi;
import com.showka.domain.builder.NyukinKeshikomiBuilder;
import com.showka.service.crud.u08.i.KeshikomiCrudService;
import com.showka.service.crud.u08.i.NyukinCrudService;
import com.showka.service.crud.u08.i.NyukinKeshikomiCrudService;

@Service
public class NyukinKeshikomiCrudServiceImpl implements NyukinKeshikomiCrudService {

	@Autowired
	private NyukinCrudService nyukinCrudService;

	@Autowired
	private KeshikomiCrudService keshikomiCrudService;

	@Override
	public void save(NyukinKeshikomi nyukinKeshikomi) {
		// OCC
		Nyukin nyukin = nyukinKeshikomi.getNyukin();
		nyukinCrudService.save(nyukin);
		// save 消込
		List<Keshikomi> keshikomiList = nyukinKeshikomi.getKeshikomiList();
		keshikomiList.forEach(keshikomi -> {
			keshikomiCrudService.save(keshikomi);
		});
	}

	@Override
	public NyukinKeshikomi getDomain(String nyukinId) {
		// 入金
		Nyukin nyukin = nyukinCrudService.getDomain(nyukinId);
		// 消込マップ
		List<Keshikomi> keshikomiList = keshikomiCrudService.getKeshikomiListOfNyukin(nyukinId);
		// set builder
		NyukinKeshikomiBuilder b = new NyukinKeshikomiBuilder();
		b.withNyukin(nyukin);
		b.withKeshikomiList(keshikomiList);
		// return 入金消込
		return b.build();
	}
}
