package com.showka.service.crud.u01;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.transaction.annotation.Transactional;

import com.showka.common.ServiceCrudTestCase;
import com.showka.domain.KokyakuDomain;
import com.showka.domain.builder.KokyakuDomainBuilder;
import com.showka.entity.MKokyaku;
import com.showka.kubun.HanbaiKubun;
import com.showka.kubun.KokyakuKubun;
import com.showka.repository.i.MKokyakuRepository;

/**
 * 顧客 CRUD Service Test
 *
 * @author 25767
 *
 */
public class KokyakuCrudServiceImplTest extends ServiceCrudTestCase {

	@Autowired
	private KokyakuCrudServiceImpl service;

	@Autowired
	private MKokyakuRepository repo;

	/**
	 * table name
	 */
	private static final String TABLE_NAME = "m_kokyaku";

	/**
	 * columns
	 */
	private static final String[] COLUMN = { "code", "name", "address", "hanbai_kubun", "kokyaku_kubun",
			"shukan_busho_id", "record_id" };

	/**
	 * test data
	 */
	private static final Object[] VALUE01 = { "KK01", "aaaa", "左京区", "00", "01", "BS01", "KK01" };
	private static final Object[] VALUE02 = { "KK02", "aaaa", "右京区", "00", "01", "BS02", "KK02" };
	private static final Object[] VALUE03 = { "KK03", "bbbb", "上京区", "10", "01", "BS02", "KK03" };

	@Before
	public void deletTable() {
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
		KokyakuDomainBuilder builder = new KokyakuDomainBuilder();
		builder.withCode(id);
		builder.withName("aaaa");
		builder.withAddress("北区");
		builder.withKokyakuKubun(KokyakuKubun.法人);
		builder.withHanbaiKubun(HanbaiKubun.掛売);
		builder.withShukanBushoId("BS01");

		builder.withVersion(version);
		builder.withRecordId(record_id);

		// build domain
		KokyakuDomain domain = builder.build();

		// save = insert
		service.save(domain);

		// assert
		MKokyaku actual = repo.findById(id).get();
		assertEquals(record_id, actual.getRecordId());

	}

	/**
	 * 既存レコードの更新テスト
	 */
	@Test
	public void test_update() {

		super.insert(TABLE_NAME, COLUMN, VALUE01, VALUE02, VALUE03);

		String id = "KK01";
		Integer version = 0;
		String record_id = "this is inserted record";

		// set up builder
		KokyakuDomainBuilder builder = new KokyakuDomainBuilder();
		builder.withCode(id);
		builder.withName("aaaa");
		builder.withAddress("北区");
		builder.withKokyakuKubun(KokyakuKubun.法人);
		builder.withHanbaiKubun(HanbaiKubun.掛売);
		builder.withShukanBushoId("BS01");

		builder.withVersion(version);
		builder.withRecordId(record_id);

		// build domain
		KokyakuDomain domain = builder.build();

		// save = update
		service.save(domain);

		// assert
		MKokyaku actual = repo.findById(id).get();
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

		super.insert(TABLE_NAME, COLUMN, VALUE01, VALUE02, VALUE03);

		String id = "KK01";
		Integer version = 999;
		String record_id = "this is inserted record";

		// set up builder
		KokyakuDomainBuilder builder = new KokyakuDomainBuilder();
		builder.withCode(id);
		builder.withName("aaaa");
		builder.withAddress("北区");
		builder.withKokyakuKubun(KokyakuKubun.法人);
		builder.withHanbaiKubun(HanbaiKubun.掛売);
		builder.withShukanBushoId("BS01");

		builder.withVersion(version);
		builder.withRecordId(record_id);

		// build domain
		KokyakuDomain domain = builder.build();

		// save = update
		service.save(domain);
		fail();

	}

	/**
	 * 既存レコードの削除テスト
	 */
	@Test
	@Transactional
	public void test_delete1() {

		super.insert(TABLE_NAME, COLUMN, VALUE01, VALUE02, VALUE03);

		String id = "KK03";
		Integer version = 0;

		// delete
		service.delete(id, version);

		// assert
		Optional<MKokyaku> result = repo.findById(id);
		assertFalse(result.isPresent());

	}

	/**
	 * 存在しないレコードの削除テスト
	 */
	@Test
	@Transactional
	public void test_delete2() {

		String id = "KK03";
		Integer version = 0;

		// delete
		service.delete(id, version);

		// assert
		Optional<MKokyaku> result = repo.findById(id);
		assertFalse(result.isPresent());

	}

	/**
	 * 既存レコードの存在確認テスト
	 */
	@Test
	public void test_exsists1() {

		super.insert(TABLE_NAME, COLUMN, VALUE01, VALUE02, VALUE03);

		String id = "KK03";

		boolean result = service.exsists(id);
		assertTrue(result);

	}

	/**
	 * 存在しないレコードの存在確認テスト
	 */
	@Test
	public void test_exsists2() {

		String id = "KK03";

		boolean result = service.exsists(id);
		assertFalse(result);
	}

	/**
	 * 既存レコードのgetテスト
	 */
	@Test
	@Transactional
	public void test_getDomain1() {

		super.insert(TABLE_NAME, COLUMN, VALUE01, VALUE02, VALUE03);

		String id = "KK03";

		// getDomain
		KokyakuDomain result = service.getDomain(id);

		// assert
		assertEquals("KK03", result.getCode());
		assertEquals("bbbb", result.getName());
		assertEquals("上京区", result.getAddress());
		assertEquals("10", result.getHanbaiKubun().getCode());
		assertEquals("01", result.getKokyakuKubun().getCode());
		assertEquals("BS02", result.getShukanBushoId());
		assertEquals("KK03", result.getRecordId());

	}

	/**
	 * 存在しないレコードのgetテスト
	 */
	@Test(expected = EntityNotFoundException.class)
	@Transactional
	public void test_getDomain2() {

		String id = "KK03";

		// getDomain
		service.getDomain(id);

	}

}
