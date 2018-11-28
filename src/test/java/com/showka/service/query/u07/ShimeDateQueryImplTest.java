package com.showka.service.query.u07;

import java.util.Set;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.builder.BushoBuilder;
import com.showka.domain.z00.Busho;
import com.showka.service.query.u07.ShimeDateQueryImpl;
import com.showka.service.query.z00.i.BushoDateQuery;
import com.showka.value.EigyoDate;
import com.showka.value.ShimeDate;
import com.showka.value.TheDate;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

public class ShimeDateQueryImplTest extends SimpleTestCase {

	@Tested
	private ShimeDateQueryImpl service;

	@Injectable
	private BushoDateQuery dateService;

	// input=30 => 対象
	@Test
	public void test_GetShimeDate_01() throws Exception {
		// input
		Busho busho = new BushoBuilder().build();
		EigyoDate eigyoDate = new EigyoDate(2018, 8, 30);
		// expect
		new Expectations() {
			{
				// 無限ループ防止
				dateService.isEigyoDate((Busho) any, (TheDate) any);
				result = true;
			}
		};
		// do
		Set<ShimeDate> actual = service.get(busho, eigyoDate);
		// check
		assertTrue(actual.contains(new ShimeDate(30)));
	}

	// input=31 => 対象外
	@Test
	public void test_GetShimeDate_02() throws Exception {
		// input
		Busho busho = new BushoBuilder().build();
		EigyoDate eigyoDate = new EigyoDate(2018, 8, 31);
		// expect
		new Expectations() {
			{
				// 無限ループ防止
				dateService.isEigyoDate((Busho) any, (TheDate) any);
				result = true;
			}
		};
		// do
		Set<ShimeDate> actual = service.get(busho, eigyoDate);
		// check
		assertEquals(0, actual.size());
	}

	// input=30, 翌月2日=営業日 => 対象30, 1
	@Test
	public void test_GetShimeDate_03() throws Exception {
		// input
		Busho busho = new BushoBuilder().build();
		EigyoDate eigyoDate = new EigyoDate(2018, 8, 30);
		EigyoDate next1 = new EigyoDate(2018, 8, 31);
		EigyoDate next2 = new EigyoDate(2018, 9, 1);
		EigyoDate next3 = new EigyoDate(2018, 9, 2);
		// expect
		new Expectations() {
			{
				dateService.isEigyoDate(busho, next1);
				result = false;
				dateService.isEigyoDate(busho, next2);
				result = false;
				dateService.isEigyoDate(busho, next3);
				result = true;
			}
		};
		// do
		Set<ShimeDate> actual = service.get(busho, eigyoDate);
		// verify
		new Verifications() {
			{
				dateService.isEigyoDate(busho, next1);
				times = 1;
				dateService.isEigyoDate(busho, next2);
				times = 1;
				dateService.isEigyoDate(busho, next3);
				times = 1;
			}
		};
		// check
		assertEquals(2, actual.size());
		assertTrue(actual.contains(new ShimeDate(30)));
		assertTrue(actual.contains(new ShimeDate(1)));
	}

	// input=2/28 => 対象28,29,30
	@Test
	public void test_GetShimeDate_04() throws Exception {
		// input
		Busho busho = new BushoBuilder().build();
		EigyoDate eigyoDate = new EigyoDate(2018, 2, 28);
		// expect
		new Expectations() {
			{
				// 無限ループ防止
				dateService.isEigyoDate((Busho) any, (TheDate) any);
				result = true;
			}
		};
		// do
		Set<ShimeDate> actual = service.get(busho, eigyoDate);
		// check
		assertEquals(3, actual.size());
		assertTrue(actual.contains(new ShimeDate(28)));
		assertTrue(actual.contains(new ShimeDate(29)));
		assertTrue(actual.contains(new ShimeDate(30)));
	}

}
