package com.showka.service.query.z00;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.z00.Busho;
import com.showka.service.query.z00.BushoDateQueryImpl;
import com.showka.value.EigyoDate;

import mockit.Injectable;
import mockit.Tested;

public class BushoDateQueryImplTest extends SimpleTestCase {

	@Tested
	private BushoDateQueryImpl service;

	@Test
	public void test02_getNextBushoEigyoDate(@Injectable Busho busho) throws Exception {
		// input
		EigyoDate eigyoDate = new EigyoDate(2018, 10, 26);
		EigyoDate actual = service.getNext(busho, eigyoDate);
		assertEquals(new EigyoDate(2018, 10, 29), actual);
	}

	@Test
	public void testIsEigyoDate() throws Exception {
		boolean actual = service.isEigyoDate(null, new EigyoDate(2018, 10, 27));
		assertFalse(actual);
	}
}
