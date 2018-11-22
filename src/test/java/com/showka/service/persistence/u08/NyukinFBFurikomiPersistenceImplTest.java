package com.showka.service.persistence.u08;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.builder.BushoBuilder;
import com.showka.domain.builder.KokyakuBuilder;
import com.showka.domain.builder.MatchedFBFurikomiBuilder;
import com.showka.domain.builder.NyukinBuilder;
import com.showka.domain.builder.SeikyuBuilder;
import com.showka.domain.u01.Kokyaku;
import com.showka.domain.u07.Seikyu;
import com.showka.domain.u08.MatchedFBFurikomi;
import com.showka.domain.u08.Nyukin;
import com.showka.domain.z00.Busho;
import com.showka.entity.JNyukinFBFurikomi;
import com.showka.kubun.NyukinHohoKubun;
import com.showka.repository.i.JNyukinFBFurikomiRepository;
import com.showka.service.crud.u08.i.NyukinCrud;
import com.showka.service.persistence.u08.NyukinFBFurikomiPersistenceImpl;
import com.showka.value.AmountOfMoney;
import com.showka.value.EigyoDate;
import com.showka.value.TheDate;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

public class NyukinFBFurikomiPersistenceImplTest extends SimpleTestCase {

	@Tested
	@Injectable
	private NyukinFBFurikomiPersistenceImpl service;

	@Injectable
	private NyukinCrud nyukinPersistence;

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

	/**
	 * 入金関係テーブルのレコードの入金IDを取得する。その入金IDを用いて入金を取得する。
	 */
	@Test
	public void test_GetNyukin_01() throws Exception {
		// input
		String fbFurikomiId = "r-20170820-001";
		// entity
		JNyukinFBFurikomi entity = new JNyukinFBFurikomi();
		String nyukinId = "r-001";
		entity.setNyukinId(nyukinId);
		// 入金
		NyukinBuilder nb = new NyukinBuilder();
		nb.withRecordId(nyukinId);
		Nyukin nyukin = nb.build();
		// expect
		new Expectations() {
			{
				service.findByFbFurikomiId(fbFurikomiId);
				result = entity;
				nyukinPersistence.getDomain(nyukinId);
				result = nyukin;
			}
		};
		// do
		Nyukin actual = service.getNyukin(fbFurikomiId);
		// verify
		new Verifications() {
			{
				service.findByFbFurikomiId(fbFurikomiId);
				times = 1;
				nyukinPersistence.getDomain(nyukinId);
				times = 1;
			}
		};
		// check
		assertEquals(nyukin, actual);
	}

}
