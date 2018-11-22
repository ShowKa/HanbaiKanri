package com.showka.service.persistence.u08;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.builder.KokyakuBuilder;
import com.showka.domain.u01.FurikomiIrainin;
import com.showka.domain.u01.FurikomiIraininSet;
import com.showka.domain.u01.Kokyaku;
import com.showka.domain.u07.Seikyu;
import com.showka.repository.i.WFirmBankFuriwakeRepository;
import com.showka.service.persistence.u06.i.UrikakeKeshikomiPersistence;
import com.showka.service.query.u01.i.FurikomiIraininQuery;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import mockit.Verifications;

/**
 * Simple Test Case
 * 
 * <pre>
 * Test2もあるので注意
 * </pre>
 * 
 */
public class FirmBankFuriwakePersistenceImplTest extends SimpleTestCase {

	@Tested
	@Injectable
	private FirmBankFuriwakePersistenceImpl service;

	@Injectable
	private FurikomiIraininQuery furikomiIraininQuery;

	@Injectable
	private UrikakeKeshikomiPersistence urikakeKeshikomiPersistence;

	@Injectable
	private WFirmBankFuriwakeRepository repo;

	/**
	 * deleteAll
	 * 
	 * <pre>
	 * Repository呼び出し確認だけ。
	 * </pre>
	 * 
	 */
	@Test
	public void test_DeleteAll_01() throws Exception {
		// expect
		new Expectations() {
			{
				repo.deleteAll();
			}
		};
		// do
		service.deleteAll();
		// verify
		new Verifications() {
			{
				repo.deleteAll();
				times = 1;
			}
		};
	}

	/**
	 * save.
	 * 
	 * <pre>
	 * 内部関数呼び出しだけ確認。
	 * </pre>
	 * 
	 */
	@Test
	public void test_saveList_01(@Mocked Seikyu seikyu) throws Exception {
		// data
		List<Seikyu> seikyuList = Arrays.asList(seikyu);
		// do
		service.save(seikyuList);
		// verify
		new Verifications() {
			{
				service.save(seikyu);
				times = 1;
			}
		};
	}

	/**
	 * save.
	 * 
	 * <pre>
	 * 内部関数呼び出しだけ確認。
	 * </pre>
	 * 
	 */
	@Test
	public void test_save_01(@Mocked Seikyu seikyu, @Mocked FurikomiIrainin f) throws Exception {
		// data
		// 顧客
		KokyakuBuilder kb = new KokyakuBuilder();
		Kokyaku kokyaku = kb.build();
		// 振込依頼人Set
		Set<FurikomiIrainin> _set = new HashSet<FurikomiIrainin>(Arrays.asList(f));
		FurikomiIraininSet set = new FurikomiIraininSet(kokyaku, _set);
		// expect
		new Expectations() {
			{
				seikyu.getKokyaku();
				result = kokyaku;
				furikomiIraininQuery.get(kokyaku);
				result = set;
			}
		};
		// do
		service.save(seikyu);
		// verify
		new Verifications() {
			{
				seikyu.getKokyaku();
				times = 1;
				furikomiIraininQuery.get(kokyaku);
				times = 1;
				service.save(seikyu, f);
				times = 1;
			}
		};
	}
}
