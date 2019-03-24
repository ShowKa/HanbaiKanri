package com.showka.service.validator.u11;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.u11.Nyuka;
import com.showka.domain.u11.ShohinIdo;
import com.showka.domain.z00.Busho;
import com.showka.service.crud.u11.i.NyukaTeiseiCrud;
import com.showka.service.query.u11.i.ShohinTanaoroshiQuery;
import com.showka.service.validator.u11.i.NyukaTeiseiValidator;
import com.showka.service.validator.u11.i.ShohinZaikoValidator;
import com.showka.system.exception.validate.CanNotUpdateOrDeleteException;
import com.showka.value.EigyoDate;
import com.showka.value.TheTimestamp;

@Service
public class NyukaTeiseiValidatorImpl implements NyukaTeiseiValidator {

	@Autowired
	private ShohinTanaoroshiQuery shohinTanaoroshiQuery;

	@Autowired
	private ShohinZaikoValidator shohinZaikoValidator;

	@Autowired
	private NyukaTeiseiCrud nyukaTeiseiCrud;

	@Override
	public void validateForTeisei(Nyuka nyuka, String teiseiShohinIdoId) throws CanNotUpdateOrDeleteException {
		// 入荷日検証
		this.validateNyukaDate(nyuka);
		// 棚卸検証
		this.validateTanaoroshi(nyuka);
		// 在庫検証
		ShohinIdo s = nyuka.getShohinIdoById(teiseiShohinIdoId);
		shohinZaikoValidator.validateMinusZaiko(s);
	}

	@Override
	public void validateForTeiseiUpdate(Nyuka nyuka, String teiseiShohinIdoId) throws CanNotUpdateOrDeleteException {
		// 訂正済検証
		this.validateAlreadyDone(nyuka, teiseiShohinIdoId);
		// 在庫検証
		ShohinIdo s = nyuka.getShohinIdoById(teiseiShohinIdoId);
		shohinZaikoValidator.validateMinusZaiko(s);
	}

	@Override
	public void validateForTeiseiDelete(Nyuka nyuka, String teiseiShohinIdoId) throws CanNotUpdateOrDeleteException {
		// 訂正済検証
		this.validateAlreadyDone(nyuka, teiseiShohinIdoId);
	}

	/**
	 * 入荷日検証.
	 */
	void validateNyukaDate(Nyuka nyuka) {
		EigyoDate nyukaDate = nyuka.getNyukaDate();
		EigyoDate eigyoDate = nyuka.getBusho().getEigyoDate();
		if (nyukaDate.equals(eigyoDate)) {
			throw new CanNotUpdateOrDeleteException("締処理が未実施のため本伝票は訂正処理の対象外です");
		}
	}

	/**
	 * 棚卸検証.
	 */
	void validateTanaoroshi(Nyuka nyuka) {
		Busho busho = nyuka.getBusho();
		TheTimestamp start = nyuka.getNyukaShohinIdo().getTimestamp();
		boolean done = shohinTanaoroshiQuery.doneBetween(busho, start);
		if (done) {
			throw new CanNotUpdateOrDeleteException("入荷日以降に棚卸が実施済");
		}
	}

	/**
	 * 訂正済検証.
	 */
	void validateAlreadyDone(Nyuka nyuka, String teiseiShohinIdoId) {
		List<ShohinIdo> teiseiShohinIdoList = nyukaTeiseiCrud.getAll(nyuka.getRecordId());
		boolean done = teiseiShohinIdoList.stream().map(s -> s.getRecordId()).collect(Collectors.toList()).contains(
				teiseiShohinIdoId);
		if (!done) {
			throw new CanNotUpdateOrDeleteException("当日に入荷訂正を行っていないため本伝票は入荷訂正の更新・削除処理の対象外です");
		}
	}
}
