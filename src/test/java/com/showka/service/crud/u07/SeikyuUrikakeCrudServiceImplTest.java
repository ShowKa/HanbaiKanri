package com.showka.service.crud.u07;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.Busho;
import com.showka.domain.Kokyaku;
import com.showka.domain.SeikyuMeisai;
import com.showka.domain.Urikake;
import com.showka.domain.builder.BushoBuilder;
import com.showka.domain.builder.KokyakuBuilder;
import com.showka.domain.builder.SeikyuMeisaiBuilder;
import com.showka.domain.builder.UrikakeBuilder;
import com.showka.service.crud.u05.i.UrikakeCrudService;
import com.showka.service.crud.u07.i.SeikyuCrudService;
import com.showka.service.search.u01.i.NyukinKakeInfoSearchService;
import com.showka.service.search.u05.i.UrikakeSearchService;
import com.showka.service.specification.u07.SeikyuUrikakeSpecificationFactory;
import com.showka.service.specification.u07.i.SeikyuSpecification;
import com.showka.value.EigyoDate;

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
		EigyoDate shimeDate = new EigyoDate(2017, 1, 1);
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
				service.seikyu(kokyaku, shimeDate);
				times = 1;
			}
		};
		// do
		service.seikyu(busho, shimeDate);
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
				urikakeSearchService.getUrikakeOfKokyaku(kokyakuId);
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
				urikakeSearchService.getUrikakeOfKokyaku(kokyakuId);
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
		// 請求明細
		List<SeikyuMeisai> meisai = new ArrayList<SeikyuMeisai>();
		SeikyuMeisaiBuilder smb = new SeikyuMeisaiBuilder();
		Urikake urikake = new UrikakeBuilder().build();
		smb.withUrikake(urikake);
		SeikyuMeisai m = smb.build();
		meisai.add(m);
		// 支払日
		EigyoDate shiharaiDate = new EigyoDate(2017, 2, 1);
		// expect
		new Expectations() {
			{
				// 請求仕様
				seikyuUrikakeSpecificationFactory.create(kokyaku, shimeDate, urikakeList);
				// save
				seikyuCrudService.save(spec);
				// 請求明細
				spec.getSeikyuMeisai();
				result = meisai;
				// 支払日
				spec.getShiharaiDate();
				result = shiharaiDate;
				// 売掛の入金予定日更新
				urikakeCrudService.updateNyukinYoteiDate(urikake, shiharaiDate);
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
				// 請求明細
				spec.getSeikyuMeisai();
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
}
