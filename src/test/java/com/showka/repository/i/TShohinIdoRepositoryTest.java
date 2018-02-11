package com.showka.repository.i;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.RepositoryTestCase;
import com.showka.entity.TShohinIdo;

public class TShohinIdoRepositoryTest extends RepositoryTestCase {

	@Autowired
	private TShohinIdoRepository repo;

	@Test
	@Transactional
	public void test01_save() {
		// data
		super.deleteAll(T_SHOHIN_IDO);
		// do
		TShohinIdo entity = new TShohinIdo();
		entity.setBushoId("BS01");
		entity.setDate(new Date(0));
		entity.setKubun("00");
		entity.setRecordId("r-01");
		entity.setTimestamp(new Date());
		repo.save(entity);
		// check
		Optional<TShohinIdo> actual = repo.findById("r-01");
		assertTrue(actual.isPresent());
	}
}
