package com.showka.service.validate.u01;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.u01.Kokyaku;
import com.showka.domain.u05.Uriage;
import com.showka.kubun.HanbaiKubun;
import com.showka.kubun.KokyakuKubun;
import com.showka.repository.i.MKokyakuRepository;
import com.showka.service.crud.u05.i.UriageCrudService;
import com.showka.service.validate.u01.i.KokyakuValidateService;
import com.showka.system.exception.AlreadyExistsException;
import com.showka.system.exception.CanNotUpdateOrDeleteException;
import com.showka.system.exception.IncorrectKubunException;
import com.showka.system.exception.NotExistException;
import com.showka.system.exception.ValidateException;

/**
 * 顧客 Validate Service
 *
 * @author 25767
 *
 */
@Service
public class KokyakuValidateServiceImpl implements KokyakuValidateService {

	@Autowired
	private MKokyakuRepository repo;

	@Autowired
	private NyukinKakeInfoValidateServiceImpl nyukinKakeInfoValidateService;

	@Autowired
	private UriageCrudService uriageCrudService;

	@Override
	public void validateForRefer(String kokyakuCode) throws ValidateException {
		if (!repo.existsById(kokyakuCode)) {
			throw new NotExistException("顧客コード", kokyakuCode);
		}
	}

	@Override
	public void validateForRegister(Kokyaku domain) throws ValidateException {
		if (repo.existsById(domain.getCode())) {
			throw new AlreadyExistsException("顧客コード", domain.getCode());
		}
	}

	@Override
	public void validate(Kokyaku domain) throws ValidateException {
		// 販売区分
		HanbaiKubun hanbaiKubun = domain.getHanbaiKubun();
		if (domain.getKokyakuKubun() == KokyakuKubun.個人 && hanbaiKubun == HanbaiKubun.掛売) {
			throw new IncorrectKubunException("販売区分", HanbaiKubun.掛売, "個人顧客は掛売できません");
		}
		if (hanbaiKubun == HanbaiKubun.掛売) {
			nyukinKakeInfoValidateService.validate(domain.getNyukinKakeInfo().get());
		}
	}

	@Override
	public void validateForDelete(String kokyakuCode) throws ValidateException {
		List<Uriage> uriageList = uriageCrudService.getUriageOfKokyaku(kokyakuCode);
		if (!uriageList.isEmpty()) {
			throw new CanNotUpdateOrDeleteException("売上が登録済み");
		}
	}
}
