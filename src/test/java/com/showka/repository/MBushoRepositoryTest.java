package com.showka.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import com.showka.common.RepositoryTestCase;
import com.showka.entity.MBusho;
import com.showka.repository.i.MBushoRepository;

public class MBushoRepositoryTest extends RepositoryTestCase {

	@Autowired
	private MBushoRepository repository;

	/**
	 * table name
	 */
	private static final String TABLE_NAME = "m_busho";

	/**
	 * columns
	 */
	private static final String[] COLUMN = { "code", "busho_kubun", "jigyo_kubun", "name", "record_id" };

	/**
	 * test data
	 */
	private static final Object[] VALUE01 = { "BS01", "00", "00", "部署01", "BS01" };
	private static final Object[] VALUE02 = { "BS02", "00", "00", "部署02", "BS02" };

	@Before
	public void deletTable() {
		super.deleteAll(TABLE_NAME);
	}

	// findbyid 該当する部署を取得
	// 入力：部署コード
	// 条件：レコード2件、該当レコードあり
	// 結果：成功
	@Test
	public void test_01() {

		super.insert(TABLE_NAME, COLUMN, VALUE01, VALUE02);

		Optional<MBusho> result = repository.findById("BS01");

		assertNotNull(result.get());
		assertEquals("BS01", result.get().getCode());
		assertEquals("00", result.get().getBushoKubun());
		assertEquals("00", result.get().getJigyoKubun());
		assertEquals("部署01", result.get().getName());
		assertEquals("BS01", result.get().getRecordId());

	}

	// findbyid 該当する部署を取得
	// 入力：部署コード
	// 条件：レコード2件、該当レコードなし
	// 結果：失敗
	@Test
	public void test_02() {

		super.insert(TABLE_NAME, COLUMN, VALUE01, VALUE02);

		Optional<MBusho> result = repository.findById("BS10");
		assertFalse(result.isPresent());
	}

	// save 部署を追加
	// 入力：部署entity
	// 条件：レコード0件
	// 結果：成功
	@Test
	public void test_03() {

		// entity
		MBusho e = new MBusho();

		// set columns
		e.setCode("BS10");
		e.setBushoKubun("00");
		e.setJigyoKubun("00");
		e.setName("部署03");

		// set record_id & version
		e.setRecordId("this is inserted record");
		e.setVersion(0);

		// save
		repository.save(e);

		// check
		List<MBusho> result = repository.findAll();
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(e.getCode(), result.get(0).getCode());
		assertEquals(e.getBushoKubun(), result.get(0).getBushoKubun());
		assertEquals(e.getJigyoKubun(), result.get(0).getJigyoKubun());
		assertEquals(e.getName(), result.get(0).getName());
		assertEquals(e.getRecordId(), result.get(0).getRecordId());

	}

	// save 部署の内容を更新
	// 入力：部署entity PKはDBに登録済みの部署と同じもの
	// 条件：レコード2件
	// 結果：成功
	@Test
	public void test_04() {

		super.insert(TABLE_NAME, COLUMN, VALUE01, VALUE02);

		// entity
		MBusho e = new MBusho();

		// set columns
		e.setCode("BS01");
		e.setBushoKubun("20");
		e.setJigyoKubun("10");
		e.setName("部署03");

		// set record_id & version
		e.setRecordId("this is inserted record");
		e.setVersion(0);

		// check before save
		List<MBusho> beforeSave = repository.findAll();
		assertNotNull(beforeSave);
		assertEquals(2, beforeSave.size());

		Optional<MBusho> beforeSaveEntity = repository.findById(e.getCode());

		assertEquals("BS01", beforeSaveEntity.get().getCode());
		assertEquals("00", beforeSaveEntity.get().getBushoKubun());
		assertEquals("00", beforeSaveEntity.get().getJigyoKubun());
		assertEquals("部署01", beforeSaveEntity.get().getName());
		assertEquals("BS01", beforeSaveEntity.get().getRecordId());

		// save
		repository.save(e);

		// check after save
		List<MBusho> result = repository.findAll();
		assertNotNull(result);
		assertEquals(2, result.size());

		Optional<MBusho> resultEntity = repository.findById(e.getCode());
		assertEquals(e.getCode(), resultEntity.get().getCode());
		assertEquals(e.getBushoKubun(), resultEntity.get().getBushoKubun());
		assertEquals(e.getJigyoKubun(), resultEntity.get().getJigyoKubun());
		assertEquals(e.getName(), resultEntity.get().getName());
		assertEquals(e.getRecordId(), resultEntity.get().getRecordId());

	}

