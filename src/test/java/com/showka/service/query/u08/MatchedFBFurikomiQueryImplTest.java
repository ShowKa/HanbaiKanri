package com.showka.service.query.u08;

import static com.showka.table.public_.tables.T_FIRM_BANK_FURIKOMI.*;
import static com.showka.table.public_.tables.W_FIRM_BANK_FURIKOMI_MATCHING.*;
import static com.showka.table.public_.tables.W_FIRM_BANK_FURIWAKE.*;

import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Result;
import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.builder.SeikyuBuilder;
import com.showka.domain.u07.Seikyu;
import com.showka.domain.u08.MatchedFBFurikomi;
import com.showka.service.crud.u07.i.SeikyuCrud;
import com.showka.service.query.u08.MatchedFBFurikomiQueryImpl;
import com.showka.table.public_.tables.T_FIRM_BANK_FURIKOMI;
import com.showka.table.public_.tables.W_FIRM_BANK_FURIKOMI_MATCHING;
import com.showka.table.public_.tables.W_FIRM_BANK_FURIWAKE;
import com.showka.value.TheDate;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

// 一部テストは「2」に移譲。
public class MatchedFBFurikomiQueryImplTest extends SimpleTestCase {

	@Tested
	@Injectable
	private MatchedFBFurikomiQueryImpl service;

	@Injectable
	private DSLContext create;

	@Injectable
	private SeikyuCrud seikyuPersistence;

	// alias
	/** T_FIRM_BANK_FURIKOMI */
	private static final T_FIRM_BANK_FURIKOMI fb = t_firm_bank_furikomi.as("fb");
	/** W_FIRM_BANK_FURIKOMI_MATCHING */
	private static final W_FIRM_BANK_FURIKOMI_MATCHING mt = w_firm_bank_furikomi_matching.as("mt");
	/** W_FIRM_BANK_FURIWAKE */
	private static final W_FIRM_BANK_FURIWAKE fw = w_firm_bank_furiwake.as("fw");

	@Test
	public void test_search_01() throws Exception {
		// input
		TheDate transmissionDate = new TheDate(2017, 8, 20);
		// records
		Field<?>[] fs = ArrayUtils.addAll(fb.fields(), fw.fields());
		Result<Record> records = _create.newResult(fs);
		// record
		Record r1 = _create.newRecord(fs);
		r1.set(fb.record_id, "r-20170820-01");
		r1.set(fb.kingaku, 1);
		r1.set(fw.seikyu_id, "r-KK01-20170101");
		records.add(r1);
		// 請求
		SeikyuBuilder sb = new SeikyuBuilder();
		Seikyu seikyu = sb.build();
		// expect
		new Expectations() {
			{
				service.query(transmissionDate);
				result = records;
				seikyuPersistence.getDomain("r-KK01-20170101");
				result = seikyu;
			}
		};
		// do
		List<MatchedFBFurikomi> actual = service.get(transmissionDate);
		// verify
		new Verifications() {
			{
				service.query(transmissionDate);
				times = 1;
				seikyuPersistence.getDomain("r-KK01-20170101");
				times = 1;
			}
		};
		// check
		assertEquals(1, actual.size());
		MatchedFBFurikomi a = actual.get(0);
		// FB振込ID
		assertEquals("r-20170820-01", a.getFBFurikomiId());
		// 金額
		assertEquals(1, a.getKingaku().intValue());
		// 請求
		assertEquals(seikyu, a.getSeikyu());
		// 伝送日付
		assertEquals(transmissionDate, a.getTransmissionDate());

	}

}
