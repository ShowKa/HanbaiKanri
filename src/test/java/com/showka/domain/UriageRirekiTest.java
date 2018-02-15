package com.showka.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.builder.UriageBuilder;
import com.showka.domain.builder.UriageMeisaiBuilder;
import com.showka.domain.builder.UriageRirekiBuilder;
import com.showka.kubun.HanbaiKubun;
import com.showka.system.EmptyProxy;
import com.showka.system.exception.SystemException;
import com.showka.value.TaxRate;
import com.showka.value.TheDate;

public class UriageRirekiTest extends SimpleTestCase {

	/** 売上明細01. */
	private static final UriageMeisai uriageMeisai01;
	static {
		UriageMeisaiBuilder b = new UriageMeisaiBuilder();
		Shohin shohin01 = EmptyProxy.domain(Shohin.class);
		b.withMeisaiNumber(1)
				.withShohinDomain(shohin01)
				.withHanbaiNumber(5)
				.withHanbaiTanka(BigDecimal.valueOf(1000))
				.withRecordId("KK01-00001-1");
		uriageMeisai01 = b.build();
	}

	/** 売上01. */
	private static final Uriage uriageRireki01;
	static {
		UriageBuilder b = new UriageBuilder();
		ArrayList<UriageMeisai> meisai = new ArrayList<UriageMeisai>();
		meisai.add(uriageMeisai01);
		uriageRireki01 = b.withKokyaku(EmptyProxy.domain(Kokyaku.class))
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
	private static final Uriage uriageRireki02;
	static {
		UriageBuilder b = new UriageBuilder();
		ArrayList<UriageMeisai> meisai = new ArrayList<UriageMeisai>();
		uriageRireki02 = b.withKokyaku(EmptyProxy.domain(Kokyaku.class))
				.withDenpyoNumber("00001")
				.withUriageDate(new TheDate(2017, 8, 20))
				.withKeijoDate(new TheDate(2017, 8, 21))
				.withHanbaiKubun(HanbaiKubun.現金)
				.withShohizeiritsu(new TaxRate(0.08))
				.withUriageMeisai(meisai)
				.withRecordId("r-KK01-00001")
				.build();
	}

	@Test
	public void test01_getNewest() throws Exception {
		// build
		UriageRirekiBuilder b = new UriageRirekiBuilder();
		List<Uriage> uriageRireki = new ArrayList<Uriage>();
		uriageRireki.add(uriageRireki01);
		uriageRireki.add(uriageRireki02);
		b.withList(uriageRireki);
		UriageRireki uriageRirekiList02 = b.build();
		// test
		Uriage actual = uriageRirekiList02.getNewest();
		assertEquals(new TheDate(2017, 8, 21), actual.getKeijoDate());
	}

	@Test(expected = SystemException.class)
	public void test02_validate() throws Exception {
		// build
		UriageRirekiBuilder b = new UriageRirekiBuilder();
		List<Uriage> uriageRireki = new ArrayList<Uriage>();
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
		UriageRirekiBuilder b = new UriageRirekiBuilder();
		List<Uriage> uriageRireki = new ArrayList<Uriage>();
		uriageRireki.add(uriageRireki01);
		uriageRireki.add(uriageRireki02);
		b.withList(uriageRireki);
		UriageRireki uriageRirekiList02 = b.build();
		// test
		Optional<Uriage> actual = uriageRirekiList02.getTeiseiUriage(new TheDate(2017, 8, 21));
		assertEquals(new TheDate(2017, 8, 21), actual.get().getKeijoDate());
		assertEquals(-5, actual.get().getUriageMeisai().get(0).getHanbaiNumber().intValue());
	}

	@Test
	public void test04_getAllWithTeiseiDenpyo() throws Exception {
		// build
		UriageRirekiBuilder b = new UriageRirekiBuilder();
		List<Uriage> uriageRireki = new ArrayList<Uriage>();
		uriageRireki.add(uriageRireki01);
		uriageRireki.add(uriageRireki02);
		b.withList(uriageRireki);
		UriageRireki uriageRirekiList = b.build();
		List<Uriage> actual = uriageRirekiList.getAllWithTeiseiDenpyo();

		assertEquals(new TheDate(2017, 8, 21), actual.get(0).getKeijoDate());
		assertEquals(0, actual.get(0).getUriageGokeiKakaku().getZeinukiKakaku().intValue());
		assertEquals(new TheDate(2017, 8, 21), actual.get(1).getKeijoDate());
		assertEquals(-5000, actual.get(1).getUriageGokeiKakaku().getZeinukiKakaku().intValue());
		assertEquals(new TheDate(2017, 8, 20), actual.get(2).getKeijoDate());
		assertEquals(5000, actual.get(2).getUriageGokeiKakaku().getZeinukiKakaku().intValue());
	}

}
