package com.showka.service.persistence.u05;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.builder.UriageCancelBuilder;
import com.showka.domain.u05.Uriage;
import com.showka.domain.u05.UriageCancel;
import com.showka.entity.CUriage;
import com.showka.entity.TUriagePK;
import com.showka.repository.i.CUriageRepository;
import com.showka.service.persistence.u05.i.UriageCancelPersistence;
import com.showka.service.persistence.u05.i.UriagePersistence;

@Service
public class UriageCancelPersistenceImpl implements UriageCancelPersistence {

	@Autowired
	private CUriageRepository repo;

	@Autowired
	private UriagePersistence uriagePersistence;

	@Override
	public void save(Uriage domain) {
		// get
		String uriageId = domain.getRecordId();
		Optional<CUriage> canceldUriageEntity = repo.findById(uriageId);
		CUriage e = canceldUriageEntity.orElse(new CUriage());

		// set entity
		e.setUriageId(uriageId);
		String recordId = canceldUriageEntity.isPresent() ? e.getRecordId() : UUID.randomUUID().toString();
		e.setRecordId(recordId);

		// 排他制御対象外

		// save
		repo.save(e);
	}

	@Override
	public UriageCancel getDomain(String uriageId) {
		// get entity
		CUriage e = repo.findById(uriageId).get();

		// get uriage
		TUriagePK pk = new TUriagePK();
		pk.setKokyakuId(e.getUriage().getPk().getKokyakuId());
		pk.setDenpyoNumber(e.getUriage().getPk().getDenpyoNumber());
		Uriage uriage = uriagePersistence.getDomain(pk);

		// set builder
		UriageCancelBuilder b = new UriageCancelBuilder();
		b.withUriageDomain(uriage);

		// common column
		b.withVersion(e.getVersion());
		b.withRecordId(e.getRecordId());

		return b.build();
	}

}
