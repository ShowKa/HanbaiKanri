package com.showka.service.crud.u05;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.CrudServiceTestCase;
import com.showka.domain.Busho;
import com.showka.entity.RUriage;
import com.showka.entity.RUriageKeijo;
import com.showka.repository.i.RUriageKeijoRepository;
import com.showka.service.search.u05.i.UriageRirekiSearchService;
import com.showka.value.TheDate;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;

public class UriageKeijoCrudServiceImplTest extends CrudServiceTestCase {

	@Tested
	private UriageKeijoCrudServiceImpl uriageKeijoCrudServiceImpl;

	@Injectable
	@Autowired
	private RUriageKeijoRepository repo;

	@Injectable
	private UriageRirekiSearchService uriageRirekiSearchService;

	/**
	 * 
	 * <pre>
	 * 入力：部署、計上日<br>
	 * 条件:計上対象の売上履歴が取得できる<br>
	 * 結果:登録される
	 * </pre>
	 */
	@Test
	public void test01_keijo(@Injectable Busho busho, @Injectable TheDate date) throws Exception {
		super.deleteAll(R_URIAGE_KEIJO);
		// input
		RUriage e = new RUriage();
		e.setRecordId("r-KK01-00001-20170101");
		List<RUriage> uriageRirekiList = new ArrayList<RUriage>();
		uriageRirekiList.add(e);
		// expect
		new Expectations() {
			{
				uriageRirekiSearchService.search(busho, date);
				result = uriageRirekiList;
				busho.getRecordId();
				result = "r-BS01";
			}
		};
		// do
		uriageKeijoCrudServiceImpl.keijo(busho, date);
		Optional<RUriageKeijo> actual = repo.findById("r-KK01-00001-20170101");
		assertTrue(actual.isPresent());
	}

}
