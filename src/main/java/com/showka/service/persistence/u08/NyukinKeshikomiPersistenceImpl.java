package com.showka.service.persistence.u08;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.builder.NyukinKeshikomiBuilder;
import com.showka.domain.u06.Urikake;
import com.showka.domain.u08.Keshikomi;
import com.showka.domain.u08.MatchedFBFurikomi;
import com.showka.domain.u08.Nyukin;
import com.showka.domain.u08.NyukinKeshikomi;
import com.showka.service.construct.u08.i.NyukinKeshikomiConstruct;
import com.showka.service.crud.u06.i.UrikakeCrud;
import com.showka.service.crud.u08.i.NyukinCrud;
import com.showka.service.persistence.u08.i.KeshikomiPersistence;
import com.showka.service.persistence.u08.i.NyukinKeshikomiPersistence;
import com.showka.service.query.u08.i.KeshikomiQuery;
import com.showka.value.EigyoDate;

@Service
public class NyukinKeshikomiPersistenceImpl implements NyukinKeshikomiPersistence {

	@Autowired
	private NyukinCrud nyukinPersistence;

	@Autowired
	private KeshikomiPersistence keshikomiPersistence;

	@Autowired
	private KeshikomiQuery keshikomiQuery;

	@Autowired
	private UrikakeCrud urikakePersistence;

	@Autowired
	private NyukinKeshikomiConstruct nyukinKeshikomiBuildService;

	@Override
	public void save(EigyoDate date, NyukinKeshikomi nyukinKeshikomi) {
		// OCC
		Nyukin nyukin = nyukinKeshikomi.getNyukin();
		nyukinPersistence.save(nyukin);
		// FIXME OCC should remove to another service
		Set<Urikake> urikakeSet = nyukinKeshikomi.getUrikakeSetOf(date);
		urikakeSet.forEach(u -> {
			urikakePersistence.save(u);
		});
		// save 消込
		Set<Keshikomi> keshikomiSet = nyukinKeshikomi.getKeshikomiSet();
		keshikomiPersistence.override(nyukin.getRecordId(), date, keshikomiSet);
	}

	// 入金消込を構築して同サービス.保存(営業日,入金消込)を呼ぶ。
	@Override
	public void save(MatchedFBFurikomi matchedFBFurikomi) {
		// 営業日=請求担当部署の営業日
		EigyoDate eigyoDate = matchedFBFurikomi.getSeikyuTantoBushoEigyoDate();
		// 入金消込
		NyukinKeshikomi nyukinKeshikomi = nyukinKeshikomiBuildService.by(matchedFBFurikomi);
		// save
		this.save(eigyoDate, nyukinKeshikomi);
	}

	@Override
	public NyukinKeshikomi getDomain(String nyukinId) {
		// 入金
		Nyukin nyukin = nyukinPersistence.getDomain(nyukinId);
		// 消込マップ
		Set<Keshikomi> keshikomiList = keshikomiQuery.getOfNyukin(nyukinId);
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
		nyukinPersistence.save(nyukin);
		// cancel
		EigyoDate eigyoDate = nyukin.getBusho().getEigyoDate();
		keshikomiIdSet.forEach(id -> {
			keshikomiPersistence.cancel(id, eigyoDate);
		});
	}
}
