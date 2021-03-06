package com.showka.repository.i;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.showka.common.RepositoryTestCase;
import com.showka.entity.MBusho;

@Transactional
public class MBushoRepositoryTest extends RepositoryTestCase {

	@Autowired
	private MBushoRepository repository;

	/** 部署01. */
	private static final Object[] VALUE01 = { "BS01", "00", "00", "部署01", "BS01" };
	/** 部署02. */
	private static final Object[] VALUE02 = { "BS02", "00", "00", "部署02", "BS02" };

	@Before
	public void deletTable() {
		super.deleteAll(M_BUSHO);
	}

	/**
	 * findbyid 該当する部署を取得
	 *
	 * <pre>
	 * 入力：部署コード <br>
	 * 条件：レコード2件、該当レコードあり <br>
	 * 結果：成功
	 *
	 * <pre>
	 */
	@Test
	public void test_01() {

		super.insert(M_BUSHO, M_BUSHO_COLUMN, VALUE01, VALUE02);

		Optional<MBusho> result = repository.findById("BS01");

		MBusho resultBusho = result.get();
		assertNotNull(resultBusho);
		assertEquals("BS01", resultBusho.getCode());
		assertEquals("00", resultBusho.getBushoKubun());
		assertEquals("00", resultBusho.getJigyoKubun());
		assertEquals("部署01", resultBusho.getName());
		assertEquals("BS01", resultBusho.getRecordId());

	}

	// findbyid 該当する部署を取得
	// 入力：部署コード
	// 条件：レコード2件、該当レコードなし
	// 結果：失敗
	@Test
	public void test_02() {

		super.insert(M_BUSHO, M_BUSHO_COLUMN, VALUE01, VALUE02);

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

		// save
		repository.save(e);

		// check
		List<MBusho> result = repository.findAll();
		assertNotNull(result);
		assertEquals(1, result.size());

		MBusho busho = result.get(0);
		assertEquals(e.getCode(), busho.getCode());
		assertEquals(e.getBushoKubun(), busho.getBushoKubun());
		assertEquals(e.getJigyoKubun(), busho.getJigyoKubun());
		assertEquals(e.getName(), busho.getName());
		assertEquals(e.getRecordId(), busho.getRecordId());

	}

	// save 部署の内容を更新
	// 入力：部署entity PKはDBに登録済みの部署と同じもの
	// 条件：レコード2件
	// 結果：成功
	@Test
	public void test_04() {

		super.insert(M_BUSHO, M_BUSHO_COLUMN, VALUE01, VALUE02);

		MBusho beforeBusho = repository.findById("BS01").get();

		assertEquals("BS01", beforeBusho.getCode());
		assertEquals("00", beforeBusho.getBushoKubun());
		assertEquals("00", beforeBusho.getJigyoKubun());
		assertEquals("部署01", beforeBusho.getName());
		assertEquals("BS01", beforeBusho.getRecordId());

		// entity
		MBusho e = repository.getOne("BS01");

		// set columns
		e.setBushoKubun("20");
		e.setJigyoKubun("10");
		e.setName("部署03");

		// set record_id & version
		e.setRecordId("this is inserted record");

		// check before save
		List<MBusho> beforeSave = repository.findAll();
		assertNotNull(beforeSave);
		assertEquals(2, beforeSave.size());

		// save
		repository.save(e);

		// check after save
		List<MBusho> result = repository.findAll();
		assertNotNull(result);
		assertEquals(2, result.size());

		MBusho resultBusho = repository.findById(e.getCode()).get();
		assertEquals(e.getCode(), resultBusho.getCode());
		assertEquals(e.getBushoKubun(), resultBusho.getBushoKubun());
		assertEquals(e.getJigyoKubun(), resultBusho.getJigyoKubun());
		assertEquals(e.getName(), resultBusho.getName());
		assertEquals(e.getRecordId(), resultBusho.getRecordId());

	}

	// existsById 部署の存在確認
	// 入力：部署コード
	// 条件：該当部署が存在すること
	// 結果：成功
	@Test
	public void test_05() {

		super.insert(M_BUSHO, M_BUSHO_COLUMN, VALUE01, VALUE02);

		boolean result = repository.existsById("BS01");
		assertTrue(result);
	}

	// existsById 部署の存在確認
	// 入力：部署コード
	// 条件：該当部署が存在しないこと
	// 結果：失敗
	@Test
	public void test_06() {

		super.insert(M_BUSHO, M_BUSHO_COLUMN, VALUE01, VALUE02);

		boolean result = repository.existsById("BS03");
		assertFalse(result);
	}

	// getOne 該当する部署を取得
	// 入力：部署コード
	// 条件：該当レコードあり
	// 結果：成功
	@Test
	public void test_07() {

		super.insert(M_BUSHO, M_BUSHO_COLUMN, VALUE01, VALUE02);

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
	public void test_08() {
		super.deleteAll(M_BUSHO);
		MBusho result = repository.getOne("XXX");
		// error occurs at here
		result.getName();
	}

	// delete 該当する部署を削除
	// 入力：部署entity
	// 条件：該当レコードあり
	// 結果：成功
	@Test
	public void test_09() {

		super.insert(M_BUSHO, M_BUSHO_COLUMN, VALUE01, VALUE02);

		// entity
		MBusho e = repository.getOne("BS01");

		// set columns
		e.setCode("BS01");
		e.setVersion(0);

		// do
		repository.delete(e);

		Optional<MBusho> result = repository.findById("BS01");
		assertFalse(result.isPresent());

	}
}
