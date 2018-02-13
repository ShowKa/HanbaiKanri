package com.showka.service.crud.u11;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;

import com.showka.common.CrudServiceTestCase;
import com.showka.domain.Shohin;
import com.showka.domain.ShohinIdoMeisai;
import com.showka.domain.builder.ShohinIdoMeisaiBuilder;
import com.showka.entity.TShohinIdoMeisai;
import com.showka.entity.TShohinIdoMeisaiPK;
import com.showka.repository.i.TShohinIdoMeisaiRepository;
import com.showka.system.EmptyProxy;

public class ShohinIdoMeisaiCrudServiceImplTest extends CrudServiceTestCase {

	@Autowired
	private ShohinIdoMeisaiCrudServiceImpl service;

	@Autowired
	private TShohinIdoMeisaiRepository repo;

	/** 商品. */
	private static final Object[] M_SHOHIN_V01 = { "SH01", "商品01", 10, "r-SH01" };
	private static final Object[] M_SHOHIN_V02 = { "SH02", "商品02", 11, "r-SH02" };
	private static final Object[] M_SHOHIN_V03 = { "SH03", "商品03", 12, "r-SH03" };

	/** 商品移動明細01. */
	private static final Object[] T_SHOHIN_IDO_MEISAI_V01 = { "r-001", 1, "r-SH01", 10, "r-001-1" };

	/** 商品移動明細02. */
	private static final Object[] T_SHOHIN_IDO_MEISAI_V02 = { "r-001", 2, "r-SH02", 11, "r-001-2" };

	/** 商品移動明細01. */
	private static ShohinIdoMeisaiBuilder shohinIdoMeisai01;
	static {
		// 商品
		Shohin shohin = EmptyProxy.domain(Shohin.class);
		shohin.setRecordId("r-SH01");
		// build
		ShohinIdoMeisaiBuilder b = new ShohinIdoMeisaiBuilder();
		b.withMeisaiNumber(1);
		b.withShohinDomain(shohin);
		b.withNumber(10);
		shohinIdoMeisai01 = b;
	}

	/** 商品移動明細03. */
	private static ShohinIdoMeisaiBuilder shohinIdoMeisai03;
	static {
		// 商品
		Shohin shohin = EmptyProxy.domain(Shohin.class);
		shohin.setRecordId("r-SH03");
		// build
		ShohinIdoMeisaiBuilder b = new ShohinIdoMeisaiBuilder();
		b.withMeisaiNumber(3);
		b.withShohinDomain(shohin);
		b.withNumber(12);
		shohinIdoMeisai03 = b;
	}

	/**
	 * 新規登録.
	 */
	@Test
	public void test01_Save() throws Exception {
		// table
		super.deleteAll(T_SHOHIN_IDO_MEISAI);
		// other
		Shohin shohin = EmptyProxy.domain(Shohin.class);
		shohin.setRecordId("r-SH01");
		// data
		ShohinIdoMeisaiBuilder b = new ShohinIdoMeisaiBuilder();
		b.withMeisaiNumber(1);
		b.withShohinDomain(shohin);
		b.withNumber(10);
		ShohinIdoMeisai shohinIdoMeisai = b.build();
		// do
		String id = "r-001";
		service.save(id, shohinIdoMeisai);
		// check
		TShohinIdoMeisaiPK pk = new TShohinIdoMeisaiPK();
		pk.setMeisaiNumber(1);
		pk.setShohinIdoId(id);
		TShohinIdoMeisai actual = repo.getOne(pk);
		assertNotNull(actual);
	}

	/**
	 * 更新.
	 */
	@Test
	public void test02_Save() throws Exception {
		// table
		super.deleteAndInsert(T_SHOHIN_IDO_MEISAI, T_SHOHIN_IDO_MEISAI_COLUMN, T_SHOHIN_IDO_MEISAI_V01);
		// other
		Shohin shohin = EmptyProxy.domain(Shohin.class);
		shohin.setRecordId("r-SH01");
		// data
		ShohinIdoMeisaiBuilder b = new ShohinIdoMeisaiBuilder();
		b.withMeisaiNumber(1);
		b.withShohinDomain(shohin);
		b.withNumber(20);
		b.withVersion(0);
		ShohinIdoMeisai shohinIdoMeisai = b.build();
		// do
		String id = "r-001";
		service.save(id, shohinIdoMeisai);
		// check
		TShohinIdoMeisaiPK pk = new TShohinIdoMeisaiPK();
		pk.setMeisaiNumber(1);
		pk.setShohinIdoId(id);
		TShohinIdoMeisai actual = repo.getOne(pk);
		assertNotNull(actual);
		assertEquals(20, actual.getNumber().intValue());
	}

