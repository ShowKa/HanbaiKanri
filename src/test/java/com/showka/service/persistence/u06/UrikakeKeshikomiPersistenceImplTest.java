package com.showka.service.persistence.u06;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.builder.KeshikomiBuilder;
import com.showka.domain.builder.UrikakeBuilder;
import com.showka.domain.builder.UrikakeKeshikomiBuilder;
import com.showka.domain.u06.Urikake;
import com.showka.domain.u06.UrikakeKeshikomi;
import com.showka.domain.u08.Keshikomi;
import com.showka.service.persistence.u06.i.UrikakePersistence;
import com.showka.service.persistence.u08.i.KeshikomiPersistence;
import com.showka.value.AmountOfMoney;
import com.showka.value.TheTimestamp;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;

public class UrikakeKeshikomiPersistenceImplTest extends SimpleTestCase {

	@Tested
	@Injectable
	private UrikakeKeshikomiPersistenceImpl service;

	@Injectable
	private UrikakePersistence urikakePersistence;

	@Injectable
	private KeshikomiPersistence keshikomiPersistence;

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
				service.getDomain(urikakeId);
				result = urikakeKeshikomi;
				times = 1;
			}
		};
		// do
		AmountOfMoney actual = service.getDomainAsOf(urikake, keshikomi2);
		// check
		assertEquals(500, actual.intValue());
	}

}