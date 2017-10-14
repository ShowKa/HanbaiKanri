package com.showka.repository.i;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.RepositoryTestCase;
import com.showka.entity.MNyukinKakeInfo;
import com.showka.repository.i.MNyukinKakeInfoRepository;

public class MNyukinKakeInfoRepositoryTest extends RepositoryTestCase {

	@Autowired
	private MNyukinKakeInfoRepository repository;

	@Test
	public void test_01() {
		List<MNyukinKakeInfo> result = repository.findAll();
		assertNotNull(result.get(0));
	}

	@Test
	public void test_02() {
		Optional<MNyukinKakeInfo> result = repository.findById("KK03");
		assertNotNull(result);
	}
}
