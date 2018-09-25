package com.showka.service.specification.u08;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.Busho;
import com.showka.domain.Keshikomi;
import com.showka.domain.MatchedFBFurikomi;
import com.showka.domain.Nyukin;
import com.showka.domain.NyukinKeshikomi;
import com.showka.domain.Seikyu;
import com.showka.domain.SeikyuMeisai;
import com.showka.domain.Urikake;
import com.showka.domain.builder.KeshikomiBuilder;
import com.showka.service.crud.u08.i.NyukinFBFurikomiCrudService;
import com.showka.service.crud.u08.i.NyukinKeshikomiCrudService;
import com.showka.service.specification.u06.i.UrikakeKeshikomiSpecificationService;
import com.showka.service.specification.u08.i.NyukinKeshikomiBuildService;
import com.showka.value.AmountOfMoney;
import com.showka.value.EigyoDate;
import com.showka.value.TheTimestamp;

@Service
public class NyukinKeshikomiBuildServiceImpl implements NyukinKeshikomiBuildService {

	@Autowired
	private NyukinKeshikomiCrudService nyukinKeshikomiCrudService;

	@Autowired
	private NyukinFBFurikomiCrudService nyukinFBFurikomiCrudService;

	@Autowired
	private UrikakeKeshikomiSpecificationService urikakeKeshikomiSpecificationService;

	@Override
	public NyukinKeshikomi build(MatchedFBFurikomi matchedFBFurikomi) {
		// 入金
		String fbFurikomiId = matchedFBFurikomi.getFBFurikomiId();
		Nyukin nyukin = nyukinFBFurikomiCrudService.getNyukin(fbFurikomiId);
		// 消込set
		Set<Keshikomi> keshikomiSet = this.buildKeshikomiSet(nyukin, matchedFBFurikomi);
		// 入金消込構築
		String nyukinId = nyukin.getRecordId();
		NyukinKeshikomi nyukinKeshikomi = nyukinKeshikomiCrudService.getDomain(nyukinId);
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
		Busho seikyuTantoBusho = matchedFBFurikomi.getSeikyu().getTantoBusho();
		EigyoDate keshikomiDate = seikyuTantoBusho.getEigyoDate();
		// タイムスタンプ
		TheTimestamp timestamp = new TheTimestamp();
		// 消込リスト構築
		// 売掛=引数.マッチング済FB振込:-振分:-請求-:請求明細:-売掛
		Seikyu seikyu = matchedFBFurikomi.getSeikyu();
		List<SeikyuMeisai> seikyuMeisai = seikyu.getSeikyuMeisai();
		Set<Keshikomi> keshikomiSet = seikyuMeisai.parallelStream().map(m -> {
			// 売掛金残高=売掛仕様サービス#残高取得
			Urikake urikake = m.getUrikake();
			AmountOfMoney zandaka = urikakeKeshikomiSpecificationService.getZandakaOf(urikake);
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
