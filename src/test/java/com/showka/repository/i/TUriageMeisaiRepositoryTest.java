package com.showka.repository.i;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.RepositoryTestCase;
import com.showka.entity.TUriageMeisai;

public class TUriageMeisaiRepositoryTest extends RepositoryTestCase {

	@Autowired
	private TUriageMeisaiRepository repository;

	@Test
	public void test01() {
		List<TUriageMeisai> actual = repository.findAll();
		assertNotNull(actual);
	}

}
