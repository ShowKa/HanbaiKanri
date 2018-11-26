package com.showka.service.query.u08;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.builder.KeshikomiBuilder;
import com.showka.domain.u08.Keshikomi;
import com.showka.service.query.u08.i.KeshikomiQuery;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

public class NyukinKeshikomiQueryImplTest extends SimpleTestCase {

	@Tested
	private NyukinKeshikomiQueryImpl service;

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
}
