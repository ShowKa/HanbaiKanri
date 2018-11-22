package com.showka.service.query.u05;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.showka.domain.u01.Kokyaku;
import com.showka.domain.u05.Uriage;
import com.showka.entity.TUriage;
import com.showka.entity.TUriagePK;
import com.showka.repository.i.TUriageRepository;
import com.showka.service.persistence.u01.i.KokyakuPersistence;
import com.showka.service.persistence.u05.i.UriagePersistence;
import com.showka.service.query.u05.i.UriageQuery;

// TODO 売上PersistenceでgetDomain(Example exampleつくる？）
@Service
public class UriageQueryImpl implements UriageQuery {

	@Autowired
	private TUriageRepository repo;

	@Autowired
	private KokyakuPersistence kokyakuPersistence;

	@Autowired
	private UriagePersistence uriagePersistence;

	@Override
	public List<Uriage> get(String kokyakuCode) {
		// 顧客
		Kokyaku kokyaku = kokyakuPersistence.getDomain(kokyakuCode);
		TUriage e = new TUriage();
		TUriagePK pk = new TUriagePK();
		pk.setKokyakuId(kokyaku.getRecordId());
		e.setPk(pk);
		Example<TUriage> example = Example.of(e);
		List<TUriage> uriage = repo.findAll(example);
		return uriage.stream().map(u -> uriagePersistence.getDomain(u.getPk())).collect(Collectors.toList());
	}
}
