package com.showka.service.crud.u01;

import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.CrudServiceTestCase;
import com.showka.domain.NyukinKakeInfo;
import com.showka.domain.builder.NyukinKakeInfoBuilder;
import com.showka.entity.MNyukinKakeInfo;
import com.showka.kubun.NyukinHohoKubun;
import com.showka.kubun.NyukinTsukiKubun;
import com.showka.repository.i.MNyukinKakeInfoRepository;

public class NyukinKakeInfoCrudServiceImplTest extends CrudServiceTestCase {

	@Autowired
	private NyukinKakeInfoCrudServiceImpl service;

	@Autowired
	private MNyukinKakeInfoRepository repo;

	/** 入金掛売情報01. */
	private static final Object[] VALUE01 = { "KK03", "00", "10", "20", "30", "r-KK03" };

	/**
	 * Before
	 */
	@Before
	public void before() {
		super.deleteAll(M_NYUKIN_KAKE_INFO);
	}

	/**
	 * 新しいレコードのINERTテスト
	 */
	@Test
	public void test_insert() {

		String id = "NEW!";
		String record_id = "this is inserted record";

		// set up builder
		NyukinKakeInfoBuilder builder = new NyukinKakeInfoBuilder();
		builder.withKokyakuId(id);
		builder.withNyukinDate(11);
		builder.withNyukinHohoKubun(NyukinHohoKubun.振込);
		builder.withNyukinTsukiKubun(NyukinTsukiKubun.当月);
		builder.withShimeDate(12);
		builder.withRecordId(record_id);

		// build domain
		NyukinKakeInfo domain = builder.build();

		// save = insert
		service.save(domain);

		// check
		MNyukinKakeInfo actual = repo.findById(id).get();
		assertEquals(record_id, actual.getRecordId());

	}

	/**
	 * 既存レコードの更新テスト
	 */
	@Test
	public void test_update() {

		// insert data
		super.deleteAndInsert(M_NYUKIN_KAKE_INFO, M_NYUKIN_KAKE_INFO_COLUMN, VALUE01);

		String id = "KK03";
		Integer version = 0;
		String record_id = "this is updated record";

		// set up builder
		NyukinKakeInfoBuilder builder = new NyukinKakeInfoBuilder();
		builder.withKokyakuId(id);
		builder.withNyukinDate(11);
		builder.withNyukinHohoKubun(NyukinHohoKubun.振込);
		builder.withNyukinTsukiKubun(NyukinTsukiKubun.当月);
		builder.withShimeDate(12);
		builder.withVersion(version);
		builder.withRecordId(record_id);

		// build domain
		NyukinKakeInfo domain = builder.build();

		// save = update
		service.save(domain);

		// check
		MNyukinKakeInfo actual = repo.getOne(id);
		assertEquals("r-KK03", actual.getRecordId());

	}

	/**
	 * 排他制御テスト
	 * 
	 * <pre>
	 * 不正なversion番号を設定する。更新エラーとなればよい。
	 * </pre>
	 */
	@Test(expected = OptimisticLockException.class)
	public void test_optimistic_lock_error() {

		// insert data
		super.insert(M_NYUKIN_KAKE_INFO, M_NYUKIN_KAKE_INFO_COLUMN, VALUE01);

		String id = "KK03";
		Integer version = 999;
		String record_id = "this is updated record";

		// set up builder
		NyukinKakeInfoBuilder builder = new NyukinKakeInfoBuilder();
		builder.withKokyakuId(id);
		builder.withNyukinDate(11);
		builder.withNyukinHohoKubun(NyukinHohoKubun.振込);
		builder.withNyukinTsukiKubun(NyukinTsukiKubun.当月);
		builder.withShimeDate(12);
		builder.withVersion(version);
		builder.withRecordId(record_id);

		// build domain
		NyukinKakeInfo domain = builder.build();

		// save = update
		service.save(domain);
		fail();
	}

	@Test
	@Transactional
	public void test_deleted() {

		// insert data
		super.insert(M_NYUKIN_KAKE_INFO, M_NYUKIN_KAKE_INFO_COLUMN, VALUE01);

		String id = "KK03";
		Integer version = 0;

		// delete
		assertTrue(repo.existsById(id));
		service.delete(id, version);

		// check
		assertFalse(repo.existsById(id));
	}

	@Test
	@Transactional
	public void test_getDomain() {

		// insert data
		super.insert(M_NYUKIN_KAKE_INFO, M_NYUKIN_KAKE_INFO_COLUMN, VALUE01);

		String id = "KK03";

		// do
		NyukinKakeInfo actual = service.getDomain(id);

		// check
		assertEquals("KK03", actual.getKokyakuId());
		assertEquals("r-KK03", actual.getRecordId());
		assertEquals(Integer.valueOf(30), actual.getNyukinDate());
		assertEquals("00", actual.getNyukinHohoKubun().getCode());
		assertEquals("10", actual.getNyukinTsukiKubun().getCode());
		assertEquals(Integer.valueOf(20), actual.getShimeDate());

	}

	@Test
	public void test_exists01() {

		// insert data
		super.insert(M_NYUKIN_KAKE_INFO, M_NYUKIN_KAKE_INFO_COLUMN, VALUE01);

		String id = "KK03";

		// do
		boolean actual = service.exsists(id);

		// check
		assertTrue(actual);
	}

	@Test
	public void test_exists02() {

		// insert data
		super.insert(M_NYUKIN_KAKE_INFO, M_NYUKIN_KAKE_INFO_COLUMN, VALUE01);

		String id = "AAAAAAA";

		// do
		boolean actual = service.exsists(id);

		// check
		assertFalse(actual);
	}

	@Test
	public void test_deleteForciblyIfExists01() {

		// insert data
		super.insert(M_NYUKIN_KAKE_INFO, M_NYUKIN_KAKE_INFO_COLUMN, VALUE01);

		String id = "KK03";
		assertTrue(repo.existsById(id));

		// do
		service.deleteForciblyIfExists(id);

		// check
		assertFalse(repo.existsById(id));
	}

	@Test
	public void test_deleteForciblyIfExists02() {

		// insert data
		super.insert(M_NYUKIN_KAKE_INFO, M_NYUKIN_KAKE_INFO_COLUMN, VALUE01);

		String id = "AAAAAA";
		assertFalse(repo.existsById(id));

		// do
		service.deleteForciblyIfExists(id);

		// nothing to worry about
	}
}
