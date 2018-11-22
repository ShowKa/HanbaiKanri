package com.showka.service.validator.u08;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.u01.Kokyaku;
import com.showka.domain.u08.Shukin;
import com.showka.service.persistence.u08.i.ShukinPersistence;
import com.showka.service.query.u08.i.NyukinKeijoQuery;
import com.showka.service.query.u08.i.NyukinKeshikomiQuery;
import com.showka.service.validator.u08.i.ShukinValidator;
import com.showka.system.exception.CanNotUpdateOrDeleteException;
import com.showka.system.exception.DuprecatedException;
import com.showka.system.exception.NotAllowedNumberException;
import com.showka.system.exception.NotMatchedException;
import com.showka.value.EigyoDate;

@Service
public class ShukinValidatorImpl implements ShukinValidator {

	@Autowired
	private NyukinKeijoQuery nyukinKeijoQuery;

	@Autowired
	private ShukinPersistence shukinPersistence;

	@Autowired
	private NyukinKeshikomiQuery nyukinKeshikomiSpecificationService;

	@Override
	public void validate(Shukin shukin) throws NotAllowedNumberException {
		if (shukin.getKingaku().lesserThanEquals(0)) {
			throw new NotAllowedNumberException("金額", BigDecimal.ONE, null);
		}
	}

	@Override
	public void validateForRegister(Shukin shukin) throws NotMatchedException, DuprecatedException {
		// 担当社員の所属を検証
		this.validateTantoShainShozokuBusho(shukin);
		// 重複する集金が登録済の場合、エラー
		// 重複条件：顧客、入金日、伝票番号
		Kokyaku kokyaku = shukin.getKokyaku();
		EigyoDate nyukinDate = shukin.getDate();
		String denpyoNumber = shukin.getDenpyoNumber();
		boolean exists = shukinPersistence.exists(kokyaku, nyukinDate, denpyoNumber);
		if (exists) {
			throw new DuprecatedException("顧客", "入金日", "伝票番号");
		}
	}

	@Override
	public void validateForUpdate(Shukin shukin) throws NotMatchedException, CanNotUpdateOrDeleteException {
		// 担当社員の所属を検証
		// ただし担当社員が更新された場合のみ
		String nyukinId = shukin.getNyukinId();
		Shukin old = shukinPersistence.getDomain(nyukinId);
		if (!old.getTantoShain().equals(shukin.getTantoShain())) {
			this.validateTantoShainShozokuBusho(shukin);
		}
		// 計上済みの場合エラー
		this.validateKeijo(shukin);
		// 消込を実施済みの場合、エラー
		this.validateKeshikomi(shukin);
	}

	@Override
	public void validateForDelete(Shukin shukin) throws CanNotUpdateOrDeleteException {
		// 計上済みの場合エラー
		this.validateKeijo(shukin);
		// 消込を実施済みの場合、エラー
		this.validateKeshikomi(shukin);
	}

	/**
	 * 計上済み検証.
	 * 
	 * @param shukin
	 *            集金
	 * @throws CanNotUpdateOrDeleteException
	 *             計上済みの場合、更新・削除不可
	 */
	void validateKeijo(Shukin shukin) throws CanNotUpdateOrDeleteException {
		String nyukinId = shukin.getNyukinId();
		boolean keijoDone = nyukinKeijoQuery.keijoDone(nyukinId);
		if (keijoDone) {
			throw new CanNotUpdateOrDeleteException("計上済みのため");
		}
	}

	/**
	 * 消込済み検証.
	 * 
	 * @param shukin
	 *            集金
	 * @throws CanNotUpdateOrDeleteException
	 *             更新削除不可例外
	 */
	void validateKeshikomi(Shukin shukin) throws CanNotUpdateOrDeleteException {
		String nyukinId = shukin.getNyukinId();
		boolean hasKeshikomi = nyukinKeshikomiSpecificationService.hasKeshikomi(nyukinId);
		if (hasKeshikomi) {
			throw new CanNotUpdateOrDeleteException("消込済みのため");
		}
	}

	/**
	 * 担当社員の所属部署検証
	 * 
	 * @param shukin
	 *            集金
	 */
	void validateTantoShainShozokuBusho(Shukin shukin) throws NotMatchedException {
		// 集金.担当社員.所属部署 != 集金.入金部署の場合、エラー
		if (!shukin.getTantoShain().getShozokuBusho().equals(shukin.getBusho())) {
			throw new NotMatchedException("集金担当社員の所属部署", "入金部署");
		}
	}
}
