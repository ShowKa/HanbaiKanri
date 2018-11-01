package com.showka.service.crud.u07;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.builder.BushoBuilder;
import com.showka.domain.builder.KokyakuBuilder;
import com.showka.domain.builder.SeikyuBuilder;
import com.showka.domain.builder.UrikakeBuilder;
import com.showka.domain.u01.Kokyaku;
import com.showka.domain.u06.Urikake;
import com.showka.domain.u07.Seikyu;
import com.showka.domain.z00.Busho;
import com.showka.repository.i.JSeikyuUrikakeRepository;
import com.showka.service.crud.u06.i.UrikakeCrudService;
import com.showka.service.crud.u07.i.SeikyuCrudService;
import com.showka.service.search.u01.i.NyukinKakeInfoSearchService;
import com.showka.service.search.u06.i.UrikakeSearchService;
import com.showka.service.search.u07.i.SeikyuSearchService;
import com.showka.service.specification.u07.SeikyuUrikakeSpecificationFactory;
import com.showka.service.specification.u07.i.SeikyuSpecification;
import com.showka.value.EigyoDate;
import com.showka.value.ShimeDate;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

public class SeikyuUrikakeCrudServiceImplTest extends SimpleTestCase {

	@Tested
	@Injectable
	private SeikyuUrikakeCrudServiceImpl service;

	@Injectable
	private NyukinKakeInfoSearchService nyukinKakeInfoSearchService;

	@Injectable
	private UrikakeSearchService urikakeSearchService;

	@Injectable
	private UrikakeCrudService urikakeCrudService;

	@Injectable
	private SeikyuCrudService seikyuCrudService;

	@Injectable
	private SeikyuUrikakeSpecificationFactory seikyuUrikakeSpecificationFactory;

	@Injectable
	private SeikyuSearchService seikyuSearchService;

	@Injectable
	private JSeikyuUrikakeRepository repo;

	/**
	 * 請求.
	 * 
	 * <pre>
	 * 入力：部署=BS01, 締日=2017/01/01
	 * 条件：なし
	 * 結果：顧客への請求処理が呼び出される。
	 * </pre>
	 * 
	 */
	@Test
	public void test01_seikyuBushoEigyoDate() throws Exception {
		// input
		// 部署
		BushoBuilder bb = new BushoBuilder();
		bb.withCode("BS01");
		Busho busho = bb.build();
		// 締日
		EigyoDate eigyoDate = new EigyoDate(2017, 1, 1);
		ShimeDate shimeDate = new ShimeDate(1);
		// 顧客リスト
		KokyakuBuilder kb = new KokyakuBuilder();
		kb.withCode("KK01");
		Kokyaku kokyaku = kb.build();
		List<Kokyaku> kokyakuList = new ArrayList<Kokyaku>();
		kokyakuList.add(kokyaku);
		// expect
		new Expectations() {
			{
				// 締日の顧客リスト取得
				nyukinKakeInfoSearchService.getKokyakuOnShimeDate(busho, shimeDate);
				result = kokyakuList;
				// 内部呼出
				service.seikyu(kokyaku, eigyoDate);
				times = 1;
			}
		};
		// do
		service.seikyu(busho, eigyoDate);
		// verify
		new Verifications() {
			{
				// 締日の顧客リスト取得
				nyukinKakeInfoSearchService.getKokyakuOnShimeDate(busho, shimeDate);
				times = 1;
			}
		};
	}

	/**
	 * 請求.
	 * 
	 * <pre>
	 * 入力：顧客=KK01, 締日=2017/01/01
	 * 条件：なし
	 * 結果：顧客への売掛請求処理が呼び出される。
	 * </pre>
	 * 
	 */
	@Test
	public void test02_seikyuKokyakuEigyoDate() throws Exception {
		// input
		// 締日
		EigyoDate shimeDate = new EigyoDate(2017, 1, 1);
		// 顧客リスト
		String kokyakuId = "r-KK01";
		KokyakuBuilder kb = new KokyakuBuilder();
		kb.withCode("KK01");
		kb.withRecordId(kokyakuId);
		Kokyaku kokyaku = kb.build();
		// 売掛リスト
		List<Urikake> urikakeList = new ArrayList<Urikake>();
		// expect
		new Expectations() {
			{
				// 売掛リスト
				urikakeSearchService.getUrikakeForSeikyu(kokyakuId);
				result = urikakeList;
				// 請求
				service.seikyu(kokyaku, shimeDate, urikakeList);
				times = 1;
			}
		};
		// do
		service.seikyu(kokyaku, shimeDate);
		// verify
		new Verifications() {
			{
				// 売掛リスト
				urikakeSearchService.getUrikakeForSeikyu(kokyakuId);
				times = 1;
			}
		};
	}

