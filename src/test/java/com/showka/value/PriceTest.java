package com.showka.value;

import java.math.BigDecimal;

import org.junit.Test;

import com.showka.common.SimpleTestCase;

public class PriceTest extends SimpleTestCase {

	@Test
	public void test01_GetKingakuFormatted() throws Exception {
		Price p = new Price(123456789);
		String actual = p.getFormatted();
		assertEquals("¥123,456,789", actual);
	}

	@Test
	public void test02_GetKingakuFormatted() throws Exception {
		Price p = new Price(new BigDecimal(12345.67));
		String actual = p.getFormatted();
		assertEquals("¥12,345.67", actual);
	}
}