	// existsById 部署の存在確認
	// 入力：部署コード
	// 条件：該当部署が存在すること
	// 結果：成功
	@Test
	public void test_05() {

		super.insert(TABLE_NAME, COLUMN, VALUE01, VALUE02);

		boolean result = repository.existsById("BS01");
		assertTrue(result);
	}

	// existsById 部署の存在確認
	// 入力：部署コード
	// 条件：該当部署が存在しないこと
	// 結果：失敗
	@Test
	public void test_06() {

		super.insert(TABLE_NAME, COLUMN, VALUE01, VALUE02);

		boolean result = repository.existsById("BS03");
		assertFalse(result);
	}

	// getOne 該当する部署を取得
	// 入力：部署コード
	// 条件：該当レコードあり
	// 結果：成功
	@Test
	@Transactional
	public void test_07() {

		super.insert(TABLE_NAME, COLUMN, VALUE01, VALUE02);

		MBusho result = repository.getOne("BS01");

		assertNotNull(result);

		assertEquals("BS01", result.getCode());
		assertEquals("00", result.getBushoKubun());
		assertEquals("00", result.getJigyoKubun());
		assertEquals("部署01", result.getName());
		assertEquals("BS01", result.getRecordId());
	}

	// getOne 該当する部署を取得
	// 入力：部署コード
	// 条件：該当レコードなし
	// 結果：失敗
	@Test(expected = EntityNotFoundException.class)
	@Transactional
	public void test_08() {

		super.insert(TABLE_NAME, COLUMN, VALUE02);

		MBusho result = repository.getOne("BS01");

		assertNotNull(result);

		assertEquals("BS01", result.getCode());
		assertEquals("00", result.getBushoKubun());
		assertEquals("00", result.getJigyoKubun());
		assertEquals("部署01", result.getName());
		assertEquals("BS01", result.getRecordId());
	}

	////////////////////////////////////////////////
	// delete 該当する部署を削除
	// 入力：部署entity
	// 条件：該当レコードあり
	// 結果：成功
	@Test
	public void test_09() {

		Optional<MBusho> result;
		super.insert(TABLE_NAME, COLUMN, VALUE01, VALUE02);

		result = repository.findById("BS01");
		assertTrue(result.isPresent());
		result = repository.findById("BS02");
		assertTrue(result.isPresent());

		// entity
		MBusho e = new MBusho();

		// set columns
		e.setCode("BS01");
		e.setBushoKubun(null);
		e.setJigyoKubun(null);
		e.setName(null);

		// set record_id & version
		e.setRecordId("this is inserted record");
		e.setVersion(0);

		repository.delete(e);

		result = repository.findById("BS01");
		assertFalse(result.isPresent());
		result = repository.findById("BS02");
		assertTrue(result.isPresent());

	}

	////////////////////////////////////////////////
	// delete 該当する部署を削除
	// 入力：部署entity
	// 条件：該当レコードなし
	// 結果：例外発生
	@Test(expected = DataIntegrityViolationException.class)
	public void test_10() {

		Optional<MBusho> result;
		super.insert(TABLE_NAME, COLUMN, VALUE01, VALUE02);

		result = repository.findById("BS01");
		assertTrue(result.isPresent());
		result = repository.findById("BS02");
		assertTrue(result.isPresent());

		// entity
		MBusho e = new MBusho();

		// set columns
		e.setCode("BS03");
		e.setBushoKubun(null);
		e.setJigyoKubun(null);
		e.setName(null);

		// set record_id & version
		e.setRecordId("this is inserted record");
		e.setVersion(0);

		repository.delete(e);

		fail();

	}

}
