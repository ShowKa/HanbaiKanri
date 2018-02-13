package com.showka.domain;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.builder.BushoBuilder;
import com.showka.domain.builder.ShohinIdoBuilder;
import com.showka.value.TheDate;
import com.showka.value.TheTimestamp;

public class ShohinIdoTest extends SimpleTestCase {

	/** 部署01. */
	public static final Busho busho01;
	static {
		BushoBuilder b = new BushoBuilder();
		busho01 = b.withCode("BS01").build();
	}

	/** 部署02. */
	public static final Busho busho02;
	static {
		BushoBuilder b = new BushoBuilder();
		busho02 = b.withCode("BS02").build();
	}

	/**
	 * same.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test01_compareTo() throws Exception {

		TheTimestamp stamp = new TheTimestamp();

		// 1
		ShohinIdoBuilder b1 = new ShohinIdoBuilder();
		b1.withBusho(busho01);
		b1.withDate(new TheDate(2017, 1, 1));
		b1.withTimestamp(stamp);

		ShohinIdo ido1 = b1.build();

		// 2
		ShohinIdoBuilder b2 = new ShohinIdoBuilder();
		b2.withBusho(busho01);
		b2.withDate(new TheDate(2017, 1, 1));
		b2.withTimestamp(stamp);
		ShohinIdo ido2 = b2.build();

		// do
		int acutual = ido1.compareTo(ido2);
		assertEquals(0, acutual);
	}

	/**
	 * compare Time Stamp
	 * 
	 * @throws Exception
	 */
	@Test
	public void test02_compareTo() throws Exception {

		// 1
		ShohinIdoBuilder b1 = new ShohinIdoBuilder();
		b1.withBusho(busho01);
		b1.withDate(new TheDate(2017, 1, 1));
		b1.withTimestamp(new TheTimestamp(2017, 1, 1, 0, 0, 0, 0));

		ShohinIdo ido1 = b1.build();

		// 2
		ShohinIdoBuilder b2 = new ShohinIdoBuilder();
		b2.withBusho(busho01);
		b2.withDate(new TheDate(2017, 1, 1));
		b2.withTimestamp(new TheTimestamp(2017, 1, 1, 0, 0, 0, 1));
		ShohinIdo ido2 = b2.build();

		// do
		int acutual = ido1.compareTo(ido2);
		assertEquals(-1, acutual);
	}

	/**
	 * compare date
	 * 
	 * @throws Exception
	 */
	@Test
	public void test03_compareTo() throws Exception {

		TheTimestamp stamp = new TheTimestamp();

		// 1
		ShohinIdoBuilder b1 = new ShohinIdoBuilder();
		b1.withBusho(busho01);
		b1.withDate(new TheDate(2017, 1, 1));
		b1.withTimestamp(stamp);

		ShohinIdo ido1 = b1.build();

		// 2
		ShohinIdoBuilder b2 = new ShohinIdoBuilder();
		b2.withBusho(busho01);
		b2.withDate(new TheDate(2017, 1, 2));
		b2.withTimestamp(stamp);
		ShohinIdo ido2 = b2.build();

		// do
		int acutual = ido1.compareTo(ido2);
		assertEquals(-1, acutual);
	}

	/**
	 * compare busho code
	 * 
	 * @throws Exception
	 */
	@Test
	public void test04_compareTo() throws Exception {

		TheTimestamp stamp = new TheTimestamp();

		// 1
		ShohinIdoBuilder b1 = new ShohinIdoBuilder();
		b1.withBusho(busho01);
		b1.withDate(new TheDate(2017, 1, 1));
		b1.withTimestamp(stamp);

		ShohinIdo ido1 = b1.build();

		// 2
		ShohinIdoBuilder b2 = new ShohinIdoBuilder();
		b2.withBusho(busho02);
		b2.withDate(new TheDate(2017, 1, 1));
		b2.withTimestamp(stamp);
		ShohinIdo ido2 = b2.build();

		// do
		int acutual = ido1.compareTo(ido2);
		assertEquals(-1, acutual);
	}
}
