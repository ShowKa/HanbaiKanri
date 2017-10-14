package com.showka.service.validate.u05;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.showka.domain.UriageDomain;
import com.showka.domain.UriageMeisaiDomain;
import com.showka.entity.TUriagePK;
import com.showka.repository.i.TUriageRepository;
import com.showka.service.validate.u05.i.UriageMeisaiValidateService;
import com.showka.service.validate.u05.i.UriageValidateService;
import com.showka.system.exception.AlreadyExistsException;
import com.showka.system.exception.EmptyException;
import com.showka.system.exception.ValidateException;

@Service
public class UriageValidateServiceImpl implements UriageValidateService {

	@Autowired
	private TUriageRepository uriageRepo;

	@Autowired
	private UriageMeisaiValidateService uriageMeisaiValidate;

	@Override
	public void validate(UriageDomain domain) throws ValidateException {

		List<UriageMeisaiDomain> meisaiList = domain.getUriageMeisai();
		if (CollectionUtils.isEmpty(meisaiList)) {
			throw new EmptyException("売上明細");
		}

		for (UriageMeisaiDomain meisai : meisaiList) {
			uriageMeisaiValidate.validate(meisai);
		}

	}

	@Override
	public void validateForRegister(UriageDomain domain) throws ValidateException {
		TUriagePK pk = new TUriagePK();
		pk.setDenpyoNumber(domain.getDenpyoNumber());
		pk.setKokyakuId(domain.getKokyaku().getRecordId());
		boolean exists = uriageRepo.existsById(pk);
		if (exists) {
			throw new AlreadyExistsException("売上伝票", domain.getDenpyoNumber());
		}
	}

}
