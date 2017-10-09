package com.showka.domain;

import java.math.BigDecimal;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.builder.UriageMeisaiDomainBuilder;

public class UriageMeisaiDomainTest extends SimpleTestCase {

	@Test
	public void test01_getMeisaiGokeiKakaku() {

		// domain
		UriageMeisaiDomainBuilder b = new UriageMeisaiDomainBuilder();
		b.withHanbaiNumber(3);
		b.withHanbaiTanka(new BigDecimal(500));
		UriageMeisaiDomain d = b.build();

		// do
		BigDecimal actual = d.getMeisaiGokeiKakaku();

		assertEquals(1500, actual.intValue());

	}

}
