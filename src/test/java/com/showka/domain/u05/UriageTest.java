package com.showka.domain.u05;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.builder.UriageBuilder;
import com.showka.value.Kakaku;
import com.showka.value.TaxRate;

public class UriageTest extends SimpleTestCase {

	@Test
	public void test01_getUriageGokeiKakaku() {
		// uriage meisai
		List<UriageMeisai> m = new ArrayList<UriageMeisai>();
		UriageMeisai m1 = new UriageMeisai(null, null, 1, new BigDecimal(100));
		UriageMeisai m2 = new UriageMeisai(null, null, 2, new BigDecimal(200));
		m.add(m1);
		m.add(m2);

		// build domain
		UriageBuilder b = new UriageBuilder();
		TaxRate zei = new TaxRate(0.08);
		b.withShohizeiritsu(zei);
		b.withUriageMeisai(m);
		Uriage d = b.build();

		// do
		Kakaku actual = d.getUriageGokeiKakaku();

		// check
		assertEquals(new Kakaku(500, 0.08), actual);
	}

}
