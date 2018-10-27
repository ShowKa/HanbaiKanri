package com.showka.service.specification.u05;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.CrudServiceTestCase;
import com.showka.domain.u05.Uriage;
import com.showka.repository.i.RUriageKeijoRepository;
import com.showka.repository.i.RUriageRepository;
import com.showka.value.EigyoDate;
import com.showka.value.TheDate;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

public class UriageKeijoSpecificationServiceImplTest extends CrudServiceTestCase {

	@Tested
	private UriageKeijoSpecificationServiceImpl service;

	@Autowired
	@Injectable
	private RUriageRepository rUriageRepository;

	@Autowired
	@Injectable
	private RUriageKeijoRepository rUriageKeijoRepository;

	// 売上履歴テーブル
	@SuppressWarnings("deprecation")
	private static final Object[] R_URIAGE_01 = {
			"r-KK01-00001",
			new Date("2017/01/01"),
			new Date("2017/01/01"),
			"00",
			0.08,
			"r-KK01-00001-20170101" };
	// 売上計上テーブル
	private static final Object[] R_URIAGE_KEIJO_01 = {
			"r-KK01-00001-20170101",
			"r-BS01",
			"r-KK01-00001-20170101-BS01" };

	/**
	 * 売上の計上済み判定.
	 * 
	 * <pre>
	 * 入力：売上 
	 * 条件：計上済み（売上計上テーブル登録済み）
	 * 結果：true=計上済み
	 * </pre>
	 */
	@Test
	public void test01_isKeijoZumi(@Injectable Uriage uriage) throws Exception {
		// database
		super.deleteAndInsert(R_URIAGE, R_URIAGE_COLUMN, R_URIAGE_01);
		super.deleteAndInsert(R_URIAGE_KEIJO, R_URIAGE_KEIJO_COLUMN, R_URIAGE_KEIJO_01);
		// input
		// 計上日
		TheDate keijoDate = new EigyoDate(2017, 1, 1);
		// 売上ID
		String uriageId = "r-KK01-00001";
		// expect
		new Expectations() {
			{
				uriage.getKeijoDate();
				result = keijoDate;
				uriage.getRecordId();
				result = uriageId;
			}
		};
		// do
		boolean actual = service.isKeijoZumi(uriage);
		// verify
		new Verifications() {
			{
				uriage.getKeijoDate();
				times = 1;
				uriage.getRecordId();
				times = 1;
			}
		};
		// check
		assertTrue(actual);
	}

	/**
	 * 売上の計上済み判定.
	 * 
	 * <pre>
	 * 入力：売上 
	 * 条件：計上済みではない。
	 * 結果：false
	 * </pre>
	 */
	@Test
	public void test02_isKeijoZumi(@Injectable Uriage uriage) throws Exception {
		// database
		super.deleteAndInsert(R_URIAGE, R_URIAGE_COLUMN, R_URIAGE_01);
		super.deleteAll(R_URIAGE_KEIJO);
		// input
		// 計上日
		TheDate keijoDate = new EigyoDate(2017, 1, 1);
		// 売上ID
		String uriageId = "r-KK01-00001";
		// expect
		new Expectations() {
			{
				uriage.getKeijoDate();
				result = keijoDate;
				uriage.getRecordId();
				result = uriageId;
			}
		};
		// do
		boolean actual = service.isKeijoZumi(uriage);
		// verify
		new Verifications() {
			{
				uriage.getKeijoDate();
				times = 1;
				uriage.getRecordId();
				times = 1;
			}
		};
		// check
		assertFalse(actual);
	}
}
