package com.showka.entity;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.PersistenceTestCase;
import com.showka.repository.i.MKokyakuRepository;

public class EntityManagementTest extends PersistenceTestCase {

	@Autowired
	private MKokyakuRepository repo;

	@Autowired
	private EntityManager entityManager;

	/** 顧客01. */
	private static final Object[] M_KOKYAKU_V01 = { "KK01", "aaaa", "左京区", "01", "00", "r-BS01", "r-KK01" };

	/** 部署01. */
	private static final Object[] M_BUSHO_V01 = { "BS01", "01", "01", "部署01", "r-BS01" };

	/** 部署日付01. */
	private static final Object[] M_BUSHO_DATE_V01 = { "r-BS01", d("20170820"), "r-BS01" };

	@Before
	public void deletTable() {
		super.deleteAndInsert(M_BUSHO, M_BUSHO_COLUMN, M_BUSHO_V01);
		super.deleteAndInsert(M_BUSHO_DATE, M_BUSHO_DATE_COLUMN, M_BUSHO_DATE_V01);
	}

	@Test
	public void test_insert() {
		// database
		super.deleteAll(M_KOKYAKU);
		// entity
		Optional<MKokyaku> _e = repo.findById("KK99");
		MKokyaku e = _e.orElse(new MKokyaku());
		// columns...
		e.setAddress("address");
		e.setCode("KK99");
		e.setHanbaiKubun("01");
		e.setKokyakuKubun("00");
		e.setName("name");
		e.setShukanBushoId("r-BS01");
		// record id
		e.setRecordId("r-KK99");
		// save
		repo.save(e);
		// check
		assertNull(e.getShukanBusho());
		// check (getOne したときに自動でflush & refresh させるようにした)
		MKokyaku e2 = repo.getOne("KK99");
		assertNotNull(e2.getShukanBusho());
		assertEquals("部署01", e2.getShukanBusho().getName());
	}

	@Test
	public void test_update() {
		// database
		super.deleteAndInsert(M_KOKYAKU, M_KOKYAKU_COLUMN, M_KOKYAKU_V01);
		// entity
		Optional<MKokyaku> _e = repo.findById("KK01");
		MKokyaku e = _e.orElse(new MKokyaku());
		// columns...
		e.setCode("KK01");
		e.setAddress("address_updated");
		e.setHanbaiKubun("01");
		e.setKokyakuKubun("00");
		e.setName("name");
		e.setShukanBushoId("r-BS01");
		// record id
		e.setRecordId("r-KK01");
		// check
		assertNotNull(e.getShukanBusho());
		// save
		repo.save(e);
		// check
		MKokyaku e2 = repo.findById("KK01").get();
		assertEquals("address_updated", e2.getAddress());
		assertNotNull(e2.getShukanBusho());
		assertEquals("部署01", e2.getShukanBusho().getName());
	}

	@Test
	public void test_version_when_insert() {
		// database
		super.deleteAll(M_KOKYAKU);
		// entity
		Optional<MKokyaku> _e = repo.findById("KK99");
		MKokyaku e = _e.orElse(new MKokyaku());
		// columns...
		e.setAddress("address");
		e.setCode("KK99");
		e.setHanbaiKubun("01");
		e.setKokyakuKubun("00");
		e.setName("name");
		e.setShukanBushoId("r-BS01");
		// record id
		e.setRecordId("r-KK99");
		// check
		assertNull(e.getVersion());
		// save
		repo.save(e);
		// check
		assertEquals(0, e.getVersion().intValue());
		// check
		MKokyaku e2 = repo.getOne("KK99");
		assertEquals(0, e2.getVersion().intValue());
	}

	@Test
	public void test_version_when_update() {
		// database
		super.deleteAndInsert(M_KOKYAKU, M_KOKYAKU_COLUMN, M_KOKYAKU_V01);
		// entity
		Optional<MKokyaku> _e = repo.findById("KK01");
		MKokyaku e = _e.orElse(new MKokyaku());
		// columns...
		e.setCode("KK01");
		e.setAddress("address_updated");
		e.setHanbaiKubun("01");
		e.setKokyakuKubun("00");
		e.setName("name");
		e.setShukanBushoId("r-BS01");
		// record id
		e.setRecordId("r-KK01");
		// check
		assertEquals(0, e.getVersion().intValue());
		// save
		repo.save(e);
		// check
		assertEquals(0, e.getVersion().intValue());
		// check
		repo.flush();
		assertEquals(1, e.getVersion().intValue());
	}

	@Test
	public void test_recordId_not_updatable() {
		// database
		super.deleteAndInsert(M_KOKYAKU, M_KOKYAKU_COLUMN, M_KOKYAKU_V01);
		// entity
		Optional<MKokyaku> _e = repo.findById("KK01");
		MKokyaku e = _e.orElse(new MKokyaku());
		// columns...
		e.setCode("KK01");
		e.setAddress("address_updated");
		e.setHanbaiKubun("01");
		e.setKokyakuKubun("00");
		e.setName("name");
		e.setShukanBushoId("r-BS01");
		// record id
		e.setRecordId("r-KK01_updated");
		// save
		repo.save(e);
		// check
		assertEquals("r-KK01_updated", e.getRecordId());
		// check
		repo.flush();
		assertEquals("r-KK01_updated", e.getRecordId());
		// check(DBから再取得すると recordIdが revertされる)
		entityManager.refresh(e);
		assertEquals("r-KK01", e.getRecordId());
	}

	@Test
	public void test_setNew() {
		// database
		super.deleteAll(M_KOKYAKU);
		// entity
		Optional<MKokyaku> _e = repo.findById("KK99");
		MKokyaku e = _e.orElse(new MKokyaku());
		// columns...
		e.setAddress("address");
		e.setCode("KK99");
		e.setHanbaiKubun("01");
		e.setKokyakuKubun("00");
		e.setName("name");
		e.setShukanBushoId("r-BS01");
		// record id
		e.setRecordId("r-KK99");
		// check
		assertFalse(e.isNew());
		// save
		repo.save(e);
		// check
		assertTrue(e.isNew());
		// check
		MKokyaku e2 = repo.getOne("KK99");
		assertFalse(e2.isNew());
	}
}
