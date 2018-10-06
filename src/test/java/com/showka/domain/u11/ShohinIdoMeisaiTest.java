package com.showka.domain.u11;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.builder.ShohinIdoMeisaiBuilder;
import com.showka.domain.u11.ShohinIdoMeisai;

public class ShohinIdoMeisaiTest extends SimpleTestCase {

	private static final ShohinIdoMeisai ido1;
	static {
		ShohinIdoMeisaiBuilder b = new ShohinIdoMeisaiBuilder();
		b.withMeisaiNumber(1);
		ido1 = b.build();
	}

	private static final ShohinIdoMeisai ido2;
	static {
		ShohinIdoMeisaiBuilder b = new ShohinIdoMeisaiBuilder();
		b.withMeisaiNumber(2);
		ido2 = b.build();
	}

	/**
	 * compare meisai number.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test01_compareTo() throws Exception {
		int actual = ido1.compareTo(ido2);
		assertEquals(-1, actual);
	}
}