	/**
	 * 請求.
	 * 
	 * <pre>
	 * 入力：顧客=KK01, 締日=2017/01/01, 売掛
	 * 条件：なし
	 * 結果：請求CRUD処理が呼び出され、かつ売掛の入金予定日更新処理が呼び出される。
	 * </pre>
	 * 
	 */
	@Test
	public void test03_seikyuKokyakuEigyoDate(@Injectable SeikyuSpecification spec) throws Exception {
		// input
		// 締日
		EigyoDate shimeDate = new EigyoDate(2017, 1, 1);
		// 顧客リスト
		String kokyakuId = "r-KK01";
		KokyakuBuilder kb = new KokyakuBuilder();
		kb.withCode("KK01");
		kb.withRecordId(kokyakuId);
		Kokyaku kokyaku = kb.build();
		// 売掛リスト
		List<Urikake> urikakeList = new ArrayList<Urikake>();
		Urikake urikake = new UrikakeBuilder().build();
		urikakeList.add(urikake);
		// 支払日
		EigyoDate shiharaiDate = new EigyoDate(2017, 2, 1);
		// expect
		new Expectations() {
			{
				// 請求仕様
				seikyuUrikakeSpecificationFactory.create(kokyaku, shimeDate, urikakeList);
				// save
				seikyuCrudService.save(spec);
				// 支払日
				spec.getShiharaiDate();
				result = shiharaiDate;
				// 売掛の入金予定日更新
				urikakeCrudService.updateNyukinYoteiDate(urikake, shiharaiDate);
				// 売掛の最新請求を登録
				service.save((String) any, urikake.getRecordId());
				times = 1;
			}
		};
		// do
		service.seikyu(kokyaku, shimeDate, urikakeList);
		// verify
		new Verifications() {
			{
				// 請求仕様
				seikyuUrikakeSpecificationFactory.create(kokyaku, shimeDate, urikakeList);
				times = 1;
				// save
				seikyuCrudService.save(spec);
				times = 1;
				// 支払日
				spec.getShiharaiDate();
				times = 1;
				// 売掛の入金予定日更新
				urikakeCrudService.updateNyukinYoteiDate(urikake, shiharaiDate);
				times = 1;
			}
		};
	}

	/**
	 * 削除.
	 * 
	 * <pre>
	 * レコードが既存の場合削除される。
	 * </pre>
	 */
	@Test
	public void test04_deleteIfExists() {
		// input
		String urikakeId = "r-001";
		// expect
		new Expectations() {
			{
				repo.existsById(urikakeId);
				result = true;
			}
		};
		// do
		service.deleteIfExists(urikakeId);
		// verify
		new Verifications() {
			{
				repo.existsById(urikakeId);
				times = 1;
				repo.deleteById(urikakeId);
				times = 1;
			}
		};
	}

	/**
	 * 削除.
	 * 
	 * <pre>
	 * レコードがない場合処理終了。
	 * </pre>
	 */
	@Test
	public void test05_deleteIfExists() {
		// input
		String urikakeId = "r-001";
		// expect
		new Expectations() {
			{
				repo.existsById(urikakeId);
				result = false;
			}
		};
		// do
		service.deleteIfExists(urikakeId);
		// verify
		new Verifications() {
			{
				repo.existsById(urikakeId);
				times = 1;
				repo.deleteById(urikakeId);
				times = 0;
			}
		};
	}

	/**
	 * レコード既存の場合、復活処理は途中終了する。
	 */
	@Test
	public void test06_Revert() throws Exception {
		// input
		String urikakeId = "r-001";
		// expect
		new Expectations() {
			{
				repo.existsById(urikakeId);
				result = true;
			}
		};
		service.revert(urikakeId);
		// verify
		new Verifications() {
			{
				repo.existsById(urikakeId);
				times = 1;
				service.save(anyString, urikakeId);
				times = 0;
			}
		};
	}

	@Test
	public void test07_Revert() throws Exception {
		// input
		String urikakeId = "r-001";
		// expect
		new Expectations() {
			{
				repo.existsById(urikakeId);
				result = false;
				seikyuSearchService.getNewestOf(urikakeId);
				result = Optional.empty();
			}
		};
		service.revert(urikakeId);
		// verify
		new Verifications() {
			{
				repo.existsById(urikakeId);
				times = 1;
				seikyuSearchService.getNewestOf(urikakeId);
				times = 1;
				service.save(anyString, urikakeId);
				times = 0;
			}
		};
	}

	@Test
	public void test08_Revert() throws Exception {
		// input
		String urikakeId = "r-001";
		// mock
		SeikyuBuilder sb = new SeikyuBuilder();
		String seikyuId = "r-KK01-20170820";
		sb.withRecordId(seikyuId);
		Seikyu seikyu = sb.build();
		// expect
		new Expectations() {
			{
				repo.existsById(urikakeId);
				result = false;
				seikyuSearchService.getNewestOf(urikakeId);
				result = Optional.of(seikyu);
				service.save(seikyuId, urikakeId);
			}
		};
		service.revert(urikakeId);
		// verify
		new Verifications() {
			{
				repo.existsById(urikakeId);
				times = 1;
				seikyuSearchService.getNewestOf(urikakeId);
				times = 1;
				service.save(seikyuId, urikakeId);
				times = 1;
			}
		};
	}
}
