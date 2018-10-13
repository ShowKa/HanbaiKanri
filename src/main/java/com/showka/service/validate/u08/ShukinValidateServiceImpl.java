package com.showka.service.validate.u08;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.u01.Kokyaku;
import com.showka.domain.u08.Shukin;
import com.showka.service.crud.u08.i.ShukinCrudService;
import com.showka.service.specification.u08.i.NyukinKeshikomiSpecificationService;
import com.showka.service.validate.u08.i.ShukinValidateService;
import com.showka.system.exception.CanNotDeleteException;
import com.showka.system.exception.CanNotUpdateException;
import com.showka.system.exception.DuprecatedException;
import com.showka.system.exception.NotAllowedNumberException;
import com.showka.system.exception.NotMatchedException;
import com.showka.value.EigyoDate;

@Service
public class ShukinValidateServiceImpl implements ShukinValidateService {

	@Autowired
	private ShukinCrudService shukinCrudService;

	@Autowired
	private NyukinKeshikomiSpecificationService nyukinKeshikomiSpecificationService;

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
		boolean exists = shukinCrudService.exists(kokyaku, nyukinDate, denpyoNumber);
		if (exists) {
			throw new DuprecatedException("顧客", "入金日", "伝票番号");
		}
	}

	@Override
	public void validateForUpdate(Shukin shukin) throws NotMatchedException, CanNotUpdateException {
		// 担当社員の所属を検証
		// ただし担当社員が更新された場合のみ
		Shukin old = shukinCrudService.getDomain(shukin.getRecordId());
		if (!old.getTantoShain().equals(shukin.getTantoShain())) {
			this.validateTantoShainShozokuBusho(shukin);
		}
		// 消込を実施済みの場合、エラー
		String nyukinId = shukin.getNyukinId();
		boolean hasKeshikomi = nyukinKeshikomiSpecificationService.hasKeshikomi(nyukinId);
		if (hasKeshikomi) {
			throw new CanNotUpdateException("消込済みのため");
		}
	}

	@Override
	public void validateForDelete(Shukin shukin) throws CanNotDeleteException {
		// 消込を実施済みの場合、エラー
		String nyukinId = shukin.getNyukinId();
		boolean hasKeshikomi = nyukinKeshikomiSpecificationService.hasKeshikomi(nyukinId);
		if (hasKeshikomi) {
			throw new CanNotDeleteException("消込済みのため");
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
