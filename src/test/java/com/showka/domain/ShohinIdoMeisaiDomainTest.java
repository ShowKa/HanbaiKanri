package com.showka.domain;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.builder.ShohinIdoMeisaiDomainBuilder;

public class ShohinIdoMeisaiDomainTest extends SimpleTestCase {

	private static final ShohinIdoMeisaiDomain ido1;
	static {
		ShohinIdoMeisaiDomainBuilder b = new ShohinIdoMeisaiDomainBuilder();
		b.withMeisaiNumber(1);
		ido1 = b.build();
	}

	private static final ShohinIdoMeisaiDomain ido2;
	static {
		ShohinIdoMeisaiDomainBuilder b = new ShohinIdoMeisaiDomainBuilder();
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
