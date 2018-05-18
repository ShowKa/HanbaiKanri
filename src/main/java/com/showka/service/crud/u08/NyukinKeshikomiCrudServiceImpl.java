package com.showka.service.crud.u08;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.Keshikomi;
import com.showka.domain.Nyukin;
import com.showka.domain.NyukinKeshikomi;
import com.showka.domain.Urikake;
import com.showka.domain.builder.NyukinKeshikomiBuilder;
import com.showka.service.crud.u05.i.UrikakeCrudService;
import com.showka.service.crud.u08.i.KeshikomiCrudService;
import com.showka.service.crud.u08.i.NyukinCrudService;
import com.showka.service.crud.u08.i.NyukinKeshikomiCrudService;
import com.showka.value.EigyoDate;

@Service
public class NyukinKeshikomiCrudServiceImpl implements NyukinKeshikomiCrudService {

	@Autowired
	private NyukinCrudService nyukinCrudService;

	@Autowired
	private KeshikomiCrudService keshikomiCrudService;

	@Autowired
	private UrikakeCrudService urikakeCrudService;

	@Override
	public void save(EigyoDate date, NyukinKeshikomi nyukinKeshikomi) {
		// OCC
		Nyukin nyukin = nyukinKeshikomi.getNyukin();
		nyukinCrudService.save(nyukin);
		// FIXME OCC should remove to another service
		Set<Urikake> urikakeSet = nyukinKeshikomi.getUrikakeSetOf(date);
		urikakeSet.forEach(u -> {
			urikakeCrudService.save(u);
		});
		// save 消込
		Set<Keshikomi> keshikomiSet = nyukinKeshikomi.getKeshikomiSet();
		keshikomiCrudService.override(nyukin.getRecordId(), date, keshikomiSet);
	}

	@Override
	public NyukinKeshikomi getDomain(String nyukinId) {
		// 入金
		Nyukin nyukin = nyukinCrudService.getDomain(nyukinId);
		// 消込マップ
		Set<Keshikomi> keshikomiList = keshikomiCrudService.getKeshikomiSetOfNyukin(nyukinId);
		// set builder
		NyukinKeshikomiBuilder b = new NyukinKeshikomiBuilder();
		b.withNyukin(nyukin);
		b.withKeshikomiSet(keshikomiList);
		// return 入金消込
		return b.build();
	}

	@Override
	public void cancel(Nyukin nyukin, Set<String> keshikomiIdSet) {
		// OCC
		nyukinCrudService.save(nyukin);
		// cancel
		EigyoDate eigyoDate = nyukin.getBusho().getEigyoDate();
		keshikomiIdSet.forEach(id -> {
			keshikomiCrudService.cancel(id, eigyoDate);
		});
	}
}
