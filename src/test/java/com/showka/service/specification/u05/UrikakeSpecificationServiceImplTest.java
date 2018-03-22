package com.showka.service.specification.u05;

import java.util.Optional;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.Uriage;
import com.showka.domain.Urikake;
import com.showka.kubun.HanbaiKubun;
import com.showka.value.EigyoDate;
import com.showka.value.Kakaku;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

public class UrikakeSpecificationServiceImplTest extends SimpleTestCase {

	@Tested
	private UrikakeSpecificationServiceImpl service;

	/**
	 * 売掛ビルド.
	 * 
	 * <pre>
	 * 入力：売上
	 * 条件：販売区分=掛売, 入金予定日=2017/2/1, 売上合計=100円（税8%）
	 * 結果：売掛 => 入金予定日=2017/2/1, 残高=108円
	 * </pre>
	 */
	@Test
	public void test01_buildUrikakeBy(@Injectable Uriage uriage) throws Exception {
		// input
		// 入金予定日
		EigyoDate nyukinYoteiDate = new EigyoDate(2017, 2, 1);
		// expect
		new Expectations() {
			{
				// 販売区分取得
				uriage.getHanbaiKubun();
				result = HanbaiKubun.掛売;
				// 入金予定日取得
				uriage.getNyukinYoteiDate();
				result = nyukinYoteiDate;
				// 売上合計金額取得
				uriage.getUriageGokeiKakaku();
				result = new Kakaku(100, 0.08);
			}
		};
		// do
		Optional<Urikake> actual = service.buildUrikakeBy(uriage);
		// verify
		new Verifications() {
			{
				// 販売区分取得
				uriage.getHanbaiKubun();
				times = 1;
				// 入金予定日取得
				uriage.getNyukinYoteiDate();
				times = 1;
				// 売上合計金額取得
				uriage.getUriageGokeiKakaku();
				times = 1;
			}
		};
		// check
		assertEquals(nyukinYoteiDate, actual.get().getNyukinYoteiDate());
		assertEquals(108, actual.get().getZandaka().intValue());
	}

	/**
	 * 売掛ビルド.
	 * 
	 * <pre>
	 * 入力：売上
	 * 条件：販売区分=現金
	 * 結果：empty
	 * </pre>
	 */
	@Test
	public void test02_buildUrikakeBy(@Injectable Uriage uriage) throws Exception {
		// expect
		new Expectations() {
			{
				// 販売区分取得
				uriage.getHanbaiKubun();
				result = HanbaiKubun.現金;
			}
		};
		// do
		Optional<Urikake> actual = service.buildUrikakeBy(uriage);
		// verify
		new Verifications() {
			{
				// 販売区分取得
				uriage.getHanbaiKubun();
				times = 1;
			}
		};
		// check
		assertFalse(actual.isPresent());
	}

}
