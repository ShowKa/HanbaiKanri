package com.showka.service.crud.u08;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.PersistenceTestCase;
import com.showka.domain.builder.KeshikomiBuilder;
import com.showka.domain.builder.NyukinBuilder;
import com.showka.domain.builder.UrikakeBuilder;
import com.showka.domain.u06.Urikake;
import com.showka.domain.u08.Keshikomi;
import com.showka.domain.u08.Nyukin;
import com.showka.entity.TKeshikomi;
import com.showka.repository.i.TKeshikomiRepository;
import com.showka.service.crud.u06.i.UrikakeCrud;
import com.showka.service.crud.u08.i.NyukinCrud;
import com.showka.value.EigyoDate;
import com.showka.value.TheTimestamp;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

public class KeshikomiCrudImplTest extends PersistenceTestCase {

	@Tested
	@Injectable
	private KeshikomiCrudImpl service;

	@Autowired
	@Injectable
	private TKeshikomiRepository repo;

	@Injectable
	private NyukinCrud nyukinCrud;

	@Injectable
	private UrikakeCrud urikakeCrud;

	// 消込
	private static final Object[] T_KESHIKOMI_01 = { "r-001", d("20170101"), 1000, "r-001", "r-KK01-00001", "r-001" };

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
		String urikakeId = "r-KK01-00001";
		ub.withRecordId(urikakeId);
		Urikake urikake = ub.build();
		// 消込
		KeshikomiBuilder kb = new KeshikomiBuilder();
		kb.withDate(new EigyoDate(2017, 1, 1));
		kb.withTimestamp(new TheTimestamp());
		kb.withKingaku(1000);
		kb.withNyukin(nyukin);
		kb.withUrikake(urikake);
		Keshikomi keshikomi = kb.build();
		// do
		service.save(keshikomi);
		// check
		TKeshikomi actual = repo.getOne(keshikomi.getRecordId());
		assertNotNull(actual);
		assertEquals(1000, actual.getKingaku().intValue());
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
		ub.withRecordId("r-KK01-00001");
		Urikake urikake = ub.build();
		// 消込
		KeshikomiBuilder kb = new KeshikomiBuilder();
		kb.withDate(new EigyoDate(2017, 1, 1));
		kb.withTimestamp(new TheTimestamp());
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

	@Test
	public void test01_delete() throws Exception {
		// database
		super.deleteAndInsert(T_KESHIKOMI, T_KESHIKOMI_COLUMN, T_KESHIKOMI_01);
		// input
		KeshikomiBuilder kb = new KeshikomiBuilder();
		kb.withRecordId("r-001");
		kb.withVersion(0);
		Keshikomi keshikomi = kb.build();
		// do
		service.delete(keshikomi);
		// check
		boolean actual = repo.existsById("r-001");
		assertFalse(actual);
	}

	@Test
	public void test01_getDomain() throws Exception {
		// database
		super.deleteAndInsert(T_KESHIKOMI, T_KESHIKOMI_COLUMN, T_KESHIKOMI_01);
		// mock
		Nyukin nyukin = new NyukinBuilder().build();
		Urikake urikake = new UrikakeBuilder().build();
		// expect
		new Expectations() {
			{
				nyukinCrud.getDomain("r-001");
				result = nyukin;
				urikakeCrud.getDomainById("r-KK01-00001");
				result = urikake;
			}
		};
		// do
		Keshikomi actual = service.getDomain("r-001");
		// verigy
		new Verifications() {
			{
				nyukinCrud.getDomain("r-001");
				times = 1;
				urikakeCrud.getDomainById("r-KK01-00001");
				times = 1;
			}
		};
		// check
		assertEquals(1000, actual.getKingaku().intValue());
		assertEquals(new EigyoDate(2017, 1, 1), actual.getDate());
	}

	@Test
	public void test01_exists() throws Exception {
		// database
		super.deleteAndInsert(T_KESHIKOMI, T_KESHIKOMI_COLUMN, T_KESHIKOMI_01);
		// do
		boolean actual = service.exists("r-001");
		// check
		assertTrue(actual);
	}
}
