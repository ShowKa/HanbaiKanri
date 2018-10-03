package com.showka.service.specification.u08;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.Busho;
import com.showka.domain.Keshikomi;
import com.showka.domain.MatchedFBFurikomi;
import com.showka.domain.Nyukin;
import com.showka.domain.NyukinKeshikomi;
import com.showka.domain.Seikyu;
import com.showka.domain.SeikyuMeisai;
import com.showka.domain.Urikake;
import com.showka.domain.builder.BushoBuilder;
import com.showka.domain.builder.KeshikomiBuilder;
import com.showka.domain.builder.MatchedFBFurikomiBuilder;
import com.showka.domain.builder.NyukinBuilder;
import com.showka.domain.builder.NyukinKeshikomiBuilder;
import com.showka.domain.builder.SeikyuBuilder;
import com.showka.domain.builder.SeikyuMeisaiBuilder;
import com.showka.domain.builder.UrikakeBuilder;
import com.showka.service.crud.u08.i.NyukinFBFurikomiCrudService;
import com.showka.service.crud.u08.i.NyukinKeshikomiCrudService;
import com.showka.service.specification.u06.i.UrikakeKeshikomiSpecificationService;
import com.showka.value.AmountOfMoney;
import com.showka.value.EigyoDate;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

public class NyukinKeshikomiBuildServiceImplTest extends SimpleTestCase {

	@Tested
	@Injectable
	private NyukinKeshikomiBuildServiceImpl service;

	@Injectable
	private NyukinKeshikomiCrudService nyukinKeshikomiCrudService;

	@Injectable
	private NyukinFBFurikomiCrudService nyukinFBFurikomiCrudService;

	@Injectable
	private UrikakeKeshikomiSpecificationService urikakeKeshikomiSpecificationService;

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
		Urikake urikake = ub.build();
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
		// 売掛残高
		AmountOfMoney zandaka = new AmountOfMoney(1);
		// expect
		new Expectations() {
			{
				urikakeKeshikomiSpecificationService.getZandakaOf(urikake);
				result = zandaka;
			}
		};
		// do
		Set<Keshikomi> actual = service.buildKeshikomiSet(nyukin, matchedFBFurikomi);
		// verify
		new Verifications() {
			{
				urikakeKeshikomiSpecificationService.getZandakaOf(urikake);
				times = 1;
			}
		};
		// check
		assertEquals(1, actual.size());
		actual.forEach(a -> {
			assertEquals(nyukin, a.getNyukin());
			assertEquals(urikake, a.getUrikake());
			assertEquals(eigyoDate, a.getDate());
			assertEquals(zandaka, a.getKingaku());
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
				nyukinFBFurikomiCrudService.getNyukin(fbFurikomiId);
				result = nyukin;
				service.buildKeshikomiSet(nyukin, matchedFBFurikomi);
				result = keshikomiSet;
				nyukinKeshikomiCrudService.getDomain(nyukinId);
				result = nyukinKeshikomi;
			}
		};
		// do
		NyukinKeshikomi actual = service.build(matchedFBFurikomi);
		// check
		assertEquals(1, actual.getKeshikomiSet().size());
		assertTrue(actual.getKeshikomiSet().contains(keshikomi));
	}
}
