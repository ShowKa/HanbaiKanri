package com.showka.service.specification.u07;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.Busho;
import com.showka.domain.Kokyaku;
import com.showka.domain.NyukinKakeInfo;
import com.showka.domain.Seikyu;
import com.showka.domain.Urikake;
import com.showka.domain.builder.KokyakuBuilder;
import com.showka.domain.builder.SeikyuBuilder;
import com.showka.service.crud.u05.i.UrikakeCrudService;
import com.showka.service.crud.u07.i.SeikyuCrudService;
import com.showka.service.search.u01.i.NyukinKakeInfoSearchService;
import com.showka.service.search.u05.i.UrikakeSearchService;
import com.showka.value.EigyoDate;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

public class SeikyuSpecificationImplTest extends SimpleTestCase {

	@Tested
	private SeikyuSpecificationImpl specification;

	@Injectable
	private NyukinKakeInfoSearchService nyukinKakeInfoSearchService;

	@Injectable
	private UrikakeSearchService urikakeSearchService;

	@Injectable
	private SeikyuCrudService seikyuCrudService;

	@Injectable
	private UrikakeCrudService urikakeCrudService;

	/**
	 * 請求.
	 * 
	 * <pre>
	 * 入力：部署=任意、締日=2016/1/1
	 * 条件：部署はKK01の主管部署である。
	 * 結果：顧客KK01の請求処理が呼び出される。
	 * </pre>
	 * 
	 */
	@Test
	public void test01_Seikyu(@Injectable Busho busho, @Injectable Urikake urikake,
			@Injectable NyukinKakeInfo nyukinKakeInfo) throws Exception {
		// input
		// 締日
		EigyoDate shimeDate = new EigyoDate(2016, 1, 1);
		// 顧客コード
		String kokyakuCode = "KK01";
		// 顧客リスト
		List<Kokyaku> kokyakuList = new ArrayList<Kokyaku>();
		KokyakuBuilder kb = new KokyakuBuilder();
		kb.withCode(kokyakuCode);
		kb.withNyukinKakeInfo(nyukinKakeInfo);
		Kokyaku kokyaku = kb.build();
		kokyakuList.add(kokyaku);
		// expect
		new Expectations() {
			{
				// 締日の顧客リスト
				nyukinKakeInfoSearchService.getKokyakuOnShimeDate(busho, shimeDate);
				result = kokyakuList;
				// 顧客へ請求
				specification.seikyu(kokyaku, shimeDate);
			}
		};
		// do
		specification.seikyu(busho, shimeDate);
		// verify
		new Verifications() {
			{
				// 締日の顧客リスト
				nyukinKakeInfoSearchService.getKokyakuOnShimeDate(busho, shimeDate);
				times = 1;
				// 顧客へ請求
				specification.seikyu(kokyaku, shimeDate);
				times = 1;
			}
		};
	}

	/**
	 * 請求.
	 * 
	 * <pre>
	 * 入力：顧客=KK01、締日=2016/1/1
	 * 条件：顧客には売掛がる。
	 * 結果：売掛の請求処理、および売掛の入金予定日更新処理が呼び出される。
	 * </pre>
	 * 
	 */
	@Test
	public void test02_Seikyu(@Injectable Busho busho, @Injectable Urikake urikake,
			@Injectable NyukinKakeInfo nyukinKakeInfo) throws Exception {
		// input
		// 締日
		EigyoDate shimeDate = new EigyoDate(2016, 1, 1);
		// 顧客コード
		String kokyakuCode = "KK01";
		// 顧客リスト
		List<Kokyaku> kokyakuList = new ArrayList<Kokyaku>();
		KokyakuBuilder kb = new KokyakuBuilder();
		kb.withCode(kokyakuCode);
		kb.withNyukinKakeInfo(nyukinKakeInfo);
		Kokyaku kokyaku = kb.build();
		kokyakuList.add(kokyaku);
		// 売掛リスト
		List<Urikake> urikakeList = new ArrayList<Urikake>();
		urikakeList.add(urikake);
		// 支払日
		EigyoDate shiharaiDate = new EigyoDate(2017, 1, 1);
		// 請求
		SeikyuBuilder sb = new SeikyuBuilder();
		sb.withKokyaku(kokyaku);
		sb.withSeikyuDate(shimeDate);
		Seikyu seikyu = sb.build();
		// expect
		new Expectations() {
			{
				// 売掛
				urikakeSearchService.getUrikakeOfKokyaku(kokyakuCode);
				result = urikakeList;
				// 請求明細
				urikake.getZandaka();
				result = Integer.valueOf(108);
				// 支払日
				nyukinKakeInfo.getNyukinYoteiDate(shimeDate);
				result = shiharaiDate;
				// save
				seikyuCrudService.save(seikyu);
				// 売掛の入金予定日更新
				urikakeCrudService.updateNyukinYoteiDate(urikake, shiharaiDate);
			}
		};
		// do
		specification.seikyu(kokyaku, shimeDate);
		// verify
		new Verifications() {
			{
				// 売掛
				urikakeSearchService.getUrikakeOfKokyaku(kokyakuCode);
				times = 1;
				// 請求明細
				urikake.getZandaka();
				times = 1;
				// 支払日
				nyukinKakeInfo.getNyukinYoteiDate(shimeDate);
				times = 1;
				// save
				seikyuCrudService.save(seikyu);
				times = 1;
				// 売掛の入金予定日更新
				urikakeCrudService.updateNyukinYoteiDate(urikake, shiharaiDate);
				times = 1;
			}
		};
	}
}
