package com.showka.service.query.u08;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.builder.KeshikomiBuilder;
import com.showka.domain.builder.NyukinBuilder;
import com.showka.domain.u08.Keshikomi;
import com.showka.domain.u08.Nyukin;
import com.showka.domain.u08.NyukinKeshikomi;
import com.showka.service.crud.u08.i.NyukinCrud;
import com.showka.service.query.u08.i.KeshikomiQuery;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

public class NyukinKeshikomiQueryImplTest extends SimpleTestCase {

	@Tested
	private NyukinKeshikomiQueryImpl service;

	@Injectable
	private NyukinCrud nyukinCrud;

	@Injectable
	private KeshikomiQuery keshikomiQuery;

	@Test
	public void test_HasKeshikomi_01() throws Exception {
		// input
		String nyukinId = "r-001";
		// mock
		Set<Keshikomi> kesikomiSet = new HashSet<>();
		// expect
		new Expectations() {
			{
				keshikomiQuery.getOfNyukin(nyukinId);
				result = kesikomiSet;
			}
		};
		// do
		boolean actual = service.hasKeshikomi(nyukinId);
		// verify
		new Verifications() {
			{
				keshikomiQuery.getOfNyukin(nyukinId);
				times = 1;
			}
		};
		// check
		assertFalse(actual);
	}

	@Test
	public void test_HasKeshikomi_02() throws Exception {
		// input
		String nyukinId = "r-001";
		// mock
		Set<Keshikomi> kesikomiSet = new HashSet<>();
		kesikomiSet.add(new KeshikomiBuilder().build());
		// expect
		new Expectations() {
			{
				keshikomiQuery.getOfNyukin(nyukinId);
				result = kesikomiSet;
			}
		};
		// do
		boolean actual = service.hasKeshikomi(nyukinId);
		// verify
		new Verifications() {
			{
				keshikomiQuery.getOfNyukin(nyukinId);
				times = 1;
			}
		};
		// check
		assertTrue(actual);
	}

	@Test
	public void test_getDomain_01() throws Exception {
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
				nyukinCrud.getDomain(nyukinId);
				result = nyukin;
				keshikomiQuery.getOfNyukin(nyukinId);
				result = keshikomiSet;
			}
		};
		// do
		NyukinKeshikomi actual = service.getDomain(nyukinId);
		// verify
		new Verifications() {
			{
				nyukinCrud.getDomain(nyukinId);
				times = 1;
				keshikomiQuery.getOfNyukin(nyukinId);
				times = 1;
			}
		};
		// check
		assertEquals(nyukin, actual.getNyukin());
		assertEquals(1, actual.getKeshikomiSet().size());
		assertTrue(actual.getKeshikomiSet().contains(keshikomi));
	}
}
