package com.showka.domain;

import java.util.List;
import java.util.stream.Collectors;

import com.showka.system.exception.SystemException;
import com.showka.value.AmountOfMoney;
import com.showka.value.EigyoDate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class KokyakuUrikake extends DomainBase {

	// members
	/** 顧客 */
	private Kokyaku kokyaku;

	/** 売掛(残高あり) */
	private List<Urikake> urikakeList;

	// public methods
	/**
	 * 売上の売掛金の合計金額を取得.
	 * 
	 * @return 合計
	 */
	public AmountOfMoney getGokeiKingaku() {
		return this.getGokeiKingaku(urikakeList);
	}

	/**
	 * 指定した営業日までに入金予定日を迎えている売掛を取得する.
	 * 
	 * <pre>
	 * 指定した営業日=入金予定日の場合も含まれる。
	 * </pre>
	 * 
	 * @param date
	 *            営業日
	 * @return 入金が必要な売掛
	 */
	public List<Urikake> getUrikakeListNyukinRequiredBy(EigyoDate date) {
		return urikakeList.parallelStream().filter(urikake -> {
			// 入金予定日<=指定日付のものを抽出
			EigyoDate nyukinYoteiDate = urikake.getNyukinYoteiDate();
			return nyukinYoteiDate.isBefore(date) || nyukinYoteiDate.equals(date);
		}).collect(Collectors.toList());
	}

	/**
	 * 指定した営業日までに入金予定日を迎えている売掛の残高の合計金額を取得する.
	 * 
	 * <pre>
	 * 指定した営業日=入金予定日の場合も含まれる。
	 * </pre>
	 * 
	 * @param date
	 *            営業日
	 * @return 入金が必要な売掛の合計金額
	 */
	public AmountOfMoney getGokeiKingakuNyukinRequiredBy(EigyoDate date) {
		List<Urikake> _urikakeList = this.getUrikakeListNyukinRequiredBy(date);
		return this.getGokeiKingaku(_urikakeList);
	}

	// override methods
	@Override
	public void validate() throws SystemException {
		// do nothing
	}

	@Override
	protected boolean equals(DomainBase other) {
		KokyakuUrikake o = (KokyakuUrikake) other;
		return kokyaku.equals(o.kokyaku);
	}

	@Override
	public int hashCode() {
		return kokyaku.hashCode();
	}

	// private methods
	/**
	 * 売掛の合計金額を取得.
	 * 
	 * @param urikakeList
	 *            売掛のリスト
	 * @return 残高合計
	 */
	private AmountOfMoney getGokeiKingaku(List<Urikake> urikakeList) {
		int gokeiKingaku = urikakeList.parallelStream().mapToInt(urikake -> {
			return urikake.getKingaku().intValue();
		}).sum();
		return new AmountOfMoney(gokeiKingaku);
	}
}
