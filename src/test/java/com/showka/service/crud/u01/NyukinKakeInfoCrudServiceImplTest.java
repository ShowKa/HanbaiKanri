package com.showka.service.crud.u01;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import com.showka.common.ServiceCrudTestCase;
import com.showka.domain.NyukinKakeInfoDomain;
import com.showka.domain.builder.NyukinKakeInfoDomainBuilder;
import com.showka.entity.MNyukinKakeInfo;
import com.showka.kubun.NyukinHohoKubun;
import com.showka.kubun.NyukinTsukiKubun;
import com.showka.repository.i.MNyukinKakeInfoRepository;

public class NyukinKakeInfoCrudServiceImplTest extends ServiceCrudTestCase {

	@Autowired
	private NyukinKakeInfoCrudServiceImpl service;

	@Autowired
	private MNyukinKakeInfoRepository repo;

	/**
	 * table name
	 */
	private static final String TABLE_NAME = "m_nyukin_kake_info";

	/**
	 * columns
	 */
	private static final String[] COLUMN = { "kokyaku_id", "nyukin_hoho_kubun", "nyukin_tsuki_kubun", "shimebi",
			"nyukin_date", "record_id" };

	/**
	 * test data
	 */
	private static final Object[] VALUE01 = { "KK03", "00", "10", "20", "30", "KK03" };

	/**
	 * Before
	 */
	@Before
	public void before() {
		super.deleteAll(TABLE_NAME);
	}

	/**
	 * 新しいレコードのINERTテスト
	 */
	@Test
	public void test_insert() {

		String id = "NEW!";
		Integer version = 0;
		String record_id = "this is inserted record";

		// set up builder
		NyukinKakeInfoDomainBuilder builder = new NyukinKakeInfoDomainBuilder();
		builder.withKokyakuId(id);
		builder.withNyukinDate(11);
		builder.withNyukinHohoKubun(NyukinHohoKubun.振込);
		builder.withNyukinTsukiKubun(NyukinTsukiKubun.当月);
		builder.withShimeDate(12);
		builder.withVersion(version);
		builder.withRecordId(record_id);

		// build domain
		NyukinKakeInfoDomain domain = builder.build();

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
		super.insert(TABLE_NAME, COLUMN, VALUE01);

		String id = "KK03";
		Integer version = 0;
		String record_id = "this is updated record";

		// set up builder
		NyukinKakeInfoDomainBuilder builder = new NyukinKakeInfoDomainBuilder();
		builder.withKokyakuId(id);
		builder.withNyukinDate(11);
		builder.withNyukinHohoKubun(NyukinHohoKubun.振込);
		builder.withNyukinTsukiKubun(NyukinTsukiKubun.当月);
		builder.withShimeDate(12);
		builder.withVersion(version);
		builder.withRecordId(record_id);

		// build domain
		NyukinKakeInfoDomain domain = builder.build();

		// save = update
		service.save(domain);

		// check
		MNyukinKakeInfo actual = repo.findById(id).get();
		assertEquals(record_id, actual.getRecordId());

	}

	/**
	 * 排他制御テスト
	 * 
	 * <pre>
	 * 不正なversion番号を設定する。更新エラーとなればよい。
	 * </pre>
	 */
	@Test(expected = ObjectOptimisticLockingFailureException.class)
	public void test_optimistic_lock_error() {

		// insert data
		super.insert(TABLE_NAME, COLUMN, VALUE01);

		String id = "KK03";
		Integer version = 999;
		String record_id = "this is updated record";

		// set up builder
		NyukinKakeInfoDomainBuilder builder = new NyukinKakeInfoDomainBuilder();
		builder.withKokyakuId(id);
		builder.withNyukinDate(11);
		builder.withNyukinHohoKubun(NyukinHohoKubun.振込);
		builder.withNyukinTsukiKubun(NyukinTsukiKubun.当月);
		builder.withShimeDate(12);
		builder.withVersion(version);
		builder.withRecordId(record_id);

		// build domain
		NyukinKakeInfoDomain domain = builder.build();

		// save = update
		service.save(domain);
		fail();
	}

	@Test
	@Transactional
	public void test_deleted() {

		// insert data
		super.insert(TABLE_NAME, COLUMN, VALUE01);

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
		super.insert(TABLE_NAME, COLUMN, VALUE01);

		String id = "KK03";

		// do
		NyukinKakeInfoDomain actual = service.getDomain(id);

		// check
		assertEquals("KK03", actual.getKokyakuId());
		assertEquals("KK03", actual.getRecordId());
		assertEquals(Integer.valueOf(30), actual.getNyukinDate());
		assertEquals("00", actual.getNyukinHohoKubun().getCode());
		assertEquals("10", actual.getNyukinTsukiKubun().getCode());
		assertEquals(Integer.valueOf(20), actual.getShimeDate());

	}

	@Test
	public void test_exists01() {

		// insert data
		super.insert(TABLE_NAME, COLUMN, VALUE01);

		String id = "KK03";

		// do
		boolean actual = service.exsists(id);

		// check
		assertTrue(actual);
	}

	@Test
	public void test_exists02() {

		// insert data
		super.insert(TABLE_NAME, COLUMN, VALUE01);

		String id = "AAAAAAA";

		// do
		boolean actual = service.exsists(id);

		// check
		assertFalse(actual);
	}

	@Test
	public void test_deleteForciblyIfExists01() {

		// insert data
		super.insert(TABLE_NAME, COLUMN, VALUE01);

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
		super.insert(TABLE_NAME, COLUMN, VALUE01);

		String id = "AAAAAA";
		assertFalse(repo.existsById(id));

		// do
		service.deleteForciblyIfExists(id);

		// nothing to worry about
	}
}
