package com.showka.service.validator.u11;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.u11.ShohinIdo;
import com.showka.domain.u11.ShohinZaiko;
import com.showka.domain.z00.Busho;
import com.showka.domain.z00.Shohin;
import com.showka.service.query.u11.i.ShohinZaikoQuery;
import com.showka.service.validator.u11.i.ShohinZaikoValidator;
import com.showka.system.exception.specification.MinusZaikoException;
import com.showka.system.exception.specification.MinusZaikoException.MinusZaiko;
import com.showka.value.EigyoDate;

@Service
public class ShohinZaikoValidatorImpl implements ShohinZaikoValidator {

	@Autowired
	private ShohinZaikoQuery shohinZaikoQuery;

	@Override
	public void validateMinusZaiko(ShohinIdo shohinIdo) throws MinusZaikoException {
		// 部署
		Busho busho = shohinIdo.getBusho();
		// 商品移動日
		EigyoDate date = shohinIdo.getDate();
		// 商品Set
		Set<Shohin> shohinSet = shohinIdo.getShohinSet();
		// マイナス在庫List
		List<MinusZaiko> minusZaikoList = shohinSet.stream().map(s -> {
			ShohinZaiko zaiko = shohinZaikoQuery.get(busho, date, s);
			ShohinZaiko merged = zaiko.merge(shohinIdo);
			if (merged.isNegative()) {
				return new MinusZaiko(s, zaiko.getNumber(), merged.getNumber());
			}
			return null;
		}).filter(mz -> mz != null).collect(Collectors.toList());
		// 例外
		if (!minusZaikoList.isEmpty()) {
			throw new MinusZaikoException(minusZaikoList);
		}
	}

	@Override
	public void validateMinusZaikoForDelete(ShohinIdo shohinIdo) throws MinusZaikoException {
		// 部署
		Busho busho = shohinIdo.getBusho();
		// 商品移動日
		EigyoDate date = shohinIdo.getDate();
		// 商品Set
		Set<Shohin> shohinSet = shohinIdo.getShohinSet();
		// マイナス在庫List
		List<MinusZaiko> minusZaikoList = shohinSet.stream().map(s -> {
			ShohinZaiko zaiko = shohinZaikoQuery.get(busho, date, s);
			ShohinZaiko removed = zaiko.remove(shohinIdo);
			if (removed.isNegative()) {
				return new MinusZaiko(s, zaiko.getNumber(), removed.getNumber());
			}
			return null;
		}).filter(mz -> mz != null).collect(Collectors.toList());
		// 例外
		if (!minusZaikoList.isEmpty()) {
			throw new MinusZaikoException(minusZaikoList);
		}
	}
}
