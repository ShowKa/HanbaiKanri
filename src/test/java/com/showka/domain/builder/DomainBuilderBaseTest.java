package com.showka.domain.builder;

import org.junit.Test;

import com.showka.domain.Uriage;
import com.showka.value.TheDate;

import junit.framework.TestCase;

public class DomainBuilderBaseTest extends TestCase {

	@Test
	public void test01_Apply() throws Exception {
		UriageBuilder ub1 = new UriageBuilder();
		ub1.withDenpyoNumber("00001");
		ub1.withKeijoDate(new TheDate(2000, 1, 1));
		Uriage u1 = ub1.build();
		UriageBuilder ub2 = new UriageBuilder();
		ub2.withDenpyoNumber("00002");
		Uriage u2 = ub2.apply(u1);
		assertEquals("00002", u2.getDenpyoNumber());
		assertEquals(new TheDate(2000, 1, 1), u2.getKeijoDate());
	}

	// record is null -> empty string
	@Test
	public void test02_Apply_id() throws Exception {
		UriageBuilder ub = new UriageBuilder();
		Uriage actual = ub.build();
		assertEquals("", actual.getRecordId());
	}
}
