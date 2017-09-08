package com.showka.service.validate.u01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.KokyakuDomain;
import com.showka.kubun.HanbaiKubun;
import com.showka.kubun.KokyakuKubun;
import com.showka.repository.i.MKokyakuRepository;
import com.showka.service.validate.u01.i.KokyakuValidateService;
import com.showka.system.exception.AlreadyExistsException;
import com.showka.system.exception.IncorrectKubunException;
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

	@Override
	public void validateForRegister(KokyakuDomain domain) throws ValidateException {
		if (repo.existsById(domain.getCode())) {
			throw new AlreadyExistsException("顧客コード", domain.getCode());
		}
	}

	@Override
	public void validate(KokyakuDomain domain) throws ValidateException {

		if (domain.getKokyakuKubun() == KokyakuKubun.個人 && domain.getHanbaiKubun() == HanbaiKubun.掛売) {
			throw new IncorrectKubunException("販売区分", HanbaiKubun.掛売, "個人顧客は掛売できません");
		}
	}

}
