package com.showka.service.crud.u08;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.FurikomiIrainin;
import com.showka.domain.FurikomiIraininSet;
import com.showka.domain.Kokyaku;
import com.showka.domain.Seikyu;
import com.showka.domain.builder.KokyakuBuilder;
import com.showka.repository.i.WFirmBankFuriwakeRepository;
import com.showka.service.search.u01.i.FurikomiIraininSearchService;
import com.showka.service.specification.u06.i.UrikakeKeshikomiSpecificationService;

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
public class FirmBankFuriwakeCrudServiceImplTest extends SimpleTestCase {

	@Tested
	@Injectable
	private FirmBankFuriwakeCrudServiceImpl service;

	@Injectable
	private FurikomiIraininSearchService furikomiIraininSearchService;

	@Injectable
	private UrikakeKeshikomiSpecificationService urikakeKeshikomiSpecificationService;

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
				furikomiIraininSearchService.search(kokyaku);
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
				furikomiIraininSearchService.search(kokyaku);
				times = 1;
				service.save(seikyu, f);
				times = 1;
			}
		};
	}
}
