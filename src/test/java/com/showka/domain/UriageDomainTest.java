package com.showka.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.builder.UriageDomainBuilder;
import com.showka.value.Kakaku;
import com.showka.value.TaxRate;

public class UriageDomainTest extends SimpleTestCase {

	@Test
	public void test01_getUriageGokeiKakaku() {
		// uriage meisai
		List<UriageMeisaiDomain> m = new ArrayList<UriageMeisaiDomain>();
		UriageMeisaiDomain m1 = new UriageMeisaiDomain(null, null, null, 1, new BigDecimal(100));
		UriageMeisaiDomain m2 = new UriageMeisaiDomain(null, null, null, 2, new BigDecimal(200));
		m.add(m1);
		m.add(m2);

		// build domain
		UriageDomainBuilder b = new UriageDomainBuilder();
		TaxRate zei = new TaxRate(0.08);
		b.withShohizeiritsu(zei);
		b.withUriageMeisai(m);
		UriageDomain d = b.build();

		// do
		Kakaku actual = d.getUriageGokeiKakaku();

		// check
		assertEquals(new Kakaku(500, 0.08), actual);
	}

}
