package com.showka.service.persistence.u07;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.builder.BushoBuilder;
import com.showka.domain.builder.KokyakuBuilder;
import com.showka.domain.u01.Kokyaku;
import com.showka.domain.u06.Urikake;
import com.showka.domain.z00.Busho;
import com.showka.service.persistence.u07.SeikyuUrikakePersistenceImpl;
import com.showka.service.persistence.u07.i.SeikyuPersistence;
import com.showka.service.query.u01.i.KokyakuQuery;
import com.showka.service.query.u06.i.UrikakeQuery;
import com.showka.service.query.u07.i.ShimeDateQuery;
import com.showka.service.specification.u07.SeikyuUrikakeSpecificationFactory;
import com.showka.service.specification.u07.i.SeikyuSpecification;
import com.showka.value.EigyoDate;
import com.showka.value.ShimeDate;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

public class SeikyuUrikakePersistenceImplTest extends SimpleTestCase {

	@Tested
	@Injectable
	private SeikyuUrikakePersistenceImpl service;

	@Injectable
	private KokyakuQuery nyukinKakeInfoQuery;

	@Injectable
	private UrikakeQuery urikakeQuery;

	@Injectable
	private SeikyuPersistence seikyuPersistence;

	@Injectable
	private SeikyuUrikakeSpecificationFactory seikyuUrikakeSpecificationFactory;

	@Injectable
	private ShimeDateQuery shimeDateBusinessService;

	@Test
	public void test01_seikyuBushoEigyoDate() throws Exception {
		// input
		// 部署
		BushoBuilder bb = new BushoBuilder();
		bb.withCode("BS01");
		Busho busho = bb.build();
		// 営業日
		EigyoDate eigyoDate = new EigyoDate(2017, 1, 1);
		// 締日
		ShimeDate shimeDate = new ShimeDate(1);
		Set<ShimeDate> shimeDates = new HashSet<>();
		shimeDates.add(shimeDate);
		// 顧客リスト
		KokyakuBuilder kb = new KokyakuBuilder();
		kb.withCode("KK01");
		Kokyaku kokyaku = kb.build();
		List<Kokyaku> kokyakuList = Arrays.asList(kokyaku);
		// expect
		new Expectations() {
			{
				// 締日取得
				shimeDateBusinessService.get(busho, eigyoDate);
				result = shimeDates;
				// 締日の顧客リスト取得
				nyukinKakeInfoQuery.getOnShimeDate(busho, shimeDates);
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
				// 締日取得
				shimeDateBusinessService.get(busho, eigyoDate);
				times = 1;
				// 締日の顧客リスト取得
				nyukinKakeInfoQuery.getOnShimeDate(busho, shimeDates);
				times = 1;
			}
		};
	}

	// 顧客への売掛請求処理が呼び出され、overload methodに処理が委託されること。
	@Test
	public void test02_seikyuKokyakuEigyoDate() throws Exception {
		// input
		// 締日
		EigyoDate shimeDate = new EigyoDate(2017, 1, 1);
		// 顧客リスト
		KokyakuBuilder kb = new KokyakuBuilder();
		kb.withCode("KK01");
		Kokyaku kokyaku = kb.build();
		// 売掛リスト
		List<Urikake> urikakeList = new ArrayList<Urikake>();
		// expect
		new Expectations() {
			{
				// 売掛リスト
				urikakeQuery.getForSeikyu(kokyaku);
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
				urikakeQuery.getForSeikyu(kokyaku);
				times = 1;
			}
		};
	}

	// 請求仕様が生成され、#save 処理に委託されること。
	@Test
	public void test03_seikyuKokyakuEigyoDate(@Injectable SeikyuSpecification spec) throws Exception {
		// input
		// 締日
		EigyoDate shimeDate = new EigyoDate(2017, 1, 1);
		// 顧客
		KokyakuBuilder kb = new KokyakuBuilder();
		kb.withCode("KK01");
		Kokyaku kokyaku = kb.build();
		// 売掛リスト
		List<Urikake> urikakeList = new ArrayList<Urikake>();
		// expect
		new Expectations() {
			{
				// 請求仕様
				seikyuUrikakeSpecificationFactory.create(kokyaku, shimeDate, urikakeList);
				// save
				seikyuPersistence.save(spec);
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
				seikyuPersistence.save(spec);
				times = 1;
			}
		};
	}

}
