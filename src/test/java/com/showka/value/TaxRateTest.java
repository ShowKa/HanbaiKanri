package com.showka.value;

import org.junit.Test;

import com.showka.common.SimpleTestCase;

public class TaxRateTest extends SimpleTestCase {

	@Test
	public void test01_ToPercentage() throws Exception {
		TaxRate rate = new TaxRate(0.08);
		String actual = rate.toPercentage();
		assertEquals("8%", actual);
	}

	@Test
	public void test02_ToPercentage() throws Exception {
		TaxRate rate = new TaxRate(0.08);
		String actual = rate.toPercentage(2);
		assertEquals("8.00%", actual);
	}
}
