package com.showka.service.search.u06;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.builder.UrikakeBuilder;
import com.showka.domain.builder.UrikakeKeshikomiBuilder;
import com.showka.domain.u06.Urikake;
import com.showka.domain.u06.UrikakeKeshikomi;
import com.showka.entity.TUrikake;
import com.showka.repository.i.TUrikakeRepository;
import com.showka.service.crud.u06.i.UrikakeKeshikomiCrudService;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

public class UrikakeSearchServiceImplTest2 extends SimpleTestCase {

	@Tested
	@Injectable
	private UrikakeSearchServiceImpl service;

	@Injectable
	private TUrikakeRepository tUrikakeRepository;

	@Injectable
	private UrikakeKeshikomiCrudService urikakeKeshikomiCrudService;

	@Test
	public void test01_getUrikakeOfKokyaku() throws Exception {
		// input
		String kokyakuId = "r-KK01";
		// mock
		// 売掛リスト
		List<TUrikake> urikakeList = new ArrayList<>();
		// 売掛1 record
		TUrikake _urikake1 = new TUrikake();
		_urikake1.setRecordId("r-KK01-00001");
		urikakeList.add(_urikake1);
		// 売掛1 domain
		UrikakeBuilder ub1 = new UrikakeBuilder();
		Urikake urikake1 = ub1.build();
		// 売掛消込1
		UrikakeKeshikomiBuilder ukb1 = new UrikakeKeshikomiBuilder();
		ukb1.withUrikake(urikake1);
		ukb1.withKeshikomiSet(new HashSet<>());
		UrikakeKeshikomi urikakeKeshikomi1 = spy(ukb1.build());
		// 売掛2
		TUrikake _urikake2 = new TUrikake();
		_urikake2.setRecordId("r-KK01-00002");
		urikakeList.add(_urikake2);
		// 売掛2 domain
		UrikakeBuilder ub2 = new UrikakeBuilder();
		Urikake urikake2 = ub2.build();
		// 売掛消込2
		UrikakeKeshikomiBuilder ukb2 = new UrikakeKeshikomiBuilder();
		ukb2.withUrikake(urikake2);
		ukb2.withKeshikomiSet(new HashSet<>());
		UrikakeKeshikomi urikakeKeshikomi2 = spy(ukb2.build());
		// spy
		doReturn(false).when(urikakeKeshikomi1).done();
		doReturn(true).when(urikakeKeshikomi2).done();
		// expect
		new Expectations() {
			{
				service.getUrikakeOfKokyakuRecord(kokyakuId);
				result = urikakeList;
				urikakeKeshikomiCrudService.getDomain(_urikake1.getRecordId());
				result = urikakeKeshikomi1;
				urikakeKeshikomiCrudService.getDomain(_urikake2.getRecordId());
				result = urikakeKeshikomi2;
			}
		};
		// do
		List<Urikake> actual = service.getUrikakeForSeikyu(kokyakuId);
		// verify
		new Verifications() {
			{
				service.getUrikakeOfKokyakuRecord(kokyakuId);
				times = 1;
				urikakeKeshikomiCrudService.getDomain(_urikake1.getRecordId());
				times = 1;
				urikakeKeshikomiCrudService.getDomain(_urikake2.getRecordId());
				times = 1;
			}
		};
		// check
		assertEquals(1, actual.size());
		assertEquals(urikake1, actual.get(0));
	}
}
