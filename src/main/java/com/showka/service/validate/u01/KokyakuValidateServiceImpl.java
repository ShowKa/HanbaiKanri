package com.showka.service.validate.u01;

import org.springframework.beans.factory.annotation.Autowired;

import com.showka.domain.KokyakuDomain;
import com.showka.kubun.HanbaiKubun;
import com.showka.kubun.KokyakuKubun;
import com.showka.repository.i.MKokyakuRepository;
import com.showka.service.validate.u01.i.KokyakuValidateService;
import com.showka.system.exception.ValidateException;

public class KokyakuValidateServiceImpl implements KokyakuValidateService {

	@Autowired
	private MKokyakuRepository repo;

	@Override
	public void validateForRegister(KokyakuDomain domain) throws ValidateException {
		if (repo.existsById(domain.getCode())) {
			throw new ValidateException("既に存在する顧客コードです。");
		}
	}

	@Override
	public void validate(KokyakuDomain domain) throws ValidateException {

		if (domain.getKokyakuKubun().isIncludedIn(KokyakuKubun.法人)) {
			return;
		}
		if (domain.getKokyakuKubun().isIncludedIn(KokyakuKubun.個人)) {
			if (domain.getHanbaiKubun().isIncludedIn(HanbaiKubun.現金)) {
				return;
			}
		}
		throw new ValidateException("個人顧客は現金払いのみです。");
	}

}
