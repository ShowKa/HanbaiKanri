package com.showka.service.specification.u05;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.Uriage;
import com.showka.entity.RUriage;
import com.showka.entity.RUriagePK;
import com.showka.repository.i.RUriageKeijoRepository;
import com.showka.repository.i.RUriageRepository;
import com.showka.service.specification.u05.i.UriageKeijoSpecificationService;

@Service
public class UriageKeijoSpecificationServiceImpl implements UriageKeijoSpecificationService {

	@Autowired
	private RUriageRepository rUriageRepository;

	@Autowired
	private RUriageKeijoRepository rUriageKeijoRepository;

	@Override
	public boolean isKeijoZumi(Uriage uriage) {
		// get 売上履歴
		RUriagePK pk = new RUriagePK();
		pk.setKeijoDate(uriage.getKeijoDate().toDate());
		pk.setUriageId(uriage.getRecordId());
		RUriage uriageRireki = rUriageRepository.getOne(pk);
		// exists 売上計上
		boolean exists = rUriageKeijoRepository.existsById(uriageRireki.getRecordId());
		return exists;
	}
}
