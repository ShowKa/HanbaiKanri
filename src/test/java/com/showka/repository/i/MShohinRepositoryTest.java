package com.showka.repository.i;

import java.util.Optional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.RepositoryTestCase;
import com.showka.entity.MShohin;

public class MShohinRepositoryTest extends RepositoryTestCase {

	@Autowired
	private MShohinRepository repository;

	/** 商品01. */
	private static final Object[] M_SHOHIN_V01 = { "SH01", "商品01", 10, "r-SH01" };

	@Test
	public void test01() {
		super.deleteAndInsert(M_SHOHIN, M_SHOHIN_COLUMN, M_SHOHIN_V01);
		String code = "SH01";
		Optional<MShohin> actual = repository.findById(code);
		assertEquals(code, actual.get().getCode());
	}

}
