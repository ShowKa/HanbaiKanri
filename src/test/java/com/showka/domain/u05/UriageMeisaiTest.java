package com.showka.domain.u05;

import java.math.BigDecimal;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.builder.UriageMeisaiBuilder;
import com.showka.domain.u05.UriageMeisai;

public class UriageMeisaiTest extends SimpleTestCase {

	@Test
	public void test01_getMeisaiGokeiKakaku() {

		// domain
		UriageMeisaiBuilder b = new UriageMeisaiBuilder();
		b.withHanbaiNumber(3);
		b.withHanbaiTanka(new BigDecimal(500));
		UriageMeisai d = b.build();

		// do
		BigDecimal actual = d.getMeisaiGokeiKakaku();

		assertEquals(1500, actual.intValue());

	}

}
