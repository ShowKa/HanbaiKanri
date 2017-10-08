package com.showka.service.search.u01;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.ServiceCrudTestCase;
import com.showka.entity.MKokyaku;

public class KokyakuSearchServiceImplTest extends ServiceCrudTestCase {

	/**
	 * service.
	 */
	@Autowired
	private KokyakuSearchServiceImpl service;

	/**
	 * table name
	 */
	private static final String M_KOKYAKU = "m_kokyaku";
	/**
	 * table name
	 */
	private static final String M_BUSHO = "m_busho";
	/**
	 * columns
	 */
	private static final String[] K_COLUMN = { "code", "name", "address", "hanbai_kubun", "kokyaku_kubun",
			"shukan_busho_id", "record_id" };

	private static final String[] B_COLUMN = { "code", "busho_kubun", "jigyo_kubun", "name", "record_id" };

	/**
	 * test data
	 */
	// kokyaku
	private static final Object[] K_VALUE01 = { "KK01", "aaaa", "左京区", "00", "01", "BS01", "KK01" };
	private static final Object[] K_VALUE02 = { "KK02", "aaaa", "右京区", "00", "01", "BS02", "KK02" };
	private static final Object[] K_VALUE03 = { "KK03", "bbbb", "上京区", "00", "01", "BS02", "KK03" };
	// busho
	private static final Object[] B_VALUE01 = { "BS01", "01", "01", "部署01", "BS01" };
	private static final Object[] B_VALUE02 = { "BS02", "99", "02", "部署02", "BS02" };

	/**
	 * Before
	 */
	@Before
	public void before() {
		super.deleteAll(M_KOKYAKU);
		super.insert(M_KOKYAKU, K_COLUMN, K_VALUE01, K_VALUE02, K_VALUE03);
		super.deleteAll(M_BUSHO);
		super.insert(M_BUSHO, B_COLUMN, B_VALUE01, B_VALUE02);
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
		KokyakuSearchCriteria c = new KokyakuSearchCriteria();
		c.setName("aaaa");
		c.setBushoName("部署02");
		List<MKokyaku> result = service.search(c);
		assertEquals(1, result.size());
	}

	@Test
	public void testSearchWithoutBusho() throws Exception {
		KokyakuSearchCriteria c = new KokyakuSearchCriteria();
		c.setName("aaaa");
		List<MKokyaku> result = service.search(c);
		assertEquals(2, result.size());
	}
}
