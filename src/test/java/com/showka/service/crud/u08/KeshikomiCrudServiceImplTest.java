package com.showka.service.crud.u08;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.CrudServiceTestCase;
import com.showka.domain.Keshikomi;
import com.showka.domain.Nyukin;
import com.showka.domain.Urikake;
import com.showka.domain.builder.KeshikomiBuilder;
import com.showka.domain.builder.NyukinBuilder;
import com.showka.domain.builder.UrikakeBuilder;
import com.showka.entity.TKeshikomi;
import com.showka.repository.i.TKeshikomiRepository;
import com.showka.value.EigyoDate;

public class KeshikomiCrudServiceImplTest extends CrudServiceTestCase {

	@Autowired
	private KeshikomiCrudServiceImpl service;

	@Autowired
	private TKeshikomiRepository repo;

	private static final Object[] T_KESHIKOMI_01 = { "r-001", d("20170101"), 1000, "r-001", "r-001", "r-001" };
	private static final Object[] T_KESHIKOMI_02 = { "r-002", d("20170101"), 2000, "r-001", "r-002", "r-002" };

	/**
	 * 新規登録.
	 */
	@Test
	public void test01_save() throws Exception {
		// database
		super.deleteAll(T_KESHIKOMI);
		// input
		// 入金
		NyukinBuilder nb = new NyukinBuilder();
		nb.withRecordId("r-001");
		Nyukin nyukin = nb.build();
		// 売掛
		UrikakeBuilder ub = new UrikakeBuilder();
		ub.withRecordId("r-001");
		Urikake urikake = ub.build();
		// 消込
		KeshikomiBuilder kb = new KeshikomiBuilder();
		kb.withDate(new EigyoDate(2017, 1, 1));
		kb.withKingaku(1000);
		kb.withNyukin(nyukin);
		kb.withUrikake(urikake);
		Keshikomi keshikomi = kb.build();
		// do
		service.save(keshikomi);
		// check
		TKeshikomi actual = repo.getOne(keshikomi.getRecordId());
		assertNotNull(actual);
	}

	/**
	 * 更新
	 */
	@Test
	public void test02_save() throws Exception {
		// database
		super.deleteAndInsert(T_KESHIKOMI, T_KESHIKOMI_COLUMN, T_KESHIKOMI_01);
		// input
		// 入金
		NyukinBuilder nb = new NyukinBuilder();
		nb.withRecordId("r-001");
		Nyukin nyukin = nb.build();
		// 売掛
		UrikakeBuilder ub = new UrikakeBuilder();
		ub.withRecordId("r-001");
		Urikake urikake = ub.build();
		// 消込
		KeshikomiBuilder kb = new KeshikomiBuilder();
		kb.withDate(new EigyoDate(2017, 1, 1));
		kb.withKingaku(1001);
		kb.withNyukin(nyukin);
		kb.withUrikake(urikake);
		kb.withRecordId("r-001");
		kb.withVersion(0);
		Keshikomi keshikomi = kb.build();
		// do
		service.save(keshikomi);
		// check
		TKeshikomi actual = repo.getOne(keshikomi.getRecordId());
		assertEquals(1001, actual.getKingaku().intValue());
	}

	/**
	 * override.
	 * 
	 * <pre>
	 *  空コレクションを渡したら何もしない。
	 * </pre>
	 */
	@Test
	public void test01_override() throws Exception {
		// do
		service.override(null, null, new ArrayList<Keshikomi>());
		// check : done nothing!
		assertTrue(true);
	}

	/**
	 * override.
	 * 
	 * <pre>
	 * 引数に無い消込は削除される。
	 * 引数にある消込は保存される。
	 * </pre>
	 */
	@Test
	public void test02_override() throws Exception {
		// database
		super.deleteAndInsert(T_KESHIKOMI, T_KESHIKOMI_COLUMN, T_KESHIKOMI_01, T_KESHIKOMI_02);
		// input
		// 入金
		NyukinBuilder nb = new NyukinBuilder();
		nb.withRecordId("r-001");
		Nyukin nyukin = nb.build();
		// 売掛
		UrikakeBuilder ub = new UrikakeBuilder();
		ub.withRecordId("r-002");
		Urikake urikake = ub.build();
		// 消込
		KeshikomiBuilder kb = new KeshikomiBuilder();
		kb.withDate(new EigyoDate(2017, 1, 1));
		kb.withKingaku(2001);
		kb.withNyukin(nyukin);
		kb.withUrikake(urikake);
		kb.withRecordId("r-002");
		kb.withVersion(0);
		Keshikomi keshikomi = kb.build();
		// 消込リスト
		Set<Keshikomi> keshikomiSet = new HashSet<Keshikomi>();
		keshikomiSet.add(keshikomi);
		// do
		service.override("r-001", new EigyoDate(2017, 1, 1), keshikomiSet);
		// check saved
		TKeshikomi actual = repo.getOne(keshikomi.getRecordId());
		assertEquals(2001, actual.getKingaku().intValue());
		// check deleted
		boolean exists = repo.existsById("r-001");
		assertFalse(exists);
	}

	@Test
	public void test01_getKeshikomiSetOfNyukin() throws Exception {
		// database
		super.deleteAndInsert(T_KESHIKOMI, T_KESHIKOMI_COLUMN, T_KESHIKOMI_01, T_KESHIKOMI_02);
		// do
		Set<Keshikomi> actual = service.getKeshikomiSetOfNyukin("r-001");
		assertEquals(2, actual.size());
	}

	@Test
	public void test01_getKeshikomiSetOfUrikake() throws Exception {
		// database
		super.deleteAndInsert(T_KESHIKOMI, T_KESHIKOMI_COLUMN, T_KESHIKOMI_01, T_KESHIKOMI_02);
		// do
		Set<Keshikomi> actual = service.getKeshikomiSetOfUrikake("r-001");
		assertEquals(1, actual.size());
	}

	@Test
	public void test01_delete() throws Exception {
		// database
		super.deleteAndInsert(T_KESHIKOMI, T_KESHIKOMI_COLUMN, T_KESHIKOMI_01);
		// do
		service.delete("r-001", 0);
		// check
		boolean actual = repo.existsById("r-001");
		assertFalse(actual);
	}

	@Test
	public void test01_getDomain() throws Exception {
		// database
		super.deleteAndInsert(T_KESHIKOMI, T_KESHIKOMI_COLUMN, T_KESHIKOMI_01);
		// do
		Keshikomi actual = service.getDomain("r-001");
		// check
		assertEquals(1000, actual.getKingaku().intValue());
	}

	@Test
	public void test01_exists() throws Exception {
		// database
		super.deleteAndInsert(T_KESHIKOMI, T_KESHIKOMI_COLUMN, T_KESHIKOMI_01);
		// do
		boolean actual = service.exsists("r-001");
		// check
		assertTrue(actual);
	}
}