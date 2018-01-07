package com.showka.domain;

import java.math.BigDecimal;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.builder.UriageMeisaiDomainBuilder;
import com.showka.domain.builder.UriageRirekiMeisaiDomainBuilder;
import com.showka.system.EmptyProxy;

public class UriageRirekiMeisaiDomainTest extends SimpleTestCase {

	private static final UriageRirekiMeisaiDomain meisai01;
	static {
		// 商品ドメインダミー
		ShohinDomain shohinDomain = EmptyProxy.domain(ShohinDomain.class);

		// 売上明細主キー
		String uriageId = "r-KK01-00001-20170820";
		Integer meisaiNumber = 1;
		String recordID = uriageId + "-" + meisaiNumber;

		// 売上明細ドメイン
		UriageRirekiMeisaiDomainBuilder b = new UriageRirekiMeisaiDomainBuilder();
		b.withHanbaiNumber(10);
		b.withHanbaiTanka(BigDecimal.valueOf(200));
		b.withMeisaiNumber(meisaiNumber);
		b.withRecordId(recordID);
		b.withShohinDomain(shohinDomain);
		b.withUriageId(uriageId);
		meisai01 = b.build();
	}

	private static final UriageMeisaiDomain meisai02;
	static {
		// 商品ドメインダミー
		ShohinDomain shohinDomain = EmptyProxy.domain(ShohinDomain.class);

		// 売上明細主キー
		String uriageId = "KK01-TEST001";
		Integer meisaiNumber = 1;
		String recordID = uriageId + "-" + meisaiNumber;

		// 売上明細ドメイン
		UriageMeisaiDomainBuilder b = new UriageMeisaiDomainBuilder();
		b.withHanbaiNumber(11);
		b.withHanbaiTanka(BigDecimal.valueOf(201));
		b.withMeisaiNumber(meisaiNumber);
		b.withRecordId(recordID);
		b.withShohinDomain(shohinDomain);
		b.withUriageId(uriageId);
		b.withVersion(0);
		meisai02 = b.build();
	}

	@Test
	public void test01_getOverriddenBy() throws Exception {
		UriageRirekiMeisaiDomain actual = meisai01.getOverriddenBy(meisai02);
		assertEquals(new Integer(11), actual.getHanbaiNumber());
		assertEquals(BigDecimal.valueOf(201), actual.getHanbaiTanka());
		assertEquals("r-KK01-00001-20170820", actual.getUriageId());
		assertEquals("r-KK01-00001-20170820-1", actual.getRecordId());
	}

}
