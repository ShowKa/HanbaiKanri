package com.showka.service.query.u05.i;

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
import com.showka.service.crud.u01.i.KokyakuCrudService;
import com.showka.service.crud.u05.i.UriageCrudService;

// TODO 売上PersistenceでgetDomain(Example exampleつくる？）
@Service
public class UriageQueryImpl implements UriageQuery {

	@Autowired
	private TUriageRepository repo;

	@Autowired
	private KokyakuCrudService kokyakuPersistence;

	@Autowired
	private UriageCrudService uriagePersistence;

	@Override
	public List<Uriage> getUriageOfKokyaku(String kokyakuCode) {
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
