package com.showka.service.persistence.u08;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.PersistenceTestCase;
import com.showka.domain.builder.KeshikomiBuilder;
import com.showka.domain.builder.NyukinBuilder;
import com.showka.domain.builder.UrikakeBuilder;
import com.showka.domain.u06.Urikake;
import com.showka.domain.u08.Keshikomi;
import com.showka.domain.u08.Nyukin;
import com.showka.repository.i.CKeshikomiRepository;
import com.showka.repository.i.TKeshikomiRepository;
import com.showka.service.crud.u08.i.KeshikomiCrud;
import com.showka.value.EigyoDate;
import com.showka.value.TheTimestamp;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

public class KeshikomiPersistenceImplTest extends PersistenceTestCase {

	@Tested
	@Injectable
	private KeshikomiPersistenceImpl service;

	@Injectable
	@Autowired
	private TKeshikomiRepository repo;

	@Injectable
	@Autowired
	private CKeshikomiRepository cancelRepo;

	@Injectable
	private KeshikomiCrud keshikomiCrud;

	private static final Object[] T_KESHIKOMI_01 = { "r-001", d("20170101"), 1000, "r-001", "r-KK01-00001", "r-001" };
	private static final Object[] T_KESHIKOMI_02 = { "r-002", d("20170101"), 2000, "r-001", "r-KK01-00002", "r-002" };

	/**
	 * override.
	 * 
	 * <pre>
	 * 引数に無い消込は削除される。
	 * 引数にある消込は保存される。
	 * </pre>
	 */
	@Test
	public void test01_override() throws Exception {
		// input
		String nyukinId = "r-001";
		// 消込
		KeshikomiBuilder kb = new KeshikomiBuilder();
		kb.withDate(new EigyoDate(2017, 1, 1));
		kb.withRecordId("r-001");
		Keshikomi keshikomi1 = kb.build();
		// 消込リスト
		Set<Keshikomi> keshikomiSet = new HashSet<Keshikomi>();
		keshikomiSet.add(keshikomi1);
		// 削除対象消込
		KeshikomiBuilder kb2 = new KeshikomiBuilder();
		kb2.withDate(new EigyoDate(2017, 1, 1));
		kb2.withRecordId("r-002");
		Keshikomi keshikomi2 = kb2.build();
		// expect
		new Expectations() {
			{
				service.getKeshikomiSetOfNyukin(nyukinId);
				result = keshikomi2;
				keshikomiCrud.delete(keshikomi2);
				keshikomiCrud.save(keshikomi1);
			}
		};
		// do
		service.override("r-001", new EigyoDate(2017, 1, 1), keshikomiSet);
		// verify
		new Verifications() {
			{
				service.getKeshikomiSetOfNyukin(nyukinId);
				times = 1;
				keshikomiCrud.delete(keshikomi2);
				times = 1;
				keshikomiCrud.save(keshikomi1);
				times = 1;
			}
		};
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
		Set<Keshikomi> actual = service.getKeshikomiSetOfUrikake("r-KK01-00001");
		assertEquals(1, actual.size());
	}

	@Test
	public void test01_cancel() throws Exception {
		// mock
		// 入金
		Nyukin n = new NyukinBuilder().build();
		n.setRecordId("r-001");
		// 売掛
		Urikake u = new UrikakeBuilder().build();
		u.setRecordId("r-001");
		// 消込
		KeshikomiBuilder kb = new KeshikomiBuilder();
		kb.withDate(new EigyoDate(2017, 1, 1));
		kb.withRecordId("r-001");
		kb.withKingaku(1000);
		kb.withTimestamp(new TheTimestamp());
		kb.withNyukin(n);
		kb.withUrikake(u);
		Keshikomi keshikomi = kb.build();
		// expect
		new Expectations() {
			{
				keshikomiCrud.getDomain("r-001");
				result = keshikomi;
				keshikomiCrud.delete(keshikomi);
			}
		};
		// do
		service.cancel("r-001", new EigyoDate(2099, 9, 9));
		// verify
		new Verifications() {
			{
				keshikomiCrud.getDomain("r-001");
				times = 1;
				keshikomiCrud.delete((Keshikomi) any);
				times = 1;
			}
		};
		// check
		boolean cancel = cancelRepo.existsById("r-001");
		assertTrue(cancel);
	}
}
