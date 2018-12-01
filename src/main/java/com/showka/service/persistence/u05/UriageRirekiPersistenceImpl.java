package com.showka.service.persistence.u05;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.u05.Uriage;
import com.showka.entity.RUriage;
import com.showka.entity.RUriagePK;
import com.showka.repository.i.RUriageRepository;
import com.showka.service.crud.u05.i.UriageRirekiMeisaiCrud;
import com.showka.service.persistence.u05.i.UriageRirekiPersistence;

@Service
public class UriageRirekiPersistenceImpl implements UriageRirekiPersistence {

	@Autowired
	private RUriageRepository repo;

	@Autowired
	private UriageRirekiMeisaiCrud uriageRirekiMeisaiPersistence;

	// 排他制御省略
	@Override
	public void save(Uriage domain) {
		// pk
		RUriagePK pk = new RUriagePK();
		String uriageId = domain.getRecordId();
		pk.setUriageId(uriageId);
		pk.setKeijoDate(domain.getKeijoDate().toDate());
		// 売上履歴Entity
		Optional<RUriage> _e = repo.findById(pk);
		RUriage e = _e.orElse(new RUriage());
		e.setHanbaiKubun(domain.getHanbaiKubun().getCode());
		e.setPk(pk);
		e.setShohizeiritsu(domain.getShohizeiritsu().getRate().doubleValue());
		e.setUriageDate(domain.getUriageDate().toDate());
		// set common column
		String recordId = _e.isPresent() ? _e.get().getRecordId() : UUID.randomUUID().toString();
		e.setRecordId(recordId);
		// save
		repo.save(e);
		// 明細
		uriageRirekiMeisaiPersistence.overrideList(recordId, domain.getUriageMeisai());
	}

	@Override
	public void delete(RUriagePK pk) {
		// entity
		RUriage rireki = repo.getOne(pk);
		// 明細削除
		uriageRirekiMeisaiPersistence.deleteAll(rireki.getRecordId());
		// 削除
		repo.delete(rireki);
	}
}
