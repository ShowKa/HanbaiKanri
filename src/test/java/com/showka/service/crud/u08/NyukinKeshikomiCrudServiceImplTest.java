package com.showka.service.crud.u08;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.Keshikomi;
import com.showka.domain.Nyukin;
import com.showka.domain.NyukinKeshikomi;
import com.showka.domain.builder.KeshikomiBuilder;
import com.showka.domain.builder.NyukinBuilder;
import com.showka.domain.builder.NyukinKeshikomiBuilder;
import com.showka.service.crud.u08.i.KeshikomiCrudService;
import com.showka.service.crud.u08.i.NyukinCrudService;
import com.showka.value.EigyoDate;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

public class NyukinKeshikomiCrudServiceImplTest extends SimpleTestCase {

	@Tested
	private NyukinKeshikomiCrudServiceImpl service;

	@Injectable
	private NyukinCrudService nyukinCrudService;

	@Injectable
	private KeshikomiCrudService keshikomiCrudService;

	@Test
	public void test01_save() throws Exception {
		// input
		// 入金
		NyukinBuilder nb = new NyukinBuilder();
		String nyukinId = "r-001";
		nb.withRecordId(nyukinId);
		Nyukin nyukin = nb.build();
		// 消込
		KeshikomiBuilder kb = new KeshikomiBuilder();
		kb.withNyukin(nyukin);
		Keshikomi keshikomi = kb.build();
		// 消込 set
		Set<Keshikomi> keshikomiSet = new HashSet<Keshikomi>();
		keshikomiSet.add(keshikomi);
		// 入金消込
		NyukinKeshikomiBuilder b = new NyukinKeshikomiBuilder();
		b.withNyukin(nyukin);
		b.withKeshikomiSet(keshikomiSet);
		NyukinKeshikomi nyukinKeshikomi = b.build();
		// 営業日
		EigyoDate date = new EigyoDate(2017, 1, 1);
		// expect
		new Expectations() {
			{
				nyukinCrudService.save(nyukin);
				keshikomiCrudService.override(nyukinId, date, keshikomiSet);
			}
		};
		// do
		service.save(date, nyukinKeshikomi);
		// verify
		new Verifications() {
			{
				nyukinCrudService.save(nyukin);
				times = 1;
				keshikomiCrudService.override(nyukinId, date, keshikomiSet);
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
				nyukinCrudService.getDomain(nyukinId);
				result = nyukin;
				keshikomiCrudService.getKeshikomiSetOfNyukin(nyukinId);
				result = keshikomiSet;
			}
		};
		// do
		NyukinKeshikomi actual = service.getDomain(nyukinId);
		// verify
		new Verifications() {
			{
				nyukinCrudService.getDomain(nyukinId);
				times = 1;
				keshikomiCrudService.getKeshikomiSetOfNyukin(nyukinId);
				times = 1;
			}
		};
		// check
		assertEquals(nyukin, actual.getNyukin());
		assertEquals(1, actual.getKeshikomiSet().size());
		assertTrue(actual.getKeshikomiSet().contains(keshikomi));
	}
}
