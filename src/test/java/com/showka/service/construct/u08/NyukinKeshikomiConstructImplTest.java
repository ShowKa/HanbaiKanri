package com.showka.service.construct.u08;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.builder.BushoBuilder;
import com.showka.domain.builder.KeshikomiBuilder;
import com.showka.domain.builder.MatchedFBFurikomiBuilder;
import com.showka.domain.builder.NyukinBuilder;
import com.showka.domain.builder.NyukinKeshikomiBuilder;
import com.showka.domain.builder.SeikyuBuilder;
import com.showka.domain.builder.SeikyuMeisaiBuilder;
import com.showka.domain.builder.UrikakeBuilder;
import com.showka.domain.builder.UrikakeKeshikomiBuilder;
import com.showka.domain.u06.Urikake;
import com.showka.domain.u06.UrikakeKeshikomi;
import com.showka.domain.u07.Seikyu;
import com.showka.domain.u07.SeikyuMeisai;
import com.showka.domain.u08.Keshikomi;
import com.showka.domain.u08.MatchedFBFurikomi;
import com.showka.domain.u08.Nyukin;
import com.showka.domain.u08.NyukinKeshikomi;
import com.showka.domain.z00.Busho;
import com.showka.service.query.u06.i.UrikakeKeshikomiQuery;
import com.showka.service.query.u08.i.NyukinFBFurikomiQuery;
import com.showka.service.query.u08.i.NyukinKeshikomiQuery;
import com.showka.value.AmountOfMoney;
import com.showka.value.EigyoDate;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

public class NyukinKeshikomiConstructImplTest extends SimpleTestCase {

	@Tested
	@Injectable
	private NyukinKeshikomiConstructImpl service;

	@Injectable
	private NyukinKeshikomiQuery nyukinKeshikomiQuery;

	@Injectable
	private NyukinFBFurikomiQuery nyukinFBFurikomiQuery;

	@Injectable
	private UrikakeKeshikomiQuery urikakeKeshikomiQuery;

	@Test
	public void test_BuildKeshikomiSet_01() throws Exception {
		// input
		// 営業日
		EigyoDate eigyoDate = new EigyoDate(2017, 8, 20);
		// 請求担当部署
		BushoBuilder bb = new BushoBuilder();
		bb.withEigyoDate(eigyoDate);
		Busho busho = bb.build();
		// 売掛
		UrikakeBuilder ub = new UrikakeBuilder();
		ub.withKingaku(1);
		Urikake urikake = ub.build();
		// 売掛消込
		UrikakeKeshikomiBuilder ukb = new UrikakeKeshikomiBuilder();
		ukb.withUrikake(urikake);
		ukb.withKeshikomiSet(new HashSet<>());
		UrikakeKeshikomi urikakeKeshikomi = ukb.build();
		// 請求明細
		SeikyuMeisaiBuilder smb = new SeikyuMeisaiBuilder();
		smb.withUrikake(urikake);
		SeikyuMeisai seikyuMeisai = smb.build();
		List<SeikyuMeisai> seikyuMeisaiList = new ArrayList<>();
		seikyuMeisaiList.add(seikyuMeisai);
		// 請求
		SeikyuBuilder sb = new SeikyuBuilder();
		sb.withTantoBusho(busho);
		sb.withSeikyuMeisai(seikyuMeisaiList);
		Seikyu seikyu = sb.build();
		// マッチング済FB振込
		MatchedFBFurikomiBuilder mb = new MatchedFBFurikomiBuilder();
		mb.withSeikyu(seikyu);
		MatchedFBFurikomi matchedFBFurikomi = mb.build();
		// 入金
		NyukinBuilder nb = new NyukinBuilder();
		Nyukin nyukin = nb.build();

		// expect
		new Expectations() {
			{
				urikakeKeshikomiQuery.get(urikake.getRecordId());
				result = urikakeKeshikomi;
			}
		};
		// do
		Set<Keshikomi> actual = service.buildKeshikomiSet(nyukin, matchedFBFurikomi);
		// verify
		new Verifications() {
			{
				urikakeKeshikomiQuery.get(urikake.getRecordId());
				times = 1;
			}
		};
		// check
		assertEquals(1, actual.size());
		actual.forEach(a -> {
			assertEquals(nyukin, a.getNyukin());
			assertEquals(urikake, a.getUrikake());
			assertEquals(eigyoDate, a.getDate());
			assertEquals(new AmountOfMoney(1), a.getKingaku());
		});
	}

	@Test
	public void test_Build_01() throws Exception {
		// input
		// FB振込Id
		String fbFurikomiId = "r-20170820-01";
		// マッチング済FB振込
		MatchedFBFurikomiBuilder mb = new MatchedFBFurikomiBuilder();
		mb.withFBFurikomiId(fbFurikomiId);
		MatchedFBFurikomi matchedFBFurikomi = mb.build();
		// 消込
		KeshikomiBuilder kb = new KeshikomiBuilder();
		Keshikomi keshikomi = kb.build();
		Set<Keshikomi> keshikomiSet = new HashSet<>();
		keshikomiSet.add(keshikomi);
		// 入金
		NyukinBuilder nb = new NyukinBuilder();
		String nyukinId = "r-001";
		nb.withRecordId(nyukinId);
		Nyukin nyukin = nb.build();
		// 入金消込
		NyukinKeshikomiBuilder nkb = new NyukinKeshikomiBuilder();
		nkb.withKeshikomiSet(new HashSet<Keshikomi>());
		NyukinKeshikomi nyukinKeshikomi = nkb.build();
		// expect
		new Expectations() {
			{
				nyukinFBFurikomiQuery.getNyukin(fbFurikomiId);
				result = nyukin;
				service.buildKeshikomiSet(nyukin, matchedFBFurikomi);
				result = keshikomiSet;
				nyukinKeshikomiQuery.getDomain(nyukinId);
				result = nyukinKeshikomi;
			}
		};
		// do
		NyukinKeshikomi actual = service.by(matchedFBFurikomi);
		// check
		assertEquals(1, actual.getKeshikomiSet().size());
		assertTrue(actual.getKeshikomiSet().contains(keshikomi));
	}
}
