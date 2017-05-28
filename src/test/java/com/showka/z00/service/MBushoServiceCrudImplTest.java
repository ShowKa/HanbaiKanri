package com.showka.z00.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.ServiceCrudTestCase;
import com.showka.entity.MBusho;

public class MBushoServiceCrudImplTest extends ServiceCrudTestCase {

	@Autowired
	MBushoServiceCrudImpl service;

	/**
	 * table name
	 */
	public static final String TABLE_NAME = "m_busho";

	/**
	 * columns
	 */
	public static final String[] COLUMN = { "code", "busho_kubun", "jigyo_kubun", "name" };

	/**
	 * test data
	 */
	public static final Object[] VALUE01 = { "BS01", "00", "00", "部署01" };
	public static final Object[] VALUE02 = { "BS02", "00", "00", "部署02" };

	/**
	 * Before
	 */
	@Before
	public void before() {
		super.deleteAll(TABLE_NAME);
		super.insert(TABLE_NAME, COLUMN, VALUE01, VALUE02);
	}

	@Test
	public void testFindById_01() throws Exception {
		MBusho actual = service.findById("BS01");
		assertEquals("部署01", actual.getName());
	}

}
