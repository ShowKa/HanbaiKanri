package com.showka.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.builder.UriageDomainBuilder;
import com.showka.domain.builder.UriageRirekiDomainBuilder;
import com.showka.domain.builder.UriageRirekiListDomainBuilder;
import com.showka.kubun.HanbaiKubun;
import com.showka.system.EmptyProxy;
import com.showka.value.TaxRate;
import com.showka.value.TheDate;

public class UriageRirekiListDomainTest extends SimpleTestCase {

	/** 売上01. */
	private static final UriageRirekiDomain uriageRireki01;
	static {
		UriageRirekiDomainBuilder b = new UriageRirekiDomainBuilder();
		ArrayList<UriageRirekiMeisaiDomain> meisai = new ArrayList<UriageRirekiMeisaiDomain>();
		uriageRireki01 = b.withUriageId("r-KK01-00001")
				.withKokyaku(EmptyProxy.domain(KokyakuDomain.class))
				.withDenpyoNumber("00001")
				.withUriageDate(new TheDate(2017, 8, 20))
				.withKeijoDate(new TheDate(2017, 8, 20))
				.withHanbaiKubun(HanbaiKubun.現金)
				.withShohizeiritsu(new TaxRate(0.08))
				.withUriageMeisai(meisai)
				.withRecordId("r-KK01-00001-20170820")
				.build();
	}

	/** 売上02. */
	private static final UriageRirekiDomain uriageRireki02;
	static {
		UriageRirekiDomainBuilder b = new UriageRirekiDomainBuilder();
		ArrayList<UriageRirekiMeisaiDomain> meisai = new ArrayList<UriageRirekiMeisaiDomain>();
		uriageRireki02 = b.withUriageId("r-KK01-00001")
				.withKokyaku(EmptyProxy.domain(KokyakuDomain.class))
				.withDenpyoNumber("00001")
				.withUriageDate(new TheDate(2017, 8, 20))
				.withKeijoDate(new TheDate(2017, 8, 21))
				.withHanbaiKubun(HanbaiKubun.現金)
				.withShohizeiritsu(new TaxRate(0.09))
				.withUriageMeisai(meisai)
				.withRecordId("r-KK01-00001-20170821")
				.build();
	}

	/** 売上02_更新後. */
	private static final UriageDomain uriageRireki02_updated;
	static {
		UriageDomainBuilder b = new UriageDomainBuilder();
		ArrayList<UriageMeisaiDomain> meisai = new ArrayList<UriageMeisaiDomain>();
		uriageRireki02_updated = b.withKokyaku(EmptyProxy.domain(KokyakuDomain.class))
				.withDenpyoNumber("00001")
				.withUriageDate(new TheDate(2017, 8, 20))
				.withKeijoDate(new TheDate(2017, 8, 21))
				.withHanbaiKubun(HanbaiKubun.現金)
				.withShohizeiritsu(new TaxRate(0.10))
				.withUriageMeisai(meisai)
				.withRecordId("r-KK01-00001")
				.build();
	}

	@Test
	public void test01_getNewest() throws Exception {
		// build
		UriageRirekiListDomainBuilder b = new UriageRirekiListDomainBuilder();
		Set<UriageRirekiDomain> uriageRireki = new HashSet<UriageRirekiDomain>();
		uriageRireki.add(uriageRireki01);
		uriageRireki.add(uriageRireki02);
		b.withList(uriageRireki);
		UriageRirekiListDomain uriageRirekiList02 = b.build();
		// test
		UriageRirekiDomain actual = uriageRirekiList02.getNewest();
		assertEquals(new TheDate(2017, 8, 21), actual.getKeijoDate());
	}

	@Test
	public void test01_merge() throws Exception {
		// build
		UriageRirekiListDomainBuilder b = new UriageRirekiListDomainBuilder();
		Set<UriageRirekiDomain> uriageRireki = new HashSet<UriageRirekiDomain>();
		uriageRireki.add(uriageRireki01);
		b.withList(uriageRireki);
		UriageRirekiListDomain uriageRirekiList01 = b.build();
		// before merge
		UriageRirekiDomain pre = uriageRirekiList01.getNewest();
		assertEquals(new TheDate(2017, 8, 20), pre.getKeijoDate());
		// test
		uriageRirekiList01.merge(uriageRireki02);
		UriageRirekiDomain actual = uriageRirekiList01.getNewest();
		assertEquals(new TheDate(2017, 8, 21), actual.getKeijoDate());
	}

	@Test
	public void test02_merge() throws Exception {
		// build
		UriageRirekiListDomainBuilder b = new UriageRirekiListDomainBuilder();
		Set<UriageRirekiDomain> uriageRireki = new HashSet<UriageRirekiDomain>();
		uriageRireki.add(uriageRireki01);
		uriageRireki.add(uriageRireki02);
		b.withList(uriageRireki);
		UriageRirekiListDomain uriageRirekiList02 = b.build();
		// before merge
		UriageRirekiDomain pre = uriageRirekiList02.getNewest();
		assertEquals(new TaxRate(0.09), pre.getShohizeiritsu());
		// test
		uriageRirekiList02.merge(uriageRireki02_updated);
		UriageRirekiDomain actual = uriageRirekiList02.getNewest();
		assertEquals(new TaxRate(0.10), actual.getShohizeiritsu());
	}

}
