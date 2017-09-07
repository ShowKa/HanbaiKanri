package com.showka.repository.i;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.RepositoryTestCase;
import com.showka.entity.TUriageMeisai;
import com.showka.entity.TUriageMeisaiPK;

public class TUriageMeisaiRepositoryTest extends RepositoryTestCase {

	@Autowired
	private TUriageMeisaiRepository repository;

	@Test
	public void test01_findAll() {
		List<TUriageMeisai> actual = repository.findAll();
		assertNotNull(actual);
	}

	@Test
	public void test02_save() {

		// data
		String id = "KK01-00001";

		// pk
		TUriageMeisaiPK pk = new TUriageMeisaiPK();
		pk.setUriageId(id);
		pk.setMeisaiNumber(3);

		// entity
		TUriageMeisai e = new TUriageMeisai();
		e.setPk(pk);
		e.setHanbaiNumber(123);
		e.setHanbaiTanka(456);
		e.setShohinId("SH01");

		// recordId & version
		e.setRecordId("KK01-00001-3");
		e.setVersion(0);

		// test
		repository.save(e);

		// check
		TUriageMeisai actual = repository.findById(pk).get();
		assertEquals(e.getShohinId(), actual.getShohinId());

	}

}
