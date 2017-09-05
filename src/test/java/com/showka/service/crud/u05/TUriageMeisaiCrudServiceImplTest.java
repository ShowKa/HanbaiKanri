package com.showka.service.crud.u05;

import java.util.Optional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.ServiceCrudTestCase;
import com.showka.entity.TUriageMeisai;
import com.showka.entity.TUriageMeisaiPK;
import com.showka.repository.i.TUriageMeisaiRepository;

public class TUriageMeisaiCrudServiceImplTest extends ServiceCrudTestCase {

	@Autowired
	private TUriageMeisaiCrudServiceImpl service;

	@Autowired
	private TUriageMeisaiRepository repo;

	@Test
	public void test01_delete() {

		// set pk
		TUriageMeisaiPK pk = new TUriageMeisaiPK();
		String uriageId = "KK01-00001";
		Integer meisaiNumber = 1;
		pk.setUriageId(uriageId);
		pk.setMeisaiNumber(meisaiNumber);

		// set version
		Integer version = 1;

		// check exists
		TUriageMeisai before = repo.findById(pk).get();
		assertNotNull(before);

		// do
		service.delete(pk, version);

		// check deleted
		Optional<TUriageMeisai> actual = repo.findById(pk);
		assertEquals(false, actual.isPresent());
	}

}
