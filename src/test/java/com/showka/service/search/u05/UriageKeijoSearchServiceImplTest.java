package com.showka.service.search.u05;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.CrudServiceTestCase;
import com.showka.domain.Busho;
import com.showka.entity.RUriage;
import com.showka.entity.RUriageKeijo;
import com.showka.repository.i.RUriageKeijoRepository;
import com.showka.service.search.u05.i.UriageRirekiSearchService;
import com.showka.system.EmptyProxy;
import com.showka.value.TheDate;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

public class UriageKeijoSearchServiceImplTest extends CrudServiceTestCase {

	@Tested
	private UriageKeijoSearchServiceImpl service;

	@Autowired
	@Injectable
	private RUriageKeijoRepository repo;

	@Injectable
	private UriageRirekiSearchService uriageRirekiSearchService;

	/** 売上計上01. */
	private static final Object[] R_URIAGE_KEIJO_V01 = {
			"r-KK01-00001-20170101",
			"r-BS01",
			"r-KK01-00001-20170101-BS01" };
	/** 売上計上02. */
	private static final Object[] R_URIAGE_KEIJO_V02 = {
			"r-KK02-00002-20170101",
			"r-BS02",
			"r-KK02-00002-20170101-BS02" };

	/**
	 * 売上検索.
	 * 
	 * <pre>
	 * 入力：部署、計上日<br>
	 * 条件：なし
	 * 結果：売上計上が取得できる(無関係のデータは取得しない)。
	 * </pre>
	 */
	@Test
	public void test01_search() throws Exception {
		// database
		super.deleteAndInsert(R_URIAGE_KEIJO, R_URIAGE_KEIJO_COLUMN, R_URIAGE_KEIJO_V01, R_URIAGE_KEIJO_V02);
		// input
		Busho busho = EmptyProxy.domain(Busho.class);
		TheDate date = new TheDate();
		// uriage rireki entity list
		List<RUriage> rUriageList = new ArrayList<RUriage>();
		RUriage e1 = new RUriage();
		e1.setRecordId("r-KK01-00001-20170101");
		rUriageList.add(e1);
		// expect
		new Expectations() {
			{
				uriageRirekiSearchService.search(busho, date);
				result = rUriageList;
			}
		};
		// do
		List<RUriageKeijo> actual = service.search(busho, date);
		// verify
		new Verifications() {
			{
				uriageRirekiSearchService.search(busho, date);
				times = 1;
			}
		};
		// check
		assertEquals(1, actual.size());
		assertEquals("r-KK01-00001-20170101", actual.get(0).getUriageRirekiId());
	}
}
