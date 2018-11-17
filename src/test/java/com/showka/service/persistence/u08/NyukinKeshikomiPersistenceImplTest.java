package com.showka.service.persistence.u08;

import static org.mockito.Mockito.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.builder.BushoBuilder;
import com.showka.domain.builder.KeshikomiBuilder;
import com.showka.domain.builder.MatchedFBFurikomiBuilder;
import com.showka.domain.builder.NyukinBuilder;
import com.showka.domain.builder.NyukinKeshikomiBuilder;
import com.showka.domain.builder.SeikyuBuilder;
import com.showka.domain.builder.UrikakeBuilder;
import com.showka.domain.u06.Urikake;
import com.showka.domain.u07.Seikyu;
import com.showka.domain.u08.Keshikomi;
import com.showka.domain.u08.MatchedFBFurikomi;
import com.showka.domain.u08.Nyukin;
import com.showka.domain.u08.NyukinKeshikomi;
import com.showka.domain.z00.Busho;
import com.showka.service.persistence.u06.i.UrikakePersistence;
import com.showka.service.persistence.u08.NyukinKeshikomiPersistenceImpl;
import com.showka.service.persistence.u08.i.KeshikomiPersistence;
import com.showka.service.persistence.u08.i.NyukinPersistence;
import com.showka.service.specification.u08.i.NyukinKeshikomiBuildService;
import com.showka.value.EigyoDate;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

public class NyukinKeshikomiPersistenceImplTest extends SimpleTestCase {

	@Tested
	@Injectable
	private NyukinKeshikomiPersistenceImpl service;

	@Injectable
	private NyukinPersistence nyukinPersistence;

	@Injectable
	private KeshikomiPersistence keshikomiPersistence;

	@Injectable
	private UrikakePersistence urikakePersistence;

	@Injectable
	private NyukinKeshikomiBuildService nyukinKeshikomiBuildService;

	@Test
	public void test01_save() throws Exception {
		// input
		// 営業日
		EigyoDate date = new EigyoDate(2017, 1, 1);
		// 入金
		NyukinBuilder nb = new NyukinBuilder();
		String nyukinId = "r-001";
		nb.withRecordId(nyukinId);
		Nyukin nyukin = nb.build();
		// 売掛
		UrikakeBuilder ub = new UrikakeBuilder();
		ub.withRecordId("r-KK01-00001");
		Urikake _urikake = ub.build();
		Urikake urikake = spy(_urikake);
		// 消込
		KeshikomiBuilder kb = new KeshikomiBuilder();
		kb.withNyukin(nyukin);
		kb.withUrikake(urikake);
		kb.withDate(date);
		Keshikomi keshikomi = kb.build();
		// 消込 set
		Set<Keshikomi> keshikomiSet = new HashSet<Keshikomi>();
		keshikomiSet.add(keshikomi);
		// 入金消込
		NyukinKeshikomiBuilder b = new NyukinKeshikomiBuilder();
		b.withNyukin(nyukin);
		b.withKeshikomiSet(keshikomiSet);
		NyukinKeshikomi nyukinKeshikomi = b.build();
		// expect
		new Expectations() {
			{
				nyukinPersistence.save(nyukin);
				urikakePersistence.save(urikake);
				keshikomiPersistence.override(nyukinId, date, keshikomiSet);
			}
		};
		// do
		service.save(date, nyukinKeshikomi);
		// verify
		new Verifications() {
			{
				nyukinPersistence.save(nyukin);
				times = 1;
				urikakePersistence.save(urikake);
				times = 1;
				keshikomiPersistence.override(nyukinId, date, keshikomiSet);
				times = 1;
			}
		};
	}

