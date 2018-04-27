package com.showka.domain;

import java.util.List;

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
	private List<Keshikomi> keshikomiList;

	// public method
	/**
	 * 消込金額の合計取得.
	 * 
	 * @return 消込.金額の合計
	 */
	public AmountOfMoney getKeshikomiKingakuGokei() {
		int amount = keshikomiList.stream().mapToInt(k -> {
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
		keshikomiList.forEach(keshikomi -> {
			if (!keshikomi.getNyukin().equals(this.nyukin)) {
				throw new SystemException("異なる入金が組み込まれています。 : " + keshikomi.getNyukin());
			}
		});
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
