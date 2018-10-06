package com.showka.service.search.u08;

import static com.showka.table.public_.tables.T_FIRM_BANK_FURIKOMI.*;
import static com.showka.table.public_.tables.T_SEIKYU.*;
import static com.showka.table.public_.tables.W_FIRM_BANK_FURIWAKE.*;

import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Result;
import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.builder.BushoBuilder;
import com.showka.domain.u08.FBFurikomiMatchingResult;
import com.showka.domain.u08.FBFurikomiMatchingResult.MatchedPair;
import com.showka.domain.z00.Busho;
import com.showka.table.public_.tables.T_FIRM_BANK_FURIKOMI;
import com.showka.table.public_.tables.T_SEIKYU;
import com.showka.table.public_.tables.W_FIRM_BANK_FURIWAKE;
import com.showka.value.TheDate;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

// 一部テストは「2」に移譲。
public class FirmBankFurikomiSearchServiceImplTest extends SimpleTestCase {

	@Tested
	@Injectable
	private FirmBankFurikomiSearchServiceImpl service;

	@Injectable
	private DSLContext create;

	// alias
	/** T_FIRM_BANK_FURIKOMI */
	private static final T_FIRM_BANK_FURIKOMI fb = t_firm_bank_furikomi.as("fb");
	/** M_BUSHO_BANK_ACCOUNT */
	// private static final M_BUSHO_BANK_ACCOUNT ba =
	// m_busho_bank_account.as("ba");
	/** W_FIRM_BANK_FURIWAKE */
	private static final W_FIRM_BANK_FURIWAKE fw = w_firm_bank_furiwake.as("fw");
	/** M_FURIKOMI_IRAININ */
	// private static final M_FURIKOMI_IRAININ fi = m_furikomi_irainin.as("fi");
	/** T_SEIKYU */
	private static final T_SEIKYU ts = t_seikyu.as("ts");

	// 注意事項:FBFurikomiMatchingResultのテストも兼ねてしまっている。。。
	@Test
	public void test_SearchMatched_01() throws Exception {
		// input
		BushoBuilder bb = new BushoBuilder();
		bb.withRecordId("r-BS01");
		Busho busho = bb.build();
		TheDate date = new TheDate(2017, 8, 20);
		// records
		Field<?>[] _fs = ArrayUtils.addAll(fb.fields(), ts.fields());
		Field<?>[] fs = ArrayUtils.addAll(_fs, fw.fields());
		Result<Record> records = _create.newResult(fs);
		// record1 マッチングOK
		Record r1 = _create.newRecord(fs);
		r1.set(fb.record_id, "r-20170820-01");
		r1.set(fw.record_id, "r-KK01-01-001");
		r1.set(ts.record_id, "anyting_001");
		records.add(r1);
		// record2 複数マッチング
		Record r2_1 = _create.newRecord(fs);
		r2_1.set(fb.record_id, "r-20170820-02");
		r2_1.set(fw.record_id, "anything_002");
		r2_1.set(ts.record_id, "anything_003");
		records.add(r2_1);
		Record r2_2 = _create.newRecord(fs);
		r2_2.set(fb.record_id, "r-20170820-02");
		r2_2.set(fw.record_id, "anything_004");
		r2_2.set(ts.record_id, "anything_005");
		records.add(r2_2);
		// record3 同一振込
		Record r3_1 = _create.newRecord(fs);
		r3_1.set(fb.record_id, "r-20170820-03-1");
		r3_1.set(fw.record_id, "anything_006");
		r3_1.set(ts.record_id, "r-KK01-20170101");
		records.add(r3_1);
		Record r3_2 = _create.newRecord(fs);
		r3_2.set(fb.record_id, "r-20170820-03-2");
		r3_2.set(fw.record_id, "anything_007");
		r3_2.set(ts.record_id, "r-KK01-20170101");
		records.add(r3_2);
		// expect
		new Expectations() {
			{
				service.searchOf(busho, date);
				result = records;
			}
		};
		// do
		FBFurikomiMatchingResult actual = service.searchMatched(busho, date);
		// verify
		new Verifications() {
			{
				service.searchOf(busho, date);
				times = 1;
			}
		};
		// check
		// マッチングOK
		List<MatchedPair> matched = actual.getMatchedNormally();
		assertEquals(1, matched.size());
		assertEquals("r-KK01-01-001", matched.get(0).getFuriwakeId());
		// 複数マッチング
		List<String> multi = actual.getMultipleMathed();
		assertEquals(1, multi.size());
		assertEquals("r-20170820-02", multi.get(0));
		// 同一振込
		List<String> rep = actual.getRepetition();
		assertEquals(2, rep.size());
		assertTrue(rep.contains("r-20170820-03-1"));
		assertTrue(rep.contains("r-20170820-03-2"));
		System.out.println(actual);
	}
}
