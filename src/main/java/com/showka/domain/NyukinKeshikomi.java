package com.showka.domain;

import java.util.Map;

import com.showka.system.exception.SystemException;
import com.showka.value.AmountOfMoney;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
// TODO レコードID+バージョンが不要（SubDomainみたいな抽象クラスが必要）
public class NyukinKeshikomi extends DomainBase {

	/** 入金. */
	private Nyukin nyukin;

	/** 消込リスト. */
	private Map<Keshikomi, Urikake> keshikomiMap;

	// public method
	/**
	 * 消込金額の合計取得.
	 * 
	 * @return 消込.金額の合計
	 */
	public AmountOfMoney getKeshikomiKingakuGokei() {
		int amount = keshikomiMap.entrySet().stream().mapToInt(entry -> {
			Keshikomi k = entry.getKey();
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

	// override
	@Override
	public void validate() throws SystemException {
		// nothing to do
	}

	@Override
	protected boolean equals(DomainBase other) {
		NyukinKeshikomi o = (NyukinKeshikomi) other;
		return nyukin.equals(o.nyukin);
	}

	@Override
	public int hashCode() {
		return generateHashCode(this.nyukin);
	}
}
