package com.showka.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.builder.UriageDomainBuilder;
import com.showka.domain.builder.UriageMeisaiDomainBuilder;
import com.showka.domain.builder.UriageRirekiDomainBuilder;
import com.showka.kubun.HanbaiKubun;
import com.showka.system.EmptyProxy;
import com.showka.system.exception.SystemException;
import com.showka.value.TaxRate;
import com.showka.value.TheDate;

public class UriageRirekiDomainTest extends SimpleTestCase {

	/** 売上明細01. */
	private static final UriageMeisaiDomain uriageMeisai01;
	static {
		UriageMeisaiDomainBuilder b = new UriageMeisaiDomainBuilder();
		ShohinDomain shohin01 = EmptyProxy.domain(ShohinDomain.class);
		b.withUriageId("KK01-00001")
				.withMeisaiNumber(1)
				.withShohinDomain(shohin01)
				.withHanbaiNumber(5)
				.withHanbaiTanka(BigDecimal.valueOf(1000))
				.withRecordId("KK01-00001-1");
		uriageMeisai01 = b.build();
	}

	/** 売上01. */
	private static final UriageDomain uriageRireki01;
	static {
		UriageDomainBuilder b = new UriageDomainBuilder();
		ArrayList<UriageMeisaiDomain> meisai = new ArrayList<UriageMeisaiDomain>();
		meisai.add(uriageMeisai01);
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
		UriageRirekiDomainBuilder b = new UriageRirekiDomainBuilder();
		List<UriageDomain> uriageRireki = new ArrayList<UriageDomain>();
		uriageRireki.add(uriageRireki01);
		uriageRireki.add(uriageRireki02);
		b.withList(uriageRireki);
		UriageRirekiDomain uriageRirekiList02 = b.build();
		// test
		UriageDomain actual = uriageRirekiList02.getNewest();
		assertEquals(new TheDate(2017, 8, 21), actual.getKeijoDate());
	}

	@Test(expected = SystemException.class)
	public void test02_validate() throws Exception {
		// build
		UriageRirekiDomainBuilder b = new UriageRirekiDomainBuilder();
		List<UriageDomain> uriageRireki = new ArrayList<UriageDomain>();
		uriageRireki.add(uriageRireki01);
		uriageRireki.add(uriageRireki02);
		uriageRireki.add(uriageRireki02);
		b.withList(uriageRireki);
		b.build();
		fail();
	}

	@Test
	public void test03_getTeiseiUriage() throws Exception {
		// build
		UriageRirekiDomainBuilder b = new UriageRirekiDomainBuilder();
		List<UriageDomain> uriageRireki = new ArrayList<UriageDomain>();
		uriageRireki.add(uriageRireki01);
		uriageRireki.add(uriageRireki02);
		b.withList(uriageRireki);
		UriageRirekiDomain uriageRirekiList02 = b.build();
		// test
		Optional<UriageDomain> actual = uriageRirekiList02.getTeiseiUriage(new TheDate(2017, 8, 21));
		assertEquals(new TheDate(2017, 8, 20), actual.get().getKeijoDate());
		assertEquals(-5, actual.get().getUriageMeisai().get(0).getHanbaiNumber().intValue());
	}

}
