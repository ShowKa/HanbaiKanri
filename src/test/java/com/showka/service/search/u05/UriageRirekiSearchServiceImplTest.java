package com.showka.service.search.u05;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.PersistenceTestCase;
import com.showka.domain.builder.BushoBuilder;
import com.showka.domain.z00.Busho;
import com.showka.entity.RUriage;
import com.showka.repository.i.RUriageRepository;
import com.showka.value.EigyoDate;

import mockit.Injectable;
import mockit.Tested;

public class UriageRirekiSearchServiceImplTest extends PersistenceTestCase {

	@Tested
	private UriageRirekiSearchServiceImpl service;

	@Injectable
	@Autowired
	private RUriageRepository repo;

	private static final Object[] R_URIAGE_V01 = {
			"r-KK01-00001",
			d("20170101"),
			d("20170101"),
			"00",
			0.08,
			"r-KK01-00001-20170101" };
	private static final Object[] T_URIAGE_V01 = {
			"r-KK01",
			"00001",
			d("20170101"),
			d("20170101"),
			"00",
			0.08,
			"r-KK01-00001" };
	private static final Object[] M_KOKYAKU_V01 = { "KK01", "aaaa", "左京区", "01", "00", "r-BS01", "r-KK01" };
	private static final Object[] M_BUSHO_V01 = { "BS01", "01", "01", "部署01", "r-BS01" };

	@Test
	@Transactional
	public void test01_search() throws Exception {
		// database
		super.deleteAndInsert(R_URIAGE, R_URIAGE_COLUMN, R_URIAGE_V01);
		super.deleteAndInsert(T_URIAGE, T_URIAGE_COLUMN, T_URIAGE_V01);
		super.deleteAndInsert(M_KOKYAKU, M_KOKYAKU_COLUMN, M_KOKYAKU_V01);
		super.deleteAndInsert(M_BUSHO, M_BUSHO_COLUMN, M_BUSHO_V01);
		// input
		// 部署
		BushoBuilder bb = new BushoBuilder();
		bb.withCode("BS01");
		bb.withRecordId("r-BS01");
		Busho busho = bb.build();
		// 計上日
		EigyoDate date = new EigyoDate(2017, 1, 1);
		// do
		List<RUriage> actual = service.search(busho, date);
		// check
		assertEquals(1, actual.size());
	}
}
