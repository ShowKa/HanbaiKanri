package com.showka.service.validate.u08;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.showka.domain.Keshikomi;
import com.showka.domain.NyukinKeshikomi;
import com.showka.domain.Urikake;
import com.showka.service.validate.u08.i.NyukinKeshikomiValidateService;
import com.showka.system.exception.ValidateException;
import com.showka.value.AmountOfMoney;

@Service
public class NyukinKeshikomiValidateServiceImpl implements NyukinKeshikomiValidateService {

	@Override
	public void validate(NyukinKeshikomi nyukinKeshikomi) throws ValidateException {
		this.validateKeshikomiKingakuGokei(nyukinKeshikomi);
		this.validateKeshikomiKingaku(nyukinKeshikomi);
		this.validateUrikakeDuplication(nyukinKeshikomi);
	}

	/**
	 * エラー： 消込.金額の合計 > 入金.金額
	 */
	void validateKeshikomiKingakuGokei(NyukinKeshikomi nyukinKeshikomi) throws ValidateException {
		AmountOfMoney keshikomiKingakuGokei = nyukinKeshikomi.getKeshikomiKingakuGokei();
		AmountOfMoney nyukinKingaku = nyukinKeshikomi.getNyukin().getKingaku();
		if (keshikomiKingakuGokei.greaterThan(nyukinKingaku)) {
			throw new ValidateException("消込金額の合計が入金金額を上回っています。");
		}
	}

	/**
	 * エラー: 消込.金額 > 売掛.残高
	 */
	void validateKeshikomiKingaku(NyukinKeshikomi nyukinKeshikomi) throws ValidateException {
		Map<Keshikomi, Urikake> keshikomiMap = nyukinKeshikomi.getKeshikomiMap();
		keshikomiMap.forEach((keshikomi, urikake) -> {
			AmountOfMoney keshikomiKingaku = keshikomi.getKingaku();
			AmountOfMoney zandaka = urikake.getZandaka();
			if (keshikomiKingaku.greaterThan(zandaka)) {
				throw new ValidateException("消込金額が売掛金額を上回っています。");
			}
		});
	}

	/**
	 * エラー: 1つの売掛への消込が、2つ以上存在する
	 */
	void validateUrikakeDuplication(NyukinKeshikomi nyukinKeshikomi) throws ValidateException {
		Map<Keshikomi, Urikake> keshikomiMap = nyukinKeshikomi.getKeshikomiMap();
		Set<Urikake> urikakeSet = keshikomiMap.values().stream().collect(Collectors.toSet());
		if (urikakeSet.size() < keshikomiMap.size()) {
			// 重複があれば売掛のセットは消込マップよりサイズが小さくなる
			throw new ValidateException("同一の売掛が重複して消し込まれています。");
		}
	}
}
