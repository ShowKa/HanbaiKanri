package com.showka.domain;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.builder.UriageDomainBuilder;
import com.showka.domain.builder.UriageRirekiListDomainBuilder;
import com.showka.kubun.HanbaiKubun;
import com.showka.system.EmptyProxy;
import com.showka.value.TaxRate;
import com.showka.value.TheDate;

public class UriageRirekiListDomainTest extends SimpleTestCase {

	/** 売上01. */
	private static final UriageDomain uriageRireki01;
	static {
		UriageDomainBuilder b = new UriageDomainBuilder();
		ArrayList<UriageMeisaiDomain> meisai = new ArrayList<UriageMeisaiDomain>();
		uriageRireki01 = b.withKokyaku(EmptyProxy.domain(KokyakuDomain.class))
				.withDenpyoNumber("00001")
				.withUriageDate(new TheDate(2017, 8, 20))
				.withKeijoDate(new TheDate(2017, 8, 20))
				.withHanbaiKubun(HanbaiKubun.現金)
				.withShohizeiritsu(new TaxRate(0.08))
				.withUriageMeisai(meisai)
				.withRecordId("r-KK01-00001")
				.build();
	}

	/** 売上02. */
	private static final UriageDomain uriageRireki02;
	static {
		UriageDomainBuilder b = new UriageDomainBuilder();
		ArrayList<UriageMeisaiDomain> meisai = new ArrayList<UriageMeisaiDomain>();
		uriageRireki02 = b.withKokyaku(EmptyProxy.domain(KokyakuDomain.class))
				.withDenpyoNumber("00001")
				.withUriageDate(new TheDate(2017, 8, 20))
				.withKeijoDate(new TheDate(2017, 8, 21))
				.withHanbaiKubun(HanbaiKubun.現金)
				.withShohizeiritsu(new TaxRate(0.09))
				.withUriageMeisai(meisai)
				.withRecordId("r-KK01-00001")
				.build();
	}

	@Test
	public void test01_getNewest() throws Exception {
		// build
		UriageRirekiListDomainBuilder b = new UriageRirekiListDomainBuilder();
		List<UriageDomain> uriageRireki = new ArrayList<UriageDomain>();
		uriageRireki.add(uriageRireki01);
		uriageRireki.add(uriageRireki02);
		b.withList(uriageRireki);
		UriageRirekiListDomain uriageRirekiList02 = b.build();
		// test
		UriageDomain actual = uriageRirekiList02.getNewest();
		assertEquals(new TheDate(2017, 8, 21), actual.getKeijoDate());
	}

}
