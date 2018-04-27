package com.showka.domain;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.showka.system.exception.SystemException;
import com.showka.value.AmountOfMoney;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UrikakeKeshikomi extends DomainBase {

	/** 売掛. */
	private Urikake urikake;

	/** 消込リスト. */
	private List<Keshikomi> keshikomiList;

	// public
	/**
	 * 消込金額合計取得
	 * 
	 * @return 消込.金額の合計
	 */
	public AmountOfMoney getKeshikomiKingakuGokei() {
		int keshikomiKingakuGokei = keshikomiList.stream().mapToInt(k -> {
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

	// override
	@Override
	public void validate() throws SystemException {
		Set<Urikake> urikakeSet = keshikomiList.stream().map(keshikomi -> {
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
	protected boolean equals(DomainBase other) {
		UrikakeKeshikomi o = (UrikakeKeshikomi) other;
		return urikake.equals(o.urikake);
	}

	@Override
	public int hashCode() {
		return urikake.hashCode();
	}
}
