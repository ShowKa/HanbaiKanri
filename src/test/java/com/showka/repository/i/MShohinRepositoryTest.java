package com.showka.repository.i;

import java.util.Optional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.RepositoryTestCase;
import com.showka.entity.MShohin;

public class MShohinRepositoryTest extends RepositoryTestCase {

	@Autowired
	private MShohinRepository repository;

	@Test
	public void test01() {
		String code = "SH01";
		Optional<MShohin> actual = repository.findById(code);
		assertEquals(code, actual.get().getCode());
	}

}
