package com.showka.service.search.u06;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.CrudServiceTestCase;
import com.showka.domain.builder.UrikakeBuilder;
import com.showka.domain.u06.Urikake;
import com.showka.repository.i.TUrikakeRepository;
import com.showka.service.crud.u06.i.UrikakeCrudService;
import com.showka.service.search.u06.UrikakeSearchServiceImpl;
import com.showka.service.specification.u06.i.UrikakeKeshikomiSpecificationService;
import com.showka.value.AmountOfMoney;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;

public class UrikakeSearchServiceImplTest extends CrudServiceTestCase {

	@Autowired
	@Tested
	private UrikakeSearchServiceImpl service;

	@Autowired
	@Injectable
	private TUrikakeRepository tUrikakeRepository;

	@Injectable
	private UrikakeCrudService urikakeCrudService;

	@Injectable
	private UrikakeKeshikomiSpecificationService urikakeKeshikomiSpecificationService;

	/** 顧客. */
	private static final Object[] M_KOKYAKU_01 = { "KK01", "aaaa", "左京区", "01", "10", "r-BS01", "r-KK01" };

	/** 売掛. */
	private static final Object[] T_URIKAKE_01 = { "r-KK01-00001", 1, d("20170101"), "r-KK01-00001" };
	private static final Object[] T_URIKAKE_02 = { "r-KK01-00002", 1, d("20170101"), "r-KK01-00002" };
	/** 売上. */
	private static final Object[] T_URIAGE_01 = {
			"r-KK01",
			"00001",
			new Date(),
			new Date(),
			"00",
			0.08,
			"r-KK01-00001" };
	private static final Object[] T_URIAGE_02 = {
			"r-KK01",
			"00002",
			new Date(),
			new Date(),
			"00",
			0.08,
			"r-KK01-00002" };

	@Test
	public void test01_getUrikakeOfKokyaku(@Injectable Urikake _urikake) throws Exception {
		// database
		super.deleteAndInsert(M_KOKYAKU, M_KOKYAKU_COLUMN, M_KOKYAKU_01);
		super.deleteAndInsert(T_URIAGE, T_URIAGE_COLUMN, T_URIAGE_01, T_URIAGE_02);
		super.deleteAndInsert(T_URIKAKE, T_URIKAKE_COLUMN, T_URIKAKE_01, T_URIKAKE_02);
		// input
		// 売掛1
		UrikakeBuilder ub1 = new UrikakeBuilder();
		String urikakeId1 = "r-KK01-00001";
		ub1.withRecordId(urikakeId1);
		Urikake _u1 = ub1.build();
		Urikake u1 = spy(_u1);
		// 売掛2
		UrikakeBuilder ub2 = new UrikakeBuilder();
		String urikakeId2 = "r-KK01-00002";
		ub2.withRecordId(urikakeId2);
		Urikake _u2 = ub2.build();
		Urikake u2 = spy(_u2);
		// spy
		doReturn(false).when(u2).equals(u1);
		// expect
		new Expectations() {
			{
				urikakeCrudService.getDomain(urikakeId1);
				result = u1;
				urikakeCrudService.getDomain(urikakeId2);
				result = u2;
				urikakeKeshikomiSpecificationService.getZandakaOf(u1);
				result = new AmountOfMoney(0);
				// need spy -> u2 notEqual u1
				urikakeKeshikomiSpecificationService.getZandakaOf(u2);
				result = new AmountOfMoney(1);
			}
		};
		// do
		List<Urikake> actual = service.getUrikakeOfKokyaku("r-KK01");
		// check
		assertEquals(1, actual.size());
		assertEquals(urikakeId2, actual.get(0).getRecordId());
	}
}
