package com.showka.value;

import org.junit.Test;

import com.showka.common.SimpleTestCase;

public class TheTimestampTest extends SimpleTestCase {

	@Test
	public void test01_ToString() throws Exception {
		assertEquals("2017/01/02 11:22:33.999999", new TheTimestamp(2017, 1, 2, 11, 22, 33, 999999999).toString());
	}

	@Test
	public void test02_FormatToSecond() throws Exception {
		assertEquals("2017/01/02", new TheTimestamp(2017, 1, 2, 11, 22, 33, 0).toString("yyyy/MM/dd"));
	}

	@Test
	public void test03_formatToSecond() throws Exception {
		assertEquals("2017/01/02 11:22:33", new TheTimestamp(2017, 1, 2, 11, 22, 33, 0).formatToSecond());
	}

}
