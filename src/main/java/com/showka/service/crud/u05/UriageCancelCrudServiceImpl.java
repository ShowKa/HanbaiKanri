package com.showka.service.crud.u05;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.UriageCancelDomain;
import com.showka.domain.UriageDomain;
import com.showka.domain.builder.UriageCancelDomainBuilder;
import com.showka.entity.CUriage;
import com.showka.entity.TUriagePK;
import com.showka.repository.i.CUriageRepository;
import com.showka.service.crud.u05.i.UriageCancelCrudService;
import com.showka.service.crud.u05.i.UriageCrudService;

@Service
public class UriageCancelCrudServiceImpl implements UriageCancelCrudService {

	@Autowired
	private CUriageRepository repo;

	@Autowired
	private UriageCrudService uriageCrudService;

	@Override
	public void save(UriageDomain domain) {
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
	public UriageCancelDomain getDomain(String uriageId) {
		// get entity
		CUriage e = repo.findById(uriageId).get();

		// get uriage
		TUriagePK pk = new TUriagePK();
		pk.setKokyakuId(e.getUriage().getPk().getKokyakuId());
		pk.setDenpyoNumber(e.getUriage().getPk().getDenpyoNumber());
		UriageDomain uriage = uriageCrudService.getDomain(pk);

		// set builder
		UriageCancelDomainBuilder b = new UriageCancelDomainBuilder();
		b.withUriageDomain(uriage);

		// common column
		b.withVersion(e.getVersion());
		b.withRecordId(e.getRecordId());

		return b.build();
	}

}
