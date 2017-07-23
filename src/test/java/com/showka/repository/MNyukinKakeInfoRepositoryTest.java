package com.showka.repository;

import java.util.List;

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
}