	@Test
	public void test01_getDomain() throws Exception {
		// input
		// 入金
		NyukinBuilder nb = new NyukinBuilder();
		String nyukinId = "r-001";
		nb.withRecordId(nyukinId);
		Nyukin nyukin = nb.build();
		// 消込
		KeshikomiBuilder kb = new KeshikomiBuilder();
		kb.withNyukin(nyukin);
		kb.withRecordId("r-001");
		Keshikomi keshikomi = kb.build();
		// 消込 set
		Set<Keshikomi> keshikomiSet = new HashSet<Keshikomi>();
		keshikomiSet.add(keshikomi);
		// expect
		new Expectations() {
			{
				nyukinPersistence.getDomain(nyukinId);
				result = nyukin;
				keshikomiPersistence.getKeshikomiSetOfNyukin(nyukinId);
				result = keshikomiSet;
			}
		};
		// do
		NyukinKeshikomi actual = service.getDomain(nyukinId);
		// verify
		new Verifications() {
			{
				nyukinPersistence.getDomain(nyukinId);
				times = 1;
				keshikomiPersistence.getKeshikomiSetOfNyukin(nyukinId);
				times = 1;
			}
		};
		// check
		assertEquals(nyukin, actual.getNyukin());
		assertEquals(1, actual.getKeshikomiSet().size());
		assertTrue(actual.getKeshikomiSet().contains(keshikomi));
	}

	@Test
	public void test01_cancel() throws Exception {
		// input
		EigyoDate eigyoDate = new EigyoDate(2017, 1, 1);
		BushoBuilder bb = new BushoBuilder();
		bb.withEigyoDate(eigyoDate);
		Busho busho = bb.build();
		// 入金
		NyukinBuilder nb = new NyukinBuilder();
		String nyukinId = "r-001";
		nb.withRecordId(nyukinId);
		nb.withBusho(busho);
		Nyukin nyukin = nb.build();
		// 消込IDs
		Set<String> ids = new HashSet<String>();
		String target = "r-001";
		ids.add(target);
		// expect
		new Expectations() {
			{
				nyukinPersistence.save(nyukin);
				keshikomiPersistence.cancel(target, eigyoDate);
			}
		};
		// do
		service.cancel(nyukin, ids);
		// verify
		new Verifications() {
			{
				nyukinPersistence.save(nyukin);
				times = 1;
				keshikomiPersistence.cancel(target, eigyoDate);
				times = 1;
			}
		};
	}

	/**
	 * FBマッチング済振込の消込.
	 * 
	 * <pre>
	 * 確認事項.
	 * - 入金消込をマッチング済FB振込から構築。
	 * - 請求担当部署の営業日を基準にして、入金消込を登録する。
	 * </pre>
	 */
	@Test
	public void test01_Save_MatchedFBFurikomi() throws Exception {
		// input
		// 営業日
		EigyoDate eigyoDate = new EigyoDate(2017, 8, 20);
		// 請求担当部署
		BushoBuilder bb = new BushoBuilder();
		bb.withEigyoDate(eigyoDate);
		Busho busho = bb.build();
		// 請求
		SeikyuBuilder sb = new SeikyuBuilder();
		sb.withTantoBusho(busho);
		Seikyu seikyu = sb.build();
		// マッチング済FB振込
		MatchedFBFurikomiBuilder mb = new MatchedFBFurikomiBuilder();
		mb.withSeikyu(seikyu);
		MatchedFBFurikomi matchedFBFurikomi = mb.build();
		// 入金消込
		NyukinKeshikomiBuilder nb = new NyukinKeshikomiBuilder();
		nb.withKeshikomiSet(new HashSet<>());
		NyukinKeshikomi nyukinKeshikomi = nb.build();
		// expect
		new Expectations() {
			{
				nyukinKeshikomiBuildService.build(matchedFBFurikomi);
				result = nyukinKeshikomi;
				service.save(eigyoDate, nyukinKeshikomi);
			}
		};
		// do
		service.save(matchedFBFurikomi);
		// verify
		new Verifications() {
			{
				nyukinKeshikomiBuildService.build(matchedFBFurikomi);
				times = 1;
				service.save(eigyoDate, nyukinKeshikomi);
				times = 1;
			}
		};
	}
}
