package com.showka.service.construct.u08;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.builder.KeshikomiBuilder;
import com.showka.domain.u06.Urikake;
import com.showka.domain.u06.UrikakeKeshikomi;
import com.showka.domain.u07.Seikyu;
import com.showka.domain.u07.SeikyuMeisai;
import com.showka.domain.u08.Keshikomi;
import com.showka.domain.u08.MatchedFBFurikomi;
import com.showka.domain.u08.Nyukin;
import com.showka.domain.u08.NyukinKeshikomi;
import com.showka.service.construct.u08.i.NyukinKeshikomiConstruct;
import com.showka.service.query.u06.i.UrikakeKeshikomiQuery;
import com.showka.service.query.u08.i.NyukinFBFurikomiQuery;
import com.showka.service.query.u08.i.NyukinKeshikomiQuery;
import com.showka.value.AmountOfMoney;
import com.showka.value.EigyoDate;
import com.showka.value.TheTimestamp;

@Service
public class NyukinKeshikomiConstructImpl implements NyukinKeshikomiConstruct {

	@Autowired
	private NyukinKeshikomiQuery nyukinKeshikomiQuery;

	@Autowired
	private NyukinFBFurikomiQuery nyukinFBFurikomiQuery;

	@Autowired
	private UrikakeKeshikomiQuery urikakeKeshikomiQuery;

	@Override
	public NyukinKeshikomi by(MatchedFBFurikomi matchedFBFurikomi) {
		// 入金
		String fbFurikomiId = matchedFBFurikomi.getFBFurikomiId();
		Nyukin nyukin = nyukinFBFurikomiQuery.getNyukin(fbFurikomiId);
		// 消込set
		Set<Keshikomi> keshikomiSet = this.buildKeshikomiSet(nyukin, matchedFBFurikomi);
		// 入金消込構築
		String nyukinId = nyukin.getRecordId();
		NyukinKeshikomi nyukinKeshikomi = nyukinKeshikomiQuery.getDomain(nyukinId);
		nyukinKeshikomi.merge(keshikomiSet);
		return nyukinKeshikomi;
	}

	/**
	 * 消込構築.
	 * 
	 * @param nyukin
	 *            入金
	 * @param matchedFBFurikomi
	 *            マッチング済FB振込
	 * @return 消込set
	 */
	Set<Keshikomi> buildKeshikomiSet(Nyukin nyukin, MatchedFBFurikomi matchedFBFurikomi) {
		// 消込日=請求担当部署の営業日
		EigyoDate keshikomiDate = matchedFBFurikomi.getSeikyuTantoBushoEigyoDate();
		// タイムスタンプ
		TheTimestamp timestamp = new TheTimestamp();
		// 消込リスト構築
		// 売掛=引数.マッチング済FB振込:-振分:-請求-:請求明細:-売掛
		Seikyu seikyu = matchedFBFurikomi.getSeikyu();
		List<SeikyuMeisai> seikyuMeisai = seikyu.getSeikyuMeisai();
		Set<Keshikomi> keshikomiSet = seikyuMeisai.parallelStream().map(m -> {
			// 売掛金残高=売掛仕様サービス#残高取得
			Urikake urikake = m.getUrikake();
			UrikakeKeshikomi urikakeKeshikomi = urikakeKeshikomiQuery.get(urikake.getRecordId());
			AmountOfMoney zandaka = urikakeKeshikomi.getZandaka();
			// 消込構築
			KeshikomiBuilder kb = new KeshikomiBuilder();
			kb.withDate(keshikomiDate);
			kb.withKingaku(zandaka);
			kb.withNyukin(nyukin);
			kb.withTimestamp(timestamp);
			kb.withUrikake(urikake);
			// FIXME mergeするときに必要。消込の同値条件はid。
			kb.withRecordId("dummy_" + UUID.randomUUID().toString());
			return kb.build();
		}).collect(Collectors.toSet());
		return keshikomiSet;
	}
}
