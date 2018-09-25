package com.showka.service.crud.u08;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.CrudServiceTestCase;
import com.showka.domain.FurikomiIrainin;
import com.showka.domain.Seikyu;
import com.showka.domain.SeikyuMeisai;
import com.showka.domain.Urikake;
import com.showka.domain.builder.SeikyuBuilder;
import com.showka.domain.builder.SeikyuMeisaiBuilder;
import com.showka.domain.builder.UrikakeBuilder;
import com.showka.entity.WFirmBankFuriwake;
import com.showka.entity.WFirmBankFuriwakePK;
import com.showka.repository.i.WFirmBankFuriwakeRepository;
import com.showka.service.search.u01.i.FurikomiIraininSearchService;
import com.showka.service.specification.u06.i.UrikakeKeshikomiSpecificationService;
import com.showka.value.AmountOfMoney;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

/**
 * CRUD Service Test Case
 *
 */
public class FirmBankFuriwakeCrudServiceImplTest2 extends CrudServiceTestCase {

	@Tested
	@Autowired
	private FirmBankFuriwakeCrudServiceImpl service;

	@Injectable
	private FurikomiIraininSearchService furikomiIraininSearchService;

	@Injectable
	private UrikakeKeshikomiSpecificationService urikakeKeshikomiSpecificationService;

	@Injectable
	@Autowired
	private WFirmBankFuriwakeRepository repo;

	/**
	 * save
	 * 
	 * <pre>
	 * FB振分登録を確認。
	 * </pre>
	 * 
	 * @param furikomiIrainin
	 * @param seikyu
	 * 
	 */
	@Test
	public void test_saveFBFuriwake_01() throws Exception {
		// database
		// data
		// 売掛
		UrikakeBuilder ub = new UrikakeBuilder();
		Urikake urikake = ub.build();
		// 請求明細
		SeikyuMeisaiBuilder smb = new SeikyuMeisaiBuilder();
		smb.withUrikake(urikake);
		SeikyuMeisai seikyuMeisai = smb.build();
		List<SeikyuMeisai> seikyuMeisaiList = new ArrayList<>(Arrays.asList(seikyuMeisai));
		// 請求
		SeikyuBuilder sb = new SeikyuBuilder();
		sb.withSeikyuMeisai(seikyuMeisaiList);
		sb.withRecordId("r-001");
		Seikyu seikyu = sb.build();
		// 振込依頼人
		FurikomiIrainin furikomiIrainin = new FurikomiIrainin("振込依頼人01");
		furikomiIrainin.setRecordId("r-KK01-1");
		// expect
		new Expectations() {
			{
				urikakeKeshikomiSpecificationService.getZandakaOf(urikake);
				result = new AmountOfMoney(100);
			}
		};
		// do
		service.save(seikyu, furikomiIrainin);
		// verify
		new Verifications() {
			{
				urikakeKeshikomiSpecificationService.getZandakaOf(urikake);
				times = 1;
			}
		};
		// check
		WFirmBankFuriwakePK pk = new WFirmBankFuriwakePK();
		pk.setFurikomiIraininId("r-KK01-1");
		pk.setSeikyuId("r-001");
		WFirmBankFuriwake actual = repo.getOne(pk);
		assertNotNull(actual);
		assertEquals(100, actual.getSaikenKingaku().intValue());
	}
}