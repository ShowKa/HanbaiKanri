package com.showka.domain;

import java.util.Set;
import java.util.stream.Collectors;

import com.showka.system.exception.SystemException;
import com.showka.value.AmountOfMoney;
import com.showka.value.EigyoDate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NyukinKeshikomi extends DomainAggregation {

	/** 入金. */
	private Nyukin nyukin;

	/** 消込リスト. */
	private Set<Keshikomi> keshikomiSet;

	// public method
	/**
	 * 消込金額の合計取得.
	 * 
	 * @return 消込.金額の合計
	 */
	public AmountOfMoney getKeshikomiKingakuGokei() {
		int amount = keshikomiSet.stream().mapToInt(k -> {
			return k.getKingaku().intValue();
		}).sum();
		return new AmountOfMoney(amount);
	}

	/**
	 * 未込額取得.
	 * 
	 * @return 入金.金額 - 消込.金額の合計
	 */
	public AmountOfMoney getMikeshikomi() {
		return nyukin.getKingaku().subtract(this.getKeshikomiKingakuGokei());
	}

	/**
	 * 消込済判定.
	 * 
	 * @return 未消込額 = 0ならtrue
	 */
	public boolean done() {
		return this.getMikeshikomi().intValue() == 0;
	}

	// operate collection
	/**
	 * 消込セットマージ.
	 * 
	 * <pre>
	 * Collection#addAll() を代理で呼び出すだけ。
	 * 既存の消込があっても上書きはしない。
	 * </pre>
	 * 
	 * @param マージ対象
	 * @return
	 */
	public void mergeKeshikomiSet(NyukinKeshikomi nyukinKeshikomi) {
		this.keshikomiSet.addAll(nyukinKeshikomi.getKeshikomiSet());
	}

	/**
	 * 指定した日付の消込を除去する。
	 * 
	 * @param date
	 *            日付
	 */
	public void removeKeshikomiOf(EigyoDate date) {
		this.keshikomiSet.removeIf(k -> {
			return k.getDate().equals(date);
		});
	}

	/**
	 * 指定した日付より前の消込を除去する。
	 * 
	 * <pre>
	 * 除去対象: 消込.日付 < 引数.日付
	 * </pre>
	 * 
	 * @param date
	 *            日付
	 */
	public void removeKeshikomiBefore(EigyoDate date) {
		this.keshikomiSet.removeIf(k -> {
			return k.getDate().isBefore(date);
		});
	}

	// getter
	/**
	 * 入金部署営業日取得.
	 * 
	 * @return 入金部署営業日
	 */
	public EigyoDate getNyukinBushoEigyoDate() {
		return this.nyukin.getBusho().getEigyoDate();
	}

	/**
	 * 売掛を取得する
	 * 
	 * @return 売掛
	 */
	public Set<Urikake> getUrikakeSet() {
		Set<Keshikomi> keshikomiSet = this.getKeshikomiSet();
		return keshikomiSet.stream().map(Keshikomi::getUrikake).collect(Collectors.toSet());
	}

	/**
	 * 消込日 = 引数.営業日の売掛を取得する
	 * 
	 * @param date
	 *            営業日
	 * @return 売掛
	 */
	public Set<Urikake> getUrikakeSetOf(EigyoDate date) {
		Set<Keshikomi> keshikomiSet = this.getKeshikomiSet();
		return keshikomiSet.stream().filter(k -> {
			return k.getDate().equals(date);
		}).map(Keshikomi::getUrikake).collect(Collectors.toSet());
	}

	// override
	@Override
	public void validate() throws SystemException {
		keshikomiSet.forEach(keshikomi -> {
			if (!keshikomi.getNyukin().equals(this.nyukin)) {
				throw new SystemException("異なる入金が組み込まれています。 : " + keshikomi.getNyukin());
			}
		});
	}

	@Override
	public int hashCode() {
		return generateHashCode(this.nyukin);
	}

	@Override
	protected boolean equals(DomainAggregation other) {
		NyukinKeshikomi o = (NyukinKeshikomi) other;
		return nyukin.equals(o.nyukin);
	}
}
