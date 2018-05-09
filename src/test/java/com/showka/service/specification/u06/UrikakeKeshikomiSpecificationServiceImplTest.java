package com.showka.service.specification.u06;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.Keshikomi;
import com.showka.domain.Urikake;
import com.showka.domain.UrikakeKeshikomi;
import com.showka.domain.builder.KeshikomiBuilder;
import com.showka.domain.builder.UrikakeBuilder;
import com.showka.domain.builder.UrikakeKeshikomiBuilder;
import com.showka.service.crud.u06.i.UrikakeKeshikomiCrudService;
import com.showka.value.AmountOfMoney;
import com.showka.value.TheTimestamp;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

public class UrikakeKeshikomiSpecificationServiceImplTest extends SimpleTestCase {

	@Tested
	private UrikakeKeshikomiSpecificationServiceImpl service;

	@Injectable
	private UrikakeKeshikomiCrudService urikakeKeshikomiCrudService;

	/**
	 * 残高取得.
	 * 
	 * <pre>
	 * 入力： 売掛1 
	 * 条件： 売掛1 = 600円, 消込1 = 100円
	 * 結果： 残高 = 500円
	 * </pre>
	 * 
	 */
	@Test
	public void test01_getZandakaOf() throws Exception {
		// input
		// 売掛ID
		String urikakeId = "r-KK01-00001-20170101";
		// 売掛
		UrikakeBuilder ub = new UrikakeBuilder();
		ub.withKingaku(600);
		ub.withRecordId(urikakeId);
		Urikake urikake = ub.build();
		// 消込1
		KeshikomiBuilder kb1 = new KeshikomiBuilder();
		kb1.withKingaku(100);
		kb1.withRecordId("r-001");
		kb1.withTimestamp(new TheTimestamp(2017, 1, 1));
		Keshikomi keshikomi1 = kb1.build();
		// 消込set
		Set<Keshikomi> keshikomiSet = new HashSet<Keshikomi>();
		keshikomiSet.add(keshikomi1);
		// 売掛消込
		UrikakeKeshikomiBuilder ukb = new UrikakeKeshikomiBuilder();
		ukb.withUrikake(urikake);
		ukb.withKeshikomiSet(keshikomiSet);
		UrikakeKeshikomi urikakeKeshikomi = ukb.build();
		// expect
		new Expectations() {
			{
				urikakeKeshikomiCrudService.getDomain(urikakeId);
				result = urikakeKeshikomi;
			}
		};
		// do
		AmountOfMoney actual = service.getZandakaOf(urikake);
		// verify
		new Verifications() {
			{
				urikakeKeshikomiCrudService.getDomain(urikakeId);
				times = 1;
			}
		};
		// check
		assertEquals(500, actual.intValue());
	}

	/**
	 * 消込時点残高取得.
	 * 
	 * <pre>
	 * 入力： 売掛1 , 消込2 
	 * 条件：売掛1 = 600円, 消込1 = 100円,  消込2 = 200円, 消込3 = 300円
	 * 結果： 残高 = 500円
	 * </pre>
	 * 
	 */
	@Test
	public void test01_getZandakaAsOfKeshikomi() throws Exception {
		// input
		// 売掛ID
		String urikakeId = "r-KK01-00001-20170101";
		// 売掛
		UrikakeBuilder ub = new UrikakeBuilder();
		ub.withKingaku(600);
		ub.withRecordId(urikakeId);
		Urikake urikake = ub.build();
		// 消込1
		KeshikomiBuilder kb1 = new KeshikomiBuilder();
		kb1.withKingaku(100);
		kb1.withRecordId("r-001");
		kb1.withTimestamp(new TheTimestamp(2017, 1, 1));
		Keshikomi keshikomi1 = kb1.build();
		// 消込2
		KeshikomiBuilder kb2 = new KeshikomiBuilder();
		kb2.withKingaku(200);
		kb2.withRecordId("r-002");
		kb2.withTimestamp(new TheTimestamp(2017, 2, 2));
		Keshikomi keshikomi2 = kb2.build();
		// 消込3
		KeshikomiBuilder kb3 = new KeshikomiBuilder();
		kb3.withKingaku(300);
		kb3.withRecordId("r-003");
		kb3.withTimestamp(new TheTimestamp(2017, 3, 3));
		Keshikomi keshikomi3 = kb3.build();
		// 消込set
		Set<Keshikomi> keshikomiSet = new HashSet<Keshikomi>();
		keshikomiSet.add(keshikomi1);
		keshikomiSet.add(keshikomi2);
		keshikomiSet.add(keshikomi3);
		// 売掛消込
		UrikakeKeshikomiBuilder ukb = new UrikakeKeshikomiBuilder();
		ukb.withUrikake(urikake);
		ukb.withKeshikomiSet(keshikomiSet);
		UrikakeKeshikomi urikakeKeshikomi = ukb.build();
		// expect
		new Expectations() {
			{
				urikakeKeshikomiCrudService.getDomain(urikakeId);
				result = urikakeKeshikomi;
			}
		};
		// do
		AmountOfMoney actual = service.getZandakaAsOfKeshikomi(urikake, keshikomi2);
		// verify
		new Verifications() {
			{
				urikakeKeshikomiCrudService.getDomain(urikakeId);
				times = 1;
			}
		};
		// check
		assertEquals(500, actual.intValue());
	}
}
