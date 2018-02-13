package com.showka.service.crud.u05;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.CrudServiceTestCase;
import com.showka.domain.Shohin;
import com.showka.domain.UriageMeisai;
import com.showka.domain.builder.UriageMeisaiBuilder;
import com.showka.entity.RUriageMeisai;
import com.showka.entity.RUriageMeisaiPK;
import com.showka.repository.i.RUriageMeisaiRepository;
import com.showka.service.crud.z00.i.MShohinCrudService;
import com.showka.system.EmptyProxy;

import mockit.Injectable;
import mockit.Tested;

public class UriageRirekiMeisaiCrudServiceImplTest extends CrudServiceTestCase {

	@Tested
	private UriageRirekiMeisaiCrudServiceImpl service;

	@Injectable
	@Autowired
	private RUriageMeisaiRepository repo;

	@Injectable
	private MShohinCrudService shohinCrudService;

	/** 売上履歴明細01. */
	private static final Object[] R_URIAGE_MEISAI_01 = {
			"r-KK01-00001-20170820",
			1,
			"r-SH01",
			5,
			1000,
			"r-KK01-00001-20170820-1" };

	/** 売上履歴明細02. */
	private static final Object[] R_URIAGE_MEISAI_02 = {
			"r-KK01-00001-20170820",
			2,
			"r-SH02",
			5,
			1001,
			"r-KK01-00001-20170820-2" };

	/** 売上履歴明細01. */
	private static final UriageMeisai meisai01;
	static {
		// 商品ドメインダミー
		Shohin shohinDomain = EmptyProxy.domain(Shohin.class);

		// 売上明細主キー
		String uriageId = "r-KK01-00001-20170820";
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
		meisai01 = b.build();
	}

	/** 商品01. */
	private static final Object[] M_SHOHIN_01 = { "SH01", "商品01", 10, "r-SH01" };
	/** 商品02. */
	private static final Object[] M_SHOHIN_02 = { "SH02", "商品01", 11, "r-SH02" };

	@Before
	public void insertShohin() {
		super.deleteAndInsert(M_SHOHIN, M_SHOHIN_COLUMN, M_SHOHIN_01, M_SHOHIN_02);
	}

	@Test
	public void test01_save_insert() throws Exception {
		// data
		deleteAll(R_URIAGE_MEISAI);
		// do
		meisai01.setVersion(null);
		service.save(meisai01);
		// check
		RUriageMeisaiPK id = new RUriageMeisaiPK();
		id.setUriageId(meisai01.getUriageId());
		id.setMeisaiNumber(meisai01.getMeisaiNumber());
		RUriageMeisai actual = repo.findById(id).get();
		assertNotNull(actual);
	}

	@Test
	public void test02_save_update() throws Exception {
		// data
		deleteAndInsert(R_URIAGE_MEISAI, R_URIAGE_MEISAI_COLUMN, R_URIAGE_MEISAI_01);
		// do
		meisai01.setVersion(0);
		service.save(meisai01);
		// check
		RUriageMeisaiPK id = new RUriageMeisaiPK();
		id.setUriageId(meisai01.getUriageId());
		id.setMeisaiNumber(meisai01.getMeisaiNumber());
		RUriageMeisai actual = repo.findById(id).get();
		assertEquals(meisai01.getRecordId(), actual.getRecordId());
	}

	@Test
	public void test03_delete() throws Exception {
		// data
		deleteAndInsert(R_URIAGE_MEISAI, R_URIAGE_MEISAI_COLUMN, R_URIAGE_MEISAI_01);
		// do
		meisai01.setVersion(0);
		service.delete(meisai01);
		// check
		RUriageMeisaiPK id = new RUriageMeisaiPK();
		id.setUriageId(meisai01.getUriageId());
		id.setMeisaiNumber(meisai01.getMeisaiNumber());
		assertEquals(false, repo.existsById(id));
	}

	@Test
	public void test04_delete() throws Exception {
		// data
		deleteAndInsert(R_URIAGE_MEISAI, R_URIAGE_MEISAI_COLUMN, R_URIAGE_MEISAI_01);
		// do
		RUriageMeisaiPK id = new RUriageMeisaiPK();
		id.setUriageId(meisai01.getUriageId());
		id.setMeisaiNumber(meisai01.getMeisaiNumber());
		service.delete(id, 0);
		// check
		assertEquals(false, repo.existsById(id));
	}

	@Test
	public void test05_getDomain() throws Exception {
		// data
		deleteAndInsert(R_URIAGE_MEISAI, R_URIAGE_MEISAI_COLUMN, R_URIAGE_MEISAI_01);
		// do
		RUriageMeisaiPK id = new RUriageMeisaiPK();
		id.setUriageId(meisai01.getUriageId());
		id.setMeisaiNumber(meisai01.getMeisaiNumber());
		// check
		UriageMeisai actual = service.getDomain(id);
		assertEquals(meisai01, actual);
	}

	@Test
	public void test06_getDomainList() throws Exception {
		// data
		deleteAndInsert(R_URIAGE_MEISAI, R_URIAGE_MEISAI_COLUMN, R_URIAGE_MEISAI_01, R_URIAGE_MEISAI_02);
		// do
		List<UriageMeisai> actual = service.getDomainList("r-KK01-00001-20170820");
		// check
		assertEquals(2, actual.size());
	}

	@Test
	public void test07_getDomainList() throws Exception {
		// data
		deleteAndInsert(R_URIAGE_MEISAI, R_URIAGE_MEISAI_COLUMN, R_URIAGE_MEISAI_01);
		// do
		RUriageMeisaiPK id = new RUriageMeisaiPK();
		id.setUriageId(meisai01.getUriageId());
		id.setMeisaiNumber(meisai01.getMeisaiNumber());
		// do
		boolean actual = service.exsists(id);
		// check
		assertEquals(true, actual);
	}

	@Test
	public void test08_overrideList() throws Exception {
		// data
		deleteAndInsert(R_URIAGE_MEISAI, R_URIAGE_MEISAI_COLUMN, R_URIAGE_MEISAI_01, R_URIAGE_MEISAI_02);
		// do
		List<UriageMeisai> meisaiList = new ArrayList<UriageMeisai>();
		UriageMeisaiBuilder b = new UriageMeisaiBuilder();
		b.withHanbaiNumber(999);
		UriageMeisai updated = b.apply(meisai01);
		meisaiList.add(updated);
		service.overrideList(meisaiList);
		// check
		List<UriageMeisai> actual = service.getDomainList("r-KK01-00001-20170820");
		assertEquals(1, actual.size());
		assertEquals(new Integer(999), actual.get(0).getHanbaiNumber());
	}

	@Test
	public void test09_deleteAll() throws Exception {
		// data
		deleteAndInsert(R_URIAGE_MEISAI, R_URIAGE_MEISAI_COLUMN, R_URIAGE_MEISAI_01, R_URIAGE_MEISAI_02);
		// do
		String uriageId = "r-KK01-00001-20170820";
		service.deleteAll(uriageId);
		// check
		List<UriageMeisai> actual = service.getDomainList(uriageId);
		assertTrue(actual.isEmpty());
	}
}
