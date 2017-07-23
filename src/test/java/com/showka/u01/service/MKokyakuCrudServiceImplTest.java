package com.showka.u01.service;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.ServiceCrudTestCase;
import com.showka.entity.MKokyaku;

public class MKokyakuCrudServiceImplTest extends ServiceCrudTestCase {

	/**
	 * service.
	 */
	@Autowired
	private MKokyakuCrudServiceImpl service;

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
	private static final Object[] VALUE03 = { "KK03", "bbbb", "上京区", "00", "01", "BS02", "KK03" };

	/**
	 * Before
	 */
	@Before
	public void before() {
		super.deleteAll(TABLE_NAME);
		super.insert(TABLE_NAME, COLUMN, VALUE01, VALUE02, VALUE03);
	}

	@Test
	public void testGetKokyauByName() throws Exception {
		List<MKokyaku> results = service.getKokyauByName("aaaa");
		assertEquals(2, results.size());
	}

	@Test
	public void testGetAllKokyaku() throws Exception {
		List<MKokyaku> result = service.getAllKokyaku();
		assertEquals(3, result.size());
	}

	@Test
	public void testSearch() throws Exception {
		MKokyakuCrudSearchCriteria c = new MKokyakuCrudSearchCriteria();
		c.setName("aaaa");
		c.setBushoName("部署02");
		List<MKokyaku> result = service.search(c);
		assertEquals(1, result.size());
	}

}
