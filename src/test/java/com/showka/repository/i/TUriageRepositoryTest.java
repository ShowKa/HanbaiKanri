package com.showka.repository.i;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.RepositoryTestCase;
import com.showka.entity.TUriage;
import com.showka.repository.i.TUriageRepository;

public class TUriageRepositoryTest extends RepositoryTestCase {

	@Autowired
	private TUriageRepository repository;

	@Test
	public void test_01() {
		List<TUriage> actual = repository.findAll();
		assertNotNull(actual.get(0));
	}
}
