package com.showka.service.crud.z00;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.showka.common.ServiceCrudTestCase;
import com.showka.domain.BushoDomain;
import com.showka.entity.MBusho;

/**
 * 部署 CRUD Service Test
 *
 * @author 25767
 *
 */
public class BushoCrudServiceImplTest extends ServiceCrudTestCase {

	@Autowired
	private BushoCrudServiceImpl service;

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
	private static final Object[] VALUE01 = { "BS01", "01", "01", "部署01", "BS01" };
	private static final Object[] VALUE02 = { "BS02", "99", "02", "部署02", "BS02" };

	@Before
	public void deletTable() {
		super.deleteAll(TABLE_NAME);
	}

	/**
	 * 既存レコードのgetテスト
	 */
	@Test
	@Transactional
	public void test_getDomain1() {

		super.insert(TABLE_NAME, COLUMN, VALUE01, VALUE02);

		String id = "BS02";

		// getDomain
		BushoDomain result = service.getDomain(id);

		// assert
		assertEquals("BS02", result.getCode());
		assertEquals("99", result.getBushoKubun().getCode());
		assertEquals("02", result.getJigyoKubun().getCode());
		assertEquals("部署02", result.getName());
		assertEquals("BS02", result.getRecordId());

	}

	/**
	 * 存在しないレコードのgetテスト
	 */
	@Test(expected = EntityNotFoundException.class)
	@Transactional
	public void test_getDomain2() {

		String id = "BS02";

		// getDomain
		service.getDomain(id);

		fail();
	}

	/**
	 * getMBushoListテスト
	 */
	@Test
	@Transactional
	public void test_getMBushoList() {

		super.insert(TABLE_NAME, COLUMN, VALUE01, VALUE02);

		// getDomain
		List<MBusho> bushoList = service.getMBushoList();

		assertEquals(2, bushoList.size());

	}

}
