package com.showka.repository.i;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.RepositoryTestCase;
import com.showka.entity.TUriage;

public class TUriageRepositoryTest extends RepositoryTestCase {

	@Autowired
	private TUriageRepository repository;

	/** 売上01. */
	private static final Object[] T_URIAGE_V01 = {
			"r-KK01",
			"00001",
			new Date(),
			new Date(),
			"00",
			0.08,
			"r-KK01-00001" };

	@Test
	public void test_01() {
		super.deleteAndInsert(T_URIAGE, T_URIAGE_COLUMN, T_URIAGE_V01);
		List<TUriage> actual = repository.findAll();
		assertNotNull(actual.get(0));
	}

	@Test
	public void test_02_recordId() {
		super.deleteAndInsert(T_URIAGE, T_URIAGE_COLUMN, T_URIAGE_V01);
		TUriage actual = repository.findByRecordId("r-KK01-00001");
		assertEquals("r-KK01-00001", actual.getRecordId());
	}
}
