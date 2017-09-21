package com.showka.service.crud.u05;

import java.math.BigDecimal;
import java.util.ArrayList;

import javax.transaction.Transactional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.ServiceCrudTestCase;
import com.showka.domain.UriageDomain;
import com.showka.domain.UriageMeisaiDomain;
import com.showka.domain.builder.UriageDomainBuilder;
import com.showka.domain.builder.UriageMeisaiDomainBuilder;
import com.showka.domain.mock.Domains;
import com.showka.entity.TUriage;
import com.showka.entity.TUriagePK;
import com.showka.kubun.HanbaiKubun;
import com.showka.repository.i.TUriageRepository;
import com.showka.value.TaxRate;
import com.showka.value.TheDate;

public class UriageCrudServiceImplTest extends ServiceCrudTestCase {

	@Autowired
	private UriageCrudServiceImpl service;

	@Autowired
	private TUriageRepository repo;

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

	/** 売上明細0X. */
	public static final UriageMeisaiDomain uriageMeisai0X;
	static {
		UriageMeisaiDomainBuilder b = new UriageMeisaiDomainBuilder();
		uriageMeisai0X = b.withUriageId("KK0X-00001")
				.withMeisaiNumber(1)
				.withShohinDomain(Domains.shohin01)
				.withHanbaiNumber(5)
				.withHanbaiTanka(BigDecimal.valueOf(1000))
				.withRecordId("KK0X-00001-1")
				.build();
	}

	/** 売上0x */
	private static final UriageDomain newUriage;
	static {
		UriageDomainBuilder b = new UriageDomainBuilder();
		ArrayList<UriageMeisaiDomain> meisai = new ArrayList<UriageMeisaiDomain>();
		meisai.add(uriageMeisai0X);
		newUriage = b.withKokyaku(Domains.kokyaku01)
				.withDenpyoNumber("0000X")
				.withUriageDate(new TheDate(2017, 8, 20))
				.withHanbaiKubun(HanbaiKubun.現金)
				.withShohizeiritsu(new TaxRate(0.08))
				.withUriageMeisai(meisai)
				.withRecordId("KK01-0000X")
				.build();
	}

	/**
	 * save for update
	 * 
	 * @throws Exception
	 */
	@Test
	@Transactional
	public void test01_Save() throws Exception {

		// save
		service.save(uriage01);

		// get domain
		TUriagePK pk = new TUriagePK();
		pk.setKokyakuId(uriage01.getKokyaku().getRecordId());
		pk.setDenpyoNumber("00001");
		TUriage actual = repo.findById(pk).get();

		// check
		assertEquals("KK01-00001", actual.getRecordId());
		assertEquals(2, actual.getMeisai().size());
	}

	/**
	 * save for new insert
	 * 
	 * @throws Exception
	 */
	@Test
	@Transactional
	public void test02_Save() throws Exception {

		// save
		service.save(newUriage);

		// get domain
		TUriagePK pk = new TUriagePK();
		pk.setKokyakuId(newUriage.getKokyaku().getRecordId());
		pk.setDenpyoNumber("0000X");
		TUriage actual = repo.findById(pk).get();

		// check
		assertEquals("KK01-0000X", actual.getRecordId());
		assertEquals("KK01", actual.getKokyaku().getCode());
		assertEquals(1, actual.getMeisai().size());
	}

	/**
	 * get domain
	 * 
	 * @throws Exception
	 */
	@Test
	@Transactional
	public void test03_GetDomain() throws Exception {

		// get domain
		TUriagePK pk = new TUriagePK();
		pk.setKokyakuId("KK01");
		pk.setDenpyoNumber("00001");
		UriageDomain actual = service.getDomain(pk);

		// check
		assertEquals("KK01-00001", actual.getRecordId());
		assertEquals("KK01", actual.getKokyaku().getCode());
		assertEquals(2, actual.getUriageMeisai().size());
	}
}
