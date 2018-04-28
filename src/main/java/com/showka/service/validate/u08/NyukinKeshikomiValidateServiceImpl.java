package com.showka.service.validate.u08;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.Keshikomi;
import com.showka.domain.NyukinKeshikomi;
import com.showka.domain.Urikake;
import com.showka.service.specification.u06.i.UrikakeKeshikomiSpecificationService;
import com.showka.service.validate.u08.i.NyukinKeshikomiValidateService;
import com.showka.system.exception.ValidateException;
import com.showka.value.AmountOfMoney;
import com.showka.value.EigyoDate;

@Service
public class NyukinKeshikomiValidateServiceImpl implements NyukinKeshikomiValidateService {

	@Autowired
	private UrikakeKeshikomiSpecificationService urikakeKeshikomiSpecificationService;

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
		Set<Keshikomi> keshikomiList = nyukinKeshikomi.getKeshikomiSet();
		keshikomiList.forEach(keshikomi -> {
			AmountOfMoney keshikomiKingaku = keshikomi.getKingaku();
			Urikake urikake = keshikomi.getUrikake();
			AmountOfMoney zandaka = urikakeKeshikomiSpecificationService.getZandakaOfExcludingSpecificKeshikomi(urikake,
					keshikomi);
			if (keshikomiKingaku.greaterThan(zandaka)) {
				throw new ValidateException("消込金額が売掛金額を上回っています。");
			}
		});
	}

	/**
	 * エラー: 1つの売掛への消込が、2つ以上存在する
	 * 
	 * <pre>
	 * 同じ日に同じ売掛に対する消込を2つ以上行っていたらエラーとする。
	 * </pre>
	 */
	void validateUrikakeDuplication(NyukinKeshikomi nyukinKeshikomi) throws ValidateException {
		// 消込セット
		Set<Keshikomi> keshikomiSet = nyukinKeshikomi.getKeshikomiSet();
		// 消込セットを日付でグルーピング
		Map<EigyoDate, List<Keshikomi>> keshikomiGroupByDate = keshikomiSet.stream()
				.collect(Collectors.groupingBy(Keshikomi::getDate));
		// さらに売掛ごとにグルーピング
		keshikomiGroupByDate.values().stream().forEach(_keshikomiList -> {
			Map<Urikake, List<Keshikomi>> keshikomiGroupByUrikake = _keshikomiList.stream()
					.collect(Collectors.groupingBy(Keshikomi::getUrikake));
			keshikomiGroupByUrikake.values().forEach(__keshikomiList -> {
				// 同一売掛に2つ以上の消込が存在する。
				if (__keshikomiList.size() > 1) {
					throw new ValidateException("同一の売掛が重複して消し込まれています。");
				}
			});
		});
	}
}