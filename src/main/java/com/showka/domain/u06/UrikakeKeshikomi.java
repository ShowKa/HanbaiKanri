package com.showka.domain.u06;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.showka.domain.DomainAggregation;
import com.showka.domain.u08.Keshikomi;
import com.showka.domain.u08.Nyukin;
import com.showka.system.exception.SystemException;
import com.showka.value.AmountOfMoney;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UrikakeKeshikomi extends DomainAggregation {

	/** 売掛. */
	private Urikake urikake;

	/** 消込リスト. */
	private Set<Keshikomi> keshikomiSet;

	// public
	/**
	 * 消込金額合計取得
	 * 
	 * @return 消込.金額の合計
	 */
	public AmountOfMoney getKeshikomiKingakuGokei() {
		int keshikomiKingakuGokei = keshikomiSet.stream().mapToInt(k -> {
			return k.getKingaku().intValue();
		}).sum();
		return new AmountOfMoney(keshikomiKingakuGokei);
	}

	/**
	 * 残高取得
	 * 
	 * @return 売掛.金額 - 消込金額合計
	 */
	public AmountOfMoney getZandaka() {
		AmountOfMoney keshikomiKingakuGokei = this.getKeshikomiKingakuGokei();
		return urikake.getKingaku().subtract(keshikomiKingakuGokei);
	}

	/**
	 * 消込完了判定.
	 * 
	 * @return 消込完了（残高=0円）ならtrue
	 */
	public boolean done() {
		return this.getZandaka().equals(0);
	}

	/**
	 * 引数.入金の消込の金額の合計
	 * 
	 * @param nyukin
	 *            入金
	 * @return 消込金額
	 */
	public AmountOfMoney getKeshikomiKingakuOf(Nyukin nyukin) {
		int amount = this.keshikomiSet.stream().filter(k -> {
			return k.getNyukin().equals(nyukin);
		}).mapToInt(k -> {
			return k.getKingaku().intValue();
		}).sum();
		return new AmountOfMoney(amount);
	}

	/**
	 * 引数でしていした入金以外による消込金額.
	 * 
	 * @param nyukin
	 *            入金
	 * @return 消込金額
	 */
	public AmountOfMoney getKeshikomiKigakuExcluding(Nyukin nyukin) {
		return getKeshikomiKingakuGokei().subtract(this.getKeshikomiKingakuOf(nyukin));
	}

	/**
	 * 消込有無.
	 * 
	 * @return 消込ありの場合true
	 */
	public boolean hasKeshikomi() {
		return this.keshikomiSet.size() > 0;
	}

	// override
	@Override
	public void validate() throws SystemException {
		Set<Urikake> urikakeSet = keshikomiSet.stream().map(keshikomi -> {
			return keshikomi.getUrikake();
		}).collect(Collectors.toSet());
		if (urikakeSet.size() > 1) {
			List<String> uriageIds = urikakeSet.stream().map(Urikake::getUriageId).collect(Collectors.toList());
			throw new SystemException("別の売掛への消込がセットされています。 : " + uriageIds);
		}
	}

	/**
	 * 同値判定.
	 * 
	 * @return 同じ売掛ならtrue
	 */
	@Override
	protected boolean equals(DomainAggregation other) {
		UrikakeKeshikomi o = (UrikakeKeshikomi) other;
		return urikake.equals(o.urikake);
	}

	@Override
	public int hashCode() {
		return urikake.hashCode();
	}
}
