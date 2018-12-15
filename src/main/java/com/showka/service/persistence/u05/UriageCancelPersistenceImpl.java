package com.showka.service.persistence.u05;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.u05.Uriage;
import com.showka.entity.CUriage;
import com.showka.repository.i.CUriageRepository;
import com.showka.service.persistence.u05.i.UriageCancelPersistence;

@Service
public class UriageCancelPersistenceImpl implements UriageCancelPersistence {

	@Autowired
	private CUriageRepository repo;

	// 排他制御対象外
	@Override
	public void save(Uriage domain) {
		// get
		String uriageId = domain.getRecordId();
		Optional<CUriage> canceldUriageEntity = repo.findById(uriageId);
		CUriage e = canceldUriageEntity.orElse(new CUriage());
		// set entity
		e.setUriageId(uriageId);
		if (!canceldUriageEntity.isPresent()) {
			e.initRecordId();
		}
		// save
		repo.save(e);
	}
}
