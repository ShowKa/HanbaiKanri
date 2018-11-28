package com.showka.service.persistence.u08;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.showka.value.EigyoDate;

@Service
public class NyukinKeshikomiPersistenceImpl implements NyukinKeshikomiPersistence {

	@Autowired
	private NyukinCrud nyukinCrud;

	@Autowired
	private KeshikomiPersistence keshikomiPersistence;

	@Autowired
	private UrikakeCrud urikakeCrud;

	@Autowired
	private NyukinKeshikomiConstruct nyukinKeshikomiConstruct;

	@Override
	public void save(EigyoDate date, NyukinKeshikomi nyukinKeshikomi) {
		// OCC
		Nyukin nyukin = nyukinKeshikomi.getNyukin();
		nyukinCrud.save(nyukin);
		// FIXME OCC should remove to another service
		Set<Urikake> urikakeSet = nyukinKeshikomi.getUrikakeSetOf(date);
		urikakeSet.forEach(u -> {
			urikakeCrud.save(u);
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
		NyukinKeshikomi nyukinKeshikomi = nyukinKeshikomiConstruct.by(matchedFBFurikomi);
		// save
		this.save(eigyoDate, nyukinKeshikomi);
	}

	@Override
	public void cancel(Nyukin nyukin, Set<String> keshikomiIdSet) {
		// OCC
		nyukinCrud.save(nyukin);
		// cancel
		EigyoDate eigyoDate = nyukin.getBusho().getEigyoDate();
		keshikomiIdSet.forEach(id -> {
			keshikomiPersistence.cancel(id, eigyoDate);
		});
	}
}
