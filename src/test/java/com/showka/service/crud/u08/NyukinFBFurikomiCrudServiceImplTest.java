package com.showka.service.crud.u08;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.Busho;
import com.showka.domain.Kokyaku;
import com.showka.domain.MatchedFBFurikomi;
import com.showka.domain.Nyukin;
import com.showka.domain.Seikyu;
import com.showka.domain.builder.BushoBuilder;
import com.showka.domain.builder.KokyakuBuilder;
import com.showka.domain.builder.MatchedFBFurikomiBuilder;
import com.showka.domain.builder.SeikyuBuilder;
import com.showka.kubun.NyukinHohoKubun;
import com.showka.repository.i.JNyukinFBFurikomiRepository;
import com.showka.service.crud.u08.i.NyukinCrudService;
import com.showka.value.AmountOfMoney;
import com.showka.value.EigyoDate;
import com.showka.value.TheDate;

import mockit.Injectable;
import mockit.Tested;

public class NyukinFBFurikomiCrudServiceImplTest extends SimpleTestCase {

	@Tested
	private NyukinFBFurikomiCrudServiceImpl service;

	@Injectable
	private NyukinCrudService nyukinCrudService;

	@Injectable
	private JNyukinFBFurikomiRepository repo;

	@Test
	public void test_buildNyukin_01() throws Exception {
		// builder
		MatchedFBFurikomiBuilder b = new MatchedFBFurikomiBuilder();
		// 振込ID
		b.withFBFurikomiId("r-20170820-01");
		// 金額
		b.withKingaku(new AmountOfMoney(1));
		// 請求担当部署
		BushoBuilder bb = new BushoBuilder();
		bb.withCode("BS01");
		bb.withEigyoDate(new EigyoDate(2017, 1, 2));
		Busho busho = bb.build();
		// 請求先の顧客
		KokyakuBuilder kb = new KokyakuBuilder();
		kb.withCode("KK01");
		Kokyaku kokyaku = kb.build();
		// 請求
		SeikyuBuilder sb = new SeikyuBuilder();
		sb.withTantoBusho(busho);
		sb.withKokyaku(kokyaku);
		Seikyu seikyu = sb.build();
		b.withSeikyu(seikyu);
		// 伝送日付
		b.withTransmissionDate(new TheDate(2017, 1, 1));
		// builde
		MatchedFBFurikomi mathedFBFurikomi = b.build();
		// do
		Nyukin actual = service.buildNyukin(mathedFBFurikomi);
		// check
		assertEquals(busho, actual.getBusho());
		assertEquals(new EigyoDate(2017, 1, 2), actual.getDate());
		assertEquals(1, actual.getKingaku().intValue());
		assertEquals(kokyaku, actual.getKokyaku());
		assertEquals(NyukinHohoKubun.振込, actual.getNyukinHohoKubun());
	}

}
