package com.showka.service.specification.u06;

import java.util.Optional;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.u05.Uriage;
import com.showka.domain.u06.Urikake;
import com.showka.kubun.HanbaiKubun;
import com.showka.service.crud.u06.i.UrikakeKeshikomiCrudService;
import com.showka.service.specification.u06.UrikakeSpecificationServiceImpl;
import com.showka.value.EigyoDate;
import com.showka.value.Kakaku;
import com.showka.value.TheDate;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

public class UrikakeSpecificationServiceImplTest extends SimpleTestCase {

	@Tested
	private UrikakeSpecificationServiceImpl service;

	@Injectable
	private UrikakeKeshikomiCrudService urikakeKeshikomiCrudService;

	/**
	 * 売掛ビルド.
	 * 
	 * <pre>
	 * 入力：売上
	 * 条件：販売区分=掛売, 入金予定日=2017/2/1, 売上合計=100円（税8%）
	 * 結果：売掛 => 入金予定日=2017/2/1, 金額=108円
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
				uriage.getDefaultNyukinYoteiDate();
				result = Optional.of(nyukinYoteiDate);
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
				uriage.getDefaultNyukinYoteiDate();
				times = 1;
				// 売上合計金額取得
				uriage.getUriageGokeiKakaku();
				times = 1;
			}
		};
		// check
		assertEquals(nyukinYoteiDate, actual.get().getNyukinYoteiDate());
		assertEquals(108, actual.get().getKingaku().intValue());
	}

	/**
	 * 売掛ビルド.
	 * 
	 * <pre>
	 * 入力：売上 計上日=2017/1/1
	 * 条件：顧客の販売区分=現金
	 * 結果：売掛 => 入金予定日=2017/2/26
	 * </pre>
	 */
	@Test
	public void test02_buildUrikakeBy(@Injectable Uriage uriage) throws Exception {
		// expect
		new Expectations() {
			{
				// 販売区分取得
				uriage.getHanbaiKubun();
				result = HanbaiKubun.掛売;
				// 入金予定日取得
				uriage.getDefaultNyukinYoteiDate();
				result = Optional.empty();
				// 計上日取得
				uriage.getKeijoDate();
				result = new TheDate(2017, 1, 1);
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
				uriage.getDefaultNyukinYoteiDate();
				times = 1;
				// 計上日取得
				uriage.getKeijoDate();
				times = 1;
				// 売上合計金額取得
				uriage.getUriageGokeiKakaku();
				times = 1;
			}
		};
		// check
		// 入金予定日
		EigyoDate nyukinYoteiDate = new EigyoDate(2017, 2, 26);
		assertEquals(nyukinYoteiDate, actual.get().getNyukinYoteiDate());
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
	public void test03_buildUrikakeBy(@Injectable Uriage uriage) throws Exception {
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
