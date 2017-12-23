package com.showka.service.crud.z00;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.showka.common.ServiceCrudTestCase;
import com.showka.domain.BushoDomain;
import com.showka.entity.MBusho;
import com.showka.value.EigyoDate;

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
	 * table name
	 */
	private static final String M_BUSHO_DATE = "m_busho_date";

	/**
	 * columns
	 */
	private static final String[] COLUMN = { "code", "busho_kubun", "jigyo_kubun", "name", "record_id" };
	private static final String[] M_BUSHO_DATE_COLUMN = { "busho_id", "eigyo_date", "record_id" };

	/**
	 * test data
	 */
	private static final Object[] VALUE01 = { "BS01", "01", "01", "部署01", "BS01" };
	private static final Object[] VALUE02 = { "BS02", "99", "02", "部署02", "BS02" };
	private static final Object[] M_BUSHO_DATE_VALUE_01 = { "BS01", new Date("2017/11/12"), "BS01" };
	private static final Object[] M_BUSHO_DATE_VALUE_02 = { "BS02", new Date("2017/11/12"), "BS02" };

	@Before
	public void deletTable() {
		super.deleteAll(TABLE_NAME);
		super.deleteAll(M_BUSHO_DATE);
	}

	/**
	 * 既存レコードのgetテスト
	 */
	@Test
	@Transactional
	public void test_getDomain1() {

		super.insert(TABLE_NAME, COLUMN, VALUE01, VALUE02);
		super.insert(M_BUSHO_DATE, M_BUSHO_DATE_COLUMN, M_BUSHO_DATE_VALUE_01, M_BUSHO_DATE_VALUE_02);

		String id = "BS02";

		// getDomain
		BushoDomain result = service.getDomain(id);

		// assert
		assertEquals("BS02", result.getCode());
		assertEquals("99", result.getBushoKubun().getCode());
		assertEquals("02", result.getJigyoKubun().getCode());
		assertEquals("部署02", result.getName());
		assertEquals("BS02", result.getRecordId());
		assertEquals(new EigyoDate(2017, 11, 12), result.getEigyoDate());
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
