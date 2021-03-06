package com.showka.service.persistence.u08;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.PersistenceTestCase;
import com.showka.entity.WFirmBankFurikomiMatching;
import com.showka.repository.i.WFirmBankFurikomiMatchingRepository;
import com.showka.service.persistence.u08.FirmBankFurikomiMatchingPersistenceImpl;

public class FirmBankFurikomiMatchingPersistenceImplTest extends PersistenceTestCase {

	@Autowired
	private FirmBankFurikomiMatchingPersistenceImpl service;

	@Autowired
	private WFirmBankFurikomiMatchingRepository repo;

	// data
	private static final Object[] FB_MATHING_01 = { "r-20170820-01", "r-KK02-1-001", "r-20170820-01-KK02-1-001" };

	@Test
	public void test_DeleteAll_01() throws Exception {
		// database
		super.deleteAndInsert(W_FIRM_BANK_FURIKOMI_MATCHING, W_FIRM_BANK_FURIKOMI_MATCHING_COLUMN, FB_MATHING_01);
		// do
		service.deleteAll();
		// check
		List<WFirmBankFurikomiMatching> actual = repo.findAll();
		assertTrue(actual.isEmpty());
	}

	@Test
	public void test_Save_01() throws Exception {
		// database
		super.deleteAll(W_FIRM_BANK_FURIKOMI_MATCHING);
		// do
		service.save("r-20170820-01", "r-KK02-1-001");
		// check
		List<WFirmBankFurikomiMatching> actual = repo.findAll();
		assertEquals(1, actual.size());
	}

}
