package com.showka.service.crud.u05;

import java.util.ArrayList;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.showka.common.ServiceCrudTestCase;
import com.showka.domain.UriageDomain;
import com.showka.domain.UriageMeisaiDomain;
import com.showka.domain.builder.UriageDomainBuilder;
import com.showka.domain.mock.Domains;
import com.showka.entity.TUriagePK;
import com.showka.kubun.HanbaiKubun;
import com.showka.value.TaxRate;
import com.showka.value.TheDate;

public class UriageCrudServiceImplTest extends ServiceCrudTestCase {

	@Autowired
	private UriageCrudServiceImpl service;

	/** 売上01. */
	private static final UriageDomain uriage01;
	static {
		UriageDomainBuilder b = new UriageDomainBuilder();
		ArrayList<UriageMeisaiDomain> meisai = new ArrayList<UriageMeisaiDomain>();
		meisai.add(Domains.uriageMeisai01);
		meisai.add(Domains.uriageMeisai02);
		uriage01 = b.withKokyaku(Domains.kokyaku01)
				.withDenpyoNumber("00001")
				.withUriageDate(new TheDate(2017, 8, 20))
				.withHanbaiKubun(HanbaiKubun.現金)
				.withShohizeiritsu(new TaxRate(0.08))
				.withUriageMeisai(meisai)
				.withRecordId("KK01-00001")
				.build();
	}

	/** 売上0x */
	private static final UriageDomain newUriage;
	static {
		UriageDomainBuilder b = new UriageDomainBuilder();
		ArrayList<UriageMeisaiDomain> meisai = new ArrayList<UriageMeisaiDomain>();
		newUriage = b.withKokyaku(Domains.kokyaku01)
				.withDenpyoNumber("0000X")
				.withUriageDate(new TheDate(2017, 8, 20))
				.withHanbaiKubun(HanbaiKubun.現金)
				.withShohizeiritsu(new TaxRate(0.08))
				.withUriageMeisai(meisai)
				.withRecordId("KK01-0000X")
				.build();
	}

	@Test
	@Transactional
	public void test01_SaveAndGetDomain() throws Exception {

		// save
		service.save(uriage01);

		// get domain
		TUriagePK pk = new TUriagePK();
		pk.setKokyakuId(uriage01.getKokyaku().getRecordId());
		pk.setDenpyoNumber("00001");
		UriageDomain actual = service.getDomain(pk);

		// check
		assertEquals("KK01-00001", actual.getRecordId());
		assertEquals(2, actual.getUriageMeisai().size());
	}

	@Test
	@Transactional
	public void test02_SaveAndGetDomain() throws Exception {

		// save
		service.save(newUriage);

		// get domain
		TUriagePK pk = new TUriagePK();
		pk.setKokyakuId(newUriage.getKokyaku().getRecordId());
		pk.setDenpyoNumber("0000X");
		UriageDomain actual = service.getDomain(pk);

		// check
		assertEquals("KK01-0000X", actual.getRecordId());
	}
}
