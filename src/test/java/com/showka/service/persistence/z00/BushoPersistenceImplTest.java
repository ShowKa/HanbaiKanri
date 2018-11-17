package com.showka.service.persistence.z00;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.showka.common.PersistenceTestCase;
import com.showka.domain.z00.Busho;
import com.showka.entity.MBusho;
import com.showka.service.persistence.z00.BushoPersistenceImpl;
import com.showka.value.EigyoDate;

/**
 * 部署 CRUD Service Test
 *
 * @author 25767
 *
 */
public class BushoPersistenceImplTest extends PersistenceTestCase {

	@Autowired
	private BushoPersistenceImpl service;

	/**
	 * test data
	 */
	private static final Object[] VALUE01 = { "BS01", "01", "01", "部署01", "BS01" };
	private static final Object[] VALUE02 = { "BS02", "99", "02", "部署02", "BS02" };
	private static final Object[] M_BUSHO_DATE_VALUE_01 = { "BS01", d("20171112"), "BS01" };
	private static final Object[] M_BUSHO_DATE_VALUE_02 = { "BS02", d("20171112"), "BS02" };

	@Before
	public void deletTable() {
		super.deleteAll(M_BUSHO);
		super.deleteAll(M_BUSHO_DATE);
	}

	/**
	 * 既存レコードのgetテスト
	 */
	@Test
	@Transactional
	public void test_getDomain1() {

		super.insert(M_BUSHO, M_BUSHO_COLUMN, VALUE01, VALUE02);
		super.insert(M_BUSHO_DATE, M_BUSHO_DATE_COLUMN, M_BUSHO_DATE_VALUE_01, M_BUSHO_DATE_VALUE_02);

		String id = "BS02";

		// getDomain
		Busho result = service.getDomain(id);

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

		super.insert(M_BUSHO, M_BUSHO_COLUMN, VALUE01, VALUE02);

		// getDomain
		List<MBusho> bushoList = service.getMBushoList();

		assertEquals(2, bushoList.size());

	}

	/**
	 * 全部署取得
	 */
	@Test
	public void test_getDomains() throws Exception {
		super.insert(M_BUSHO, M_BUSHO_COLUMN, VALUE01, VALUE02);
		super.insert(M_BUSHO_DATE, M_BUSHO_DATE_COLUMN, M_BUSHO_DATE_VALUE_01, M_BUSHO_DATE_VALUE_02);
		List<Busho> actual = service.getDomains();
		assertEquals(2, actual.size());
	}

}
