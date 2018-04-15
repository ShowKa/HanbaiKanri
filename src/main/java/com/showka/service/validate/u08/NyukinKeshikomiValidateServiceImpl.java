package com.showka.service.validate.u08;

import org.springframework.stereotype.Service;

import com.showka.domain.NyukinKeshikomi;
import com.showka.service.validate.u08.i.NyukinKeshikomiValidateService;
import com.showka.system.exception.ValidateException;
import com.showka.value.AmountOfMoney;

@Service
public class NyukinKeshikomiValidateServiceImpl implements NyukinKeshikomiValidateService {

	@Override
	public void validate(NyukinKeshikomi nyukinKeshikomi) {
		// 消込.金額の合計 > 入金.金額 の場合エラー
		AmountOfMoney keshikomiKingakuGokei = nyukinKeshikomi.getKeshikomiKingakuGokei();
		AmountOfMoney nyukinKingaku = nyukinKeshikomi.getNyukin().getKingaku();
		if (keshikomiKingakuGokei.greaterThan(nyukinKingaku)) {
			throw new ValidateException("消込金額が入金金額を上回っています");
		}
		// 消込.金額 > 売掛.残高 の場合エラー
		nyukinKeshikomi.getKeshikomiMap().forEach((keshikomi, urikake) -> {
			// TODO
			AmountOfMoney keshikomiKingaku = keshikomi.getKingaku();
			AmountOfMoney zandaka = urikake.getZandaka();
		});
	}
}
