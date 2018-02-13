package com.showka.service.crud.u05;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.showka.common.CrudServiceTestCase;
import com.showka.domain.Shohin;
import com.showka.domain.UriageMeisai;
import com.showka.domain.builder.ShohinBuilder;
import com.showka.domain.builder.UriageMeisaiBuilder;
import com.showka.entity.MShohin;
import com.showka.entity.TUriageMeisai;
import com.showka.entity.TUriageMeisaiPK;
import com.showka.repository.i.TUriageMeisaiRepository;
import com.showka.service.crud.z00.i.MShohinCrudService;
import com.showka.system.EmptyProxy;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.Tested;
import mockit.Verifications;

public class UriageMeisaiCrudServiceImplTest extends CrudServiceTestCase {

	@Tested
	private UriageMeisaiCrudServiceImpl service;

	@Autowired
	@Injectable
	private TUriageMeisaiRepository repo;

	@Injectable
	private MShohinCrudService shohinService;

	/** 売上明細01 */
	private static final Object[] T_URIAGE_MEISAI_01 = { "r-KK01-00001", 1, "r-SH01", 5, 1000, "r-KK01-00001-1" };
	/** 売上明細02 */
	private static final Object[] T_URIAGE_MEISAI_02 = { "r-KK01-00001", 2, "r-SH02", 6, 1001, "r-KK01-00001-2" };
	/** 商品01. */
	private static final Object[] M_SHOHIN_01 = { "SH01", "商品01", 10, "r-SH01" };
	/** 商品02. */
	private static final Object[] M_SHOHIN_02 = { "SH02", "商品01", 11, "r-SH02" };

	@Test
	public void test01_save() {
		// table
		super.deleteAndInsert(T_URIAGE_MEISAI, T_URIAGE_MEISAI_COLUMN, T_URIAGE_MEISAI_01);
		// 商品ドメインダミー
		ShohinBuilder sh = new ShohinBuilder();
		sh.withRecordId("shohin_record_id");
		Shohin shohinDomain = sh.build();
		// 売上明細主キー
		String uriageId = "r-KK01-00001";
		Integer meisaiNumber = 1;
		String recordID = uriageId + "-" + meisaiNumber;
		// 売上明細ドメイン
		UriageMeisaiBuilder b = new UriageMeisaiBuilder();
		b.withHanbaiNumber(10);
		b.withHanbaiTanka(BigDecimal.valueOf(200));
		b.withMeisaiNumber(meisaiNumber);
		b.withRecordId(recordID);
		b.withShohinDomain(shohinDomain);
		b.withUriageId(uriageId);
		b.withVersion(0);
		UriageMeisai domain = b.build();
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
		// table
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
		// table
		super.deleteAndInsert(T_URIAGE_MEISAI, T_URIAGE_MEISAI_COLUMN, T_URIAGE_MEISAI_01);
		// set pk
		TUriageMeisaiPK pk = new TUriageMeisaiPK();
		String uriageId = "r-KK01-00001";
		Integer meisaiNumber = 1;
		pk.setUriageId(uriageId);
		pk.setMeisaiNumber(meisaiNumber);
		// mock
		new MockUp<TUriageMeisai>() {
			@Mock
			public MShohin getShohin() {
				MShohin e = new MShohin();
				e.setCode("SH01");
				return e;
			}
		};
		// expect
		new Expectations() {
			{
				shohinService.getDomain("SH01");
				result = EmptyProxy.domain(Shohin.class);
			}
		};
		// do
		UriageMeisai d = service.getDomain(pk);
		// verify
		new Verifications() {
			{
				shohinService.getDomain("SH01");
				times = 1;
			}
		};
		// check
		assertEquals(uriageId, d.getUriageId());
		assertEquals(meisaiNumber, d.getMeisaiNumber());
		assertNotNull(d.getShohinDomain());
	}

	@Test
	public void test04_exists() {
		// table
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
		// table
		super.deleteAndInsert(T_URIAGE_MEISAI, T_URIAGE_MEISAI_COLUMN, T_URIAGE_MEISAI_01, T_URIAGE_MEISAI_02);
		super.deleteAndInsert(M_SHOHIN, M_SHOHIN_COLUMN, M_SHOHIN_01, M_SHOHIN_02);
		// parameter
		String uriageId = "r-KK01-00001";
		// expect
		new Expectations() {
			{
				shohinService.getDomain("SH01");
				result = EmptyProxy.domain(Shohin.class);
				shohinService.getDomain("SH02");
				result = EmptyProxy.domain(Shohin.class);
			}
		};
		// do
		List<UriageMeisai> actual = service.getDomainList(uriageId);
		// verify
		new Verifications() {
			{
				shohinService.getDomain("SH01");
				times = 1;
				shohinService.getDomain("SH02");
				times = 1;
			}
		};
		// check
		assertEquals(2, actual.size());
	}

	@Test
	public void test07_getMaxMeisaiNumber() throws Exception {
		// table
		super.deleteAndInsert(T_URIAGE_MEISAI, T_URIAGE_MEISAI_COLUMN, T_URIAGE_MEISAI_01, T_URIAGE_MEISAI_02);
		// parameter
		String uriageId = "r-KK01-00001";
		// do
		Integer actual = service.getMaxMeisaiNumber(uriageId);
		// check
		assertEquals(2, actual.intValue());
	}

	@Test
	public void test08_deleteAll() throws Exception {
		// table
		super.deleteAndInsert(T_URIAGE_MEISAI, T_URIAGE_MEISAI_COLUMN, T_URIAGE_MEISAI_01, T_URIAGE_MEISAI_02);
		// parameter
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