	@Test
	public void test03_Delete() throws Exception {
		// table
		super.deleteAndInsert(T_SHOHIN_IDO_MEISAI, T_SHOHIN_IDO_MEISAI_COLUMN, T_SHOHIN_IDO_MEISAI_V01);
		// set primary key
		TShohinIdoMeisaiPK pk = new TShohinIdoMeisaiPK();
		pk.setMeisaiNumber(1);
		pk.setShohinIdoId("r-001");
		// before
		Optional<TShohinIdoMeisai> before = repo.findById(pk);
		assertTrue(before.isPresent());
		// do
		service.delete(pk, 0);
		// check
		Optional<TShohinIdoMeisai> actual = repo.findById(pk);
		assertFalse(actual.isPresent());
	}

	@Test
	public void test04_DeleteAll() throws Exception {
		// table
		super.deleteAndInsert(T_SHOHIN_IDO_MEISAI, T_SHOHIN_IDO_MEISAI_COLUMN, T_SHOHIN_IDO_MEISAI_V01,
				T_SHOHIN_IDO_MEISAI_V02);
		// set primary key
		TShohinIdoMeisaiPK pk = new TShohinIdoMeisaiPK();
		pk.setShohinIdoId("r-001");
		TShohinIdoMeisai entity = new TShohinIdoMeisai();
		entity.setPk(pk);
		// before
		List<TShohinIdoMeisai> before = repo.findAll(Example.of(entity));
		assertTrue(before.size() == 2);
		// do
		service.deleteAll("r-001");
		// check
		List<TShohinIdoMeisai> actual = repo.findAll(Example.of(entity));
		assertTrue(actual.size() == 0);
	}

	@Test
	public void test05_OverrideList() throws Exception {
		// table
		super.deleteAndInsert(T_SHOHIN_IDO_MEISAI, T_SHOHIN_IDO_MEISAI_COLUMN, T_SHOHIN_IDO_MEISAI_V01,
				T_SHOHIN_IDO_MEISAI_V02);
		super.deleteAndInsert(M_SHOHIN, M_SHOHIN_COLUMN, M_SHOHIN_V01, M_SHOHIN_V02, M_SHOHIN_V03);
		// data
		List<ShohinIdoMeisai> meisaiList = new ArrayList<ShohinIdoMeisai>();
		ShohinIdoMeisai meisai01 = shohinIdoMeisai01.build();
		meisai01.setVersion(0); // occ
		meisaiList.add(meisai01);
		ShohinIdoMeisai meisai03 = shohinIdoMeisai03.build();
		meisaiList.add(meisai03);
		// do
		service.overrideList("r-001", meisaiList);
		// check
		assertTrue(repo.existsById(new TShohinIdoMeisaiPK("r-001", 1)));
		assertFalse(repo.existsById(new TShohinIdoMeisaiPK("r-001", 2)));
		assertTrue(repo.existsById(new TShohinIdoMeisaiPK("r-001", 3)));
	}

	@Test
	public void test06_OverrideList() throws Exception {
		// table
		super.deleteAndInsert(T_SHOHIN_IDO_MEISAI, T_SHOHIN_IDO_MEISAI_COLUMN, T_SHOHIN_IDO_MEISAI_V01,
				T_SHOHIN_IDO_MEISAI_V02);
		super.deleteAndInsert(M_SHOHIN, M_SHOHIN_COLUMN, M_SHOHIN_V01, M_SHOHIN_V02, M_SHOHIN_V03);
		// data
		List<ShohinIdoMeisai> meisaiList = new ArrayList<ShohinIdoMeisai>();
		// do
		service.overrideList("r-001", meisaiList);
		// check
		assertFalse(repo.existsById(new TShohinIdoMeisaiPK("r-001", 1)));
		assertFalse(repo.existsById(new TShohinIdoMeisaiPK("r-001", 2)));
	}

	@Test
	public void test07_GetDomain() throws Exception {
		// table
		super.deleteAndInsert(T_SHOHIN_IDO_MEISAI, T_SHOHIN_IDO_MEISAI_COLUMN, T_SHOHIN_IDO_MEISAI_V01,
				T_SHOHIN_IDO_MEISAI_V02);
		super.deleteAndInsert(M_SHOHIN, M_SHOHIN_COLUMN, M_SHOHIN_V01, M_SHOHIN_V02);
		// do
		TShohinIdoMeisaiPK pk = new TShohinIdoMeisaiPK("r-001", 1);
		ShohinIdoMeisai actual = service.getDomain(pk);
		assertEquals("r-001-1", actual.getRecordId());
	}

	@Test
	public void test08_GetDomainList() throws Exception {
		// table
		super.deleteAndInsert(T_SHOHIN_IDO_MEISAI, T_SHOHIN_IDO_MEISAI_COLUMN, T_SHOHIN_IDO_MEISAI_V01,
				T_SHOHIN_IDO_MEISAI_V02);
		super.deleteAndInsert(M_SHOHIN, M_SHOHIN_COLUMN, M_SHOHIN_V01, M_SHOHIN_V02);
		// do
		List<ShohinIdoMeisai> actual = service.getDomainList("r-001");
		assertEquals(2, actual.size());
	}

}
