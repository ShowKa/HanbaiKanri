package com.showka.domain;

import java.util.ArrayList;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.builder.UriageDomainBuilder;
import com.showka.domain.builder.UriageMeisaiDomainBuilder;
import com.showka.domain.builder.UriageRirekiDomainBuilder;
import com.showka.domain.builder.UriageRirekiMeisaiDomainBuilder;
import com.showka.kubun.HanbaiKubun;
import com.showka.system.EmptyProxy;
import com.showka.value.TaxRate;
import com.showka.value.TheDate;

public class UriageRirekiDomainTest extends SimpleTestCase {

	/** 売上履歴（上書きされる）. */
	private static final UriageRirekiDomain uriage01;
	static {
		UriageRirekiDomainBuilder b = new UriageRirekiDomainBuilder();
		ArrayList<UriageRirekiMeisaiDomain> meisai = new ArrayList<UriageRirekiMeisaiDomain>();
		UriageRirekiMeisaiDomainBuilder mb = new UriageRirekiMeisaiDomainBuilder();
		mb.withMeisaiNumber(1);
		meisai.add(mb.build());
		mb.withMeisaiNumber(2);
		mb.withRecordId("r2");
		meisai.add(mb.build());
		uriage01 = b.withUriageId("r-KK01-00001")
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

	/** 売上（上書きする）. */
	private static final UriageDomain uriage02;
	static {
		UriageDomainBuilder b = new UriageDomainBuilder();
		ArrayList<UriageMeisaiDomain> meisai = new ArrayList<UriageMeisaiDomain>();
		UriageMeisaiDomainBuilder mb = new UriageMeisaiDomainBuilder();
		mb.withMeisaiNumber(2);
		mb.withRecordId("not-r2");
		meisai.add(mb.build());
		mb.withMeisaiNumber(3);
		mb.withRecordId("r3");
		meisai.add(mb.build());
		uriage02 = b.withKokyaku(EmptyProxy.domain(KokyakuDomain.class))
				.withDenpyoNumber("00001")
				.withUriageDate(new TheDate(2017, 8, 21))
				.withKeijoDate(new TheDate(2017, 8, 20))
				.withHanbaiKubun(HanbaiKubun.掛売)
				.withShohizeiritsu(new TaxRate(0.09))
				.withUriageMeisai(meisai)
				.withRecordId("r-KK01-00001-20170820")
				.build();
	}

	@Test
	public void testGetOverriddenBy() throws Exception {
		UriageRirekiDomain actual = uriage01.getOverriddenBy(uriage02);
		assertEquals(new TheDate(2017, 8, 21), actual.getUriageDate());
		assertEquals(2, actual.getUriageMeisai().size());
		assertEquals("r2", actual.getUriageRirekiMeisai().get(0).getRecordId());
		assertFalse("r3".equals(actual.getUriageRirekiMeisai().get(1).getRecordId()));
	}

}
