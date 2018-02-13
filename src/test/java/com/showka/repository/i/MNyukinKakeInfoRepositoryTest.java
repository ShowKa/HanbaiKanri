package com.showka.repository.i;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.RepositoryTestCase;
import com.showka.entity.MNyukinKakeInfo;

public class MNyukinKakeInfoRepositoryTest extends RepositoryTestCase {

	@Autowired
	private MNyukinKakeInfoRepository repository;

	/** 入金掛売情報01. */
	private static final Object[] M_NYUKIN_KAKE_INFO_V01 = { "r-KK01", "00", "10", 15, 25, "r-KK01" };

	@Test
	public void test_01() {
		super.deleteAndInsert(M_NYUKIN_KAKE_INFO, M_NYUKIN_KAKE_INFO_COLUMN, M_NYUKIN_KAKE_INFO_V01);
		List<MNyukinKakeInfo> result = repository.findAll();
		assertNotNull(result.get(0));
	}

	@Test
	public void test_02() {
		super.deleteAndInsert(M_NYUKIN_KAKE_INFO, M_NYUKIN_KAKE_INFO_COLUMN, M_NYUKIN_KAKE_INFO_V01);
		Optional<MNyukinKakeInfo> result = repository.findById("r-KK01");
		assertTrue(result.isPresent());
	}
}
