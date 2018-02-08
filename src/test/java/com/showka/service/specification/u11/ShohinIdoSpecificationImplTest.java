package com.showka.service.specification.u11;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.KokyakuDomain;
import com.showka.domain.ShohinDomain;
import com.showka.domain.ShohinIdoDomain;
import com.showka.domain.ShohinIdoMeisaiDomain;
import com.showka.domain.UriageDomain;
import com.showka.domain.UriageMeisaiDomain;
import com.showka.domain.builder.UriageDomainBuilder;
import com.showka.domain.builder.UriageMeisaiDomainBuilder;
import com.showka.entity.TUriagePK;
import com.showka.kubun.HanbaiKubun;
import com.showka.kubun.ShohinIdoKubun;
import com.showka.service.crud.u05.i.UriageCrudService;
import com.showka.system.EmptyProxy;
import com.showka.value.TaxRate;
import com.showka.value.TheDate;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

public class ShohinIdoSpecificationImplTest extends SimpleTestCase {

	@Tested
	private ShohinIdoSpecificationImpl shohinIdoSpecificationImpl;

	@Injectable
	private UriageCrudService uriageCrudService;

	/** 売上明細01. */
	public static final UriageMeisaiDomain uriageMeisai01;
	static {
		ShohinDomain shohin = EmptyProxy.domain(ShohinDomain.class);
		UriageMeisaiDomainBuilder b = new UriageMeisaiDomainBuilder();
		b.withUriageId("KK01-00001")
				.withMeisaiNumber(1)
				.withShohinDomain(shohin)
				.withHanbaiNumber(5)
				.withHanbaiTanka(BigDecimal.valueOf(1000))
				.withRecordId("KK01-00001-1");
		uriageMeisai01 = b.build();
	}

	/** 売上明細02. */
	public static final UriageMeisaiDomain uriageMeisai02;
	static {
		ShohinDomain shohin = EmptyProxy.domain(ShohinDomain.class);
		UriageMeisaiDomainBuilder b = new UriageMeisaiDomainBuilder();
		b.withMeisaiNumber(2)
				.withShohinDomain(shohin)
				.withHanbaiNumber(6)
				.withHanbaiTanka(BigDecimal.valueOf(1001))
				.withRecordId("KK01-00001-2");
		uriageMeisai02 = b.build();
	}

	/** 売上01. */
	public static final UriageDomain uriage01;
	static {
		UriageDomainBuilder b = new UriageDomainBuilder();
		ArrayList<UriageMeisaiDomain> meisai = new ArrayList<UriageMeisaiDomain>();
		meisai.add(uriageMeisai01);
		meisai.add(uriageMeisai02);
		uriage01 = b.withKokyaku(EmptyProxy.domain(KokyakuDomain.class))
				.withDenpyoNumber("00001")
				.withUriageDate(new TheDate(2017, 8, 20))
				.withKeijoDate(new TheDate(2017, 8, 20))
				.withHanbaiKubun(HanbaiKubun.現金)
				.withShohizeiritsu(new TaxRate(0.08))
				.withUriageMeisai(meisai)
				.withRecordId("KK01-00001")
				.build();
	}

	@Test
	public void test01_BuildShohinIdo() throws Exception {
		// expect
		new Expectations() {
			{
				uriageCrudService.exsists((TUriagePK) any);
				result = false;
			}
		};
		// do
		List<ShohinIdoDomain> actual = shohinIdoSpecificationImpl.buildShohinIdo(uriage01);
		// verify
		new Verifications() {
			{
				uriageCrudService.exsists((TUriagePK) any);
				times = 1;
			}
		};
		// check
		assertEquals(1, actual.size());
		ShohinIdoDomain ido = actual.get(0);
		assertEquals(ShohinIdoKubun.売上, ido.getKubun());
		// 明細
		List<ShohinIdoMeisaiDomain> meisai = ido.getMeisai();
		assertEquals(2, meisai.size());
		meisai.stream().filter(p -> {
			return p.getMeisaiNumber().intValue() == 1;
		}).forEach(meisai1 -> {
			assertEquals(5, meisai1.getNumber().intValue());
		});
	}

	@Test
	public void test02_BuildShohinIdo() throws Exception {
		// expect
		new Expectations() {
			{
				uriageCrudService.exsists((TUriagePK) any);
				result = true;
				uriageCrudService.getDomain((TUriagePK) any);
				result = uriage01;
			}
		};
		// do
		List<ShohinIdoDomain> actual = shohinIdoSpecificationImpl.buildShohinIdo(uriage01);
		// verify
		new Verifications() {
			{
				uriageCrudService.exsists((TUriagePK) any);
				times = 1;
				uriageCrudService.getDomain((TUriagePK) any);
				times = 1;
			}
		};
		// check
		assertEquals(2, actual.size());
		ShohinIdoDomain ido = actual.get(1);
		assertEquals(ShohinIdoKubun.売上訂正, ido.getKubun());
	}

}
