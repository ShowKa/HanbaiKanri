package com.showka.service.persistence.u08;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.PersistenceTestCase;
import com.showka.domain.builder.SeikyuBuilder;
import com.showka.domain.builder.SeikyuMeisaiBuilder;
import com.showka.domain.builder.UrikakeBuilder;
import com.showka.domain.builder.UrikakeKeshikomiBuilder;
import com.showka.domain.u01.FurikomiIrainin;
import com.showka.domain.u06.Urikake;
import com.showka.domain.u06.UrikakeKeshikomi;
import com.showka.domain.u07.Seikyu;
import com.showka.domain.u07.SeikyuMeisai;
import com.showka.entity.WFirmBankFuriwake;
import com.showka.entity.WFirmBankFuriwakePK;
import com.showka.repository.i.WFirmBankFuriwakeRepository;
import com.showka.service.persistence.u06.i.UrikakeKeshikomiPersistence;
import com.showka.service.query.u01.i.FurikomiIraininQuery;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

/**
 * CRUD Service Test Case
 *
 */
public class FirmBankFuriwakePersistenceImplTest2 extends PersistenceTestCase {

	@Tested
	@Autowired
	private FirmBankFuriwakePersistenceImpl service;

	@Injectable
	private FurikomiIraininQuery furikomiIraininQuery;

	@Injectable
	private UrikakeKeshikomiPersistence urikakeKeshikomiPersistence;

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
		ub.withKingaku(100);
		Urikake urikake = ub.build();
		// 売掛消込
		UrikakeKeshikomiBuilder ukb = new UrikakeKeshikomiBuilder();
		ukb.withUrikake(urikake);
		ukb.withKeshikomiSet(new HashSet<>());
		UrikakeKeshikomi urikakeKeshikomi = ukb.build();
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
				urikakeKeshikomiPersistence.getDomain(urikake.getRecordId());
				result = urikakeKeshikomi;
			}
		};
		// do
		service.save(seikyu, furikomiIrainin);
		// verify
		new Verifications() {
			{
				urikakeKeshikomiPersistence.getDomain(urikake.getRecordId());
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
