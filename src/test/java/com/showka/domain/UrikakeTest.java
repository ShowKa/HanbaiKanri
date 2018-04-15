package com.showka.domain;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.builder.UrikakeBuilder;
import com.showka.value.AmountOfMoney;
import com.showka.value.Kakaku;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Verifications;

public class UrikakeTest extends SimpleTestCase {

	/**
	 * 現時点での入金額取得.
	 * 
	 * <pre>
	 * 入力：なし
	 * 条件：合計金額=105、売掛金残高=5
	 * 結果：入金額=100
	 * </pre>
	 */
	@Test
	public void test01_getPresentNyukinKingaku(@Injectable Uriage uriage) throws Exception {
		// input
		// 価格
		Kakaku kakaku = new Kakaku(100, 0.05);
		// 売掛
		UrikakeBuilder b = new UrikakeBuilder();
		b.withUriage(uriage);
		b.withZandaka(new AmountOfMoney(5));
		Urikake urikake = b.build();
		// expect
		new Expectations() {
			{
				uriage.getUriageGokeiKakaku();
				result = kakaku;
			}
		};
		// do
		AmountOfMoney actual = urikake.getPresentNyukinKingaku();
		// verify
		new Verifications() {
			{
				uriage.getUriageGokeiKakaku();
				times = 1;
			}
		};
		// check
		assertEquals(100, actual.intValue());
	}

}
