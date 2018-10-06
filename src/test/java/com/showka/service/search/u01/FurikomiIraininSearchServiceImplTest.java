package com.showka.service.search.u01;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.CrudServiceTestCase;
import com.showka.domain.u01.FurikomiIraininSet;
import com.showka.domain.u01.Kokyaku;
import com.showka.entity.MFurikomiIrainin;

import mockit.Expectations;
import mockit.Mocked;

public class FurikomiIraininSearchServiceImplTest extends CrudServiceTestCase {

	@Autowired
	private FurikomiIraininSearchServiceImpl service;

	// test data
	private static final Object[] F_VALUE01 = { "r-KK01", "振込依頼人KK01-1", "r-KK01-1" };
	private static final Object[] F_VALUE02 = { "r-KK02", "振込依頼人KK02-1", "r-KK02-2" };

	@Test
	public void test_Search_01(@Mocked Kokyaku kokyaku) throws Exception {
		// database
		super.deleteAndInsert(M_FURIKOMI_IRANIN, M_FURIKOMI_IRANIN_COLUMN, F_VALUE01, F_VALUE02);
		// expect
		new Expectations() {
			{
				kokyaku.getRecordId();
				result = "r-KK01";
			}
		};
		// do
		FurikomiIraininSet actual = service.search(kokyaku);
		// assert
		assertEquals(1, actual.getSet().size());
		assertEquals(kokyaku, actual.getKokyaku());
	}

	@Test
	public void test_SearchEntity_01(@Mocked Kokyaku kokyaku) throws Exception {
		// database
		super.deleteAndInsert(M_FURIKOMI_IRANIN, M_FURIKOMI_IRANIN_COLUMN, F_VALUE01, F_VALUE02);
		// expect
		new Expectations() {
			{
				kokyaku.getRecordId();
				result = "r-KK01";
			}
		};
		// do
		List<MFurikomiIrainin> actual = service.searchEntity(kokyaku);
		// assert
		assertEquals(1, actual.size());
	}

}
