package com.showka.service.validator.u11;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.u11.Nyuka;
import com.showka.domain.u11.ShohinIdo;
import com.showka.domain.u11.ShohinIdoMeisai;
import com.showka.domain.z00.Shohin;
import com.showka.service.validator.u11.i.NyukaValidator;
import com.showka.service.validator.u11.i.ShohinZaikoValidator;
import com.showka.system.exception.validate.CanNotUpdateOrDeleteException;
import com.showka.system.exception.validate.MeisaiUniqueConstraintException;
import com.showka.system.exception.validate.NotAllowedNumberException;
import com.showka.value.EigyoDate;

@Service
public class NyukaValidatorImpl implements NyukaValidator {

	/** 商品在庫整合性検証 */
	@Autowired
	private ShohinZaikoValidator shohinZaikoValidator;

	@Override
	public void validate(Nyuka nyuka) throws NotAllowedNumberException, MeisaiUniqueConstraintException {
		// 入荷数
		this.validateNykaNumber(nyuka);
		// 商品重複
		this.validateShohin(nyuka);
	}

	@Override
	public void validateForUpdate(Nyuka nyuka) throws CanNotUpdateOrDeleteException {
		// 入荷日
		this.validateNyukaDate(nyuka);
		// マイナス在庫
		ShohinIdo shohinIDo = nyuka.getNyukaShohinIdo();
		shohinZaikoValidator.validateMinusZaiko(shohinIDo);
	}

	@Override
	public void validateForDelete(Nyuka nyuka) throws CanNotUpdateOrDeleteException {
		// 入荷日
		this.validateNyukaDate(nyuka);
		// マイナス在庫
		ShohinIdo shohinIDo = nyuka.getNyukaShohinIdo();
		shohinZaikoValidator.validateMinusZaikoForDelete(shohinIDo);
	}

	/**
	 * 商品重複検証.
	 * 
	 * @param nyuka
	 *            入荷
	 */
	void validateShohin(Nyuka nyuka) {
		// 商品の重複
		List<ShohinIdoMeisai> meisai = nyuka.getNyukaShohinIdo().getMeisai();
		Set<Shohin> shohinSet = nyuka.getShohinSet();
		if (meisai.size() != shohinSet.size()) {
			throw new MeisaiUniqueConstraintException("商品");
		}
	}

	/**
	 * 入荷数検証.
	 * 
	 * @param nyuka
	 *            入荷
	 */
	void validateNykaNumber(Nyuka nyuka) {
		// 入荷数
		List<ShohinIdoMeisai> meisai = nyuka.getNyukaShohinIdo().getMeisai();
		int minimumNumber = 1;
		meisai.forEach(m -> {
			boolean less = m.getNumber().intValue() < minimumNumber;
			if (less) {
				throw new NotAllowedNumberException("入荷数", minimumNumber);
			}
		});
	}

	/**
	 * 入荷日検証
	 * 
	 * @param nyuka
	 *            入荷
	 */
	void validateNyukaDate(Nyuka nyuka) {
		EigyoDate nyukaDate = nyuka.getNyukaDate();
		EigyoDate eigyoDate = nyuka.getBusho().getEigyoDate();
		if (!nyukaDate.isEqual(eigyoDate)) {
			throw new CanNotUpdateOrDeleteException("入荷日を過ぎているため");
		}
	}
}
