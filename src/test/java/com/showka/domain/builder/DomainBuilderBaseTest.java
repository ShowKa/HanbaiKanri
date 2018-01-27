package com.showka.domain.builder;

import org.junit.Test;

import com.showka.domain.UriageDomain;
import com.showka.value.TheDate;

import junit.framework.TestCase;

public class DomainBuilderBaseTest extends TestCase {

	@Test
	public void test01_Apply() throws Exception {

		UriageDomainBuilder ub1 = new UriageDomainBuilder();
		ub1.withDenpyoNumber("00001");
		ub1.withKeijoDate(new TheDate(2000, 1, 1));
		UriageDomain u1 = ub1.build();

		UriageDomainBuilder ub2 = new UriageDomainBuilder();
		ub2.withDenpyoNumber("00002");
		UriageDomain u2 = ub2.apply(u1);

		assertEquals("00002", u2.getDenpyoNumber());
		assertEquals(new TheDate(2000, 1, 1), u2.getKeijoDate());
	}

}
