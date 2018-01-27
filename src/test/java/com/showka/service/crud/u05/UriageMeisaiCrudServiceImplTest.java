package com.showka.service.crud.u05;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.showka.common.ServiceCrudTestCase;
import com.showka.domain.ShohinDomain;
import com.showka.domain.UriageMeisaiDomain;
import com.showka.domain.builder.ShohinDomainBuilder;
import com.showka.domain.builder.UriageMeisaiDomainBuilder;
import com.showka.entity.TUriageMeisai;
import com.showka.entity.TUriageMeisaiPK;
import com.showka.repository.i.TUriageMeisaiRepository;

public class UriageMeisaiCrudServiceImplTest extends ServiceCrudTestCase {

	@Autowired
	private UriageMeisaiCrudServiceImpl service;

	@Autowired
	private TUriageMeisaiRepository repo;

	/** 売上明細01 */
	private static final Object[] T_URIAGE_MEISAI_01 = { "r-KK01-00001", 1, "SH01", 5, 1000, "r-KK01-00001-1" };

	/** 売上明細02 */
	private static final Object[] T_URIAGE_MEISAI_02 = { "r-KK01-00001", 2, "SH02", 6, 1001, "r-KK01-00001-2" };

	@Test
	public void test01_save() {

		// data
		super.deleteAndInsert(T_URIAGE_MEISAI, T_URIAGE_MEISAI_COLUMN, T_URIAGE_MEISAI_01);

		// 商品ドメインダミー
		ShohinDomainBuilder sh = new ShohinDomainBuilder();
		sh.withRecordId("shohin_record_id");
		ShohinDomain shohinDomain = sh.build();

		// 売上明細主キー
		String uriageId = "r-KK01-00001";
		Integer meisaiNumber = 1;
		String recordID = uriageId + "-" + meisaiNumber;

		// 売上明細ドメイン
		UriageMeisaiDomainBuilder b = new UriageMeisaiDomainBuilder();
		b.withHanbaiNumber(10);
		b.withHanbaiTanka(BigDecimal.valueOf(200));
		b.withMeisaiNumber(meisaiNumber);
		b.withRecordId(recordID);
		b.withShohinDomain(shohinDomain);
		b.withUriageId(uriageId);
		b.withVersion(0);
		UriageMeisaiDomain domain = b.build();

		// do
		service.save(domain);

		// check
		TUriageMeisaiPK id = new TUriageMeisaiPK();
		id.setUriageId(uriageId);
		id.setMeisaiNumber(meisaiNumber);
		TUriageMeisai actual = repo.findById(id).get();

		assertEquals(recordID, actual.getRecordId());
	}

	@Test
	public void test02_delete() {

		// data
		super.deleteAndInsert(T_URIAGE_MEISAI, T_URIAGE_MEISAI_COLUMN, T_URIAGE_MEISAI_01);

		// set pk
		TUriageMeisaiPK pk = new TUriageMeisaiPK();
		String uriageId = "r-KK01-00001";
		Integer meisaiNumber = 1;
		pk.setUriageId(uriageId);
		pk.setMeisaiNumber(meisaiNumber);

		// set version
		Integer version = 0;

		// check exists
		TUriageMeisai before = repo.findById(pk).get();
		assertNotNull(before);

		// do
		service.delete(pk, version);

		// check deleted
		Optional<TUriageMeisai> actual = repo.findById(pk);
		assertEquals(false, actual.isPresent());
	}

	@Test
	@Transactional
	public void test03_getDomain() {

		// data
		super.deleteAndInsert(T_URIAGE_MEISAI, T_URIAGE_MEISAI_COLUMN, T_URIAGE_MEISAI_01);

		// set pk
		TUriageMeisaiPK pk = new TUriageMeisaiPK();
		String uriageId = "r-KK01-00001";
		Integer meisaiNumber = 1;
		pk.setUriageId(uriageId);
		pk.setMeisaiNumber(meisaiNumber);

		// do
		UriageMeisaiDomain d = service.getDomain(pk);

		// check
		assertEquals(uriageId, d.getUriageId());
		assertEquals(meisaiNumber, d.getMeisaiNumber());
		assertNotNull(d.getShohinDomain());
	}

	@Test
	public void test04_exists() {

		// data
		super.deleteAndInsert(T_URIAGE_MEISAI, T_URIAGE_MEISAI_COLUMN, T_URIAGE_MEISAI_01);

		// set pk
		TUriageMeisaiPK pk = new TUriageMeisaiPK();
		String uriageId = "r-KK01-00001";
		Integer meisaiNumber = 1;
		pk.setUriageId(uriageId);
		pk.setMeisaiNumber(meisaiNumber);

		// do
		boolean actual = service.exsists(pk);

		// check
		assertTrue(actual);
	}

	@Test
	public void test05_exists() {

		// set pk
		TUriageMeisaiPK pk = new TUriageMeisaiPK();
		String uriageId = "AAAAAAAAAAAA";
		Integer meisaiNumber = 1;
		pk.setUriageId(uriageId);
		pk.setMeisaiNumber(meisaiNumber);

		// do
		boolean actual = service.exsists(pk);

		// check
		assertFalse(actual);
	}

	@Test
	public void test06_getDomainList() throws Exception {

		// data
		super.deleteAndInsert(T_URIAGE_MEISAI, T_URIAGE_MEISAI_COLUMN, T_URIAGE_MEISAI_01, T_URIAGE_MEISAI_02);

		// param
		String uriageId = "r-KK01-00001";

		// do
		List<UriageMeisaiDomain> actual = service.getDomainList(uriageId);

		// check
		assertEquals(2, actual.size());
	}

	@Test
	public void test07_getMaxMeisaiNumber() throws Exception {

		// data
		super.deleteAndInsert(T_URIAGE_MEISAI, T_URIAGE_MEISAI_COLUMN, T_URIAGE_MEISAI_01, T_URIAGE_MEISAI_02);

		// param
		String uriageId = "r-KK01-00001";

		// do
		Integer actual = service.getMaxMeisaiNumber(uriageId);

		// check
		assertEquals(2, actual.intValue());
	}

	@Test
	public void test08_deleteAll() throws Exception {

		// data
		super.deleteAndInsert(T_URIAGE_MEISAI, T_URIAGE_MEISAI_COLUMN, T_URIAGE_MEISAI_01, T_URIAGE_MEISAI_02);

		// param
		String uriageId = "r-KK01-00001";
		// do
		service.deleteAll(uriageId);
		// test
		TUriageMeisaiPK pk = new TUriageMeisaiPK();
		pk.setMeisaiNumber(1);
		pk.setUriageId(uriageId);
		boolean actual = service.exsists(pk);
		assertFalse(actual);
	}

}
