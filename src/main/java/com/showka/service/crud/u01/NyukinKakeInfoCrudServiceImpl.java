package com.showka.service.crud.u01;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.NyukinKakeInfoDomain;
import com.showka.domain.builder.NyukinKakeInfoDomainBuilder;
import com.showka.entity.MNyukinKakeInfo;
import com.showka.kubun.NyukinHohoKubun;
import com.showka.kubun.NyukinTsukiKubun;
import com.showka.kubun.i.Kubun;
import com.showka.repository.i.MNyukinKakeInfoRepository;
import com.showka.service.crud.u01.i.NyukinKakeInfoCrudService;

/**
 * 入金掛情報 CRUD Service
 *
 * @author ShowKa
 *
 */
@Service
public class NyukinKakeInfoCrudServiceImpl implements NyukinKakeInfoCrudService {

	@Autowired
	private MNyukinKakeInfoRepository repo;

	@Override
	public void save(NyukinKakeInfoDomain domain) {

		// entity
		Optional<MNyukinKakeInfo> entity = repo.findById(domain.getKokyakuId());
		MNyukinKakeInfo e = entity.orElse(new MNyukinKakeInfo());

		// set columns
		e.setKokyakuId(domain.getKokyakuId());
		e.setNyukinDate(domain.getNyukinDate());
		e.setNyukinHohoKubun(domain.getNyukinHohoKubun().getCode());
		e.setNyukinTsukiKubun(domain.getNyukinTsukiKubun().getCode());
		e.setShimebi(domain.getShimeDate());

		// set record_id & version
		e.setRecordId(domain.getRecordId());
		e.setVersion(domain.getVersion());

		// save
		repo.save(e);
	}

	@Override
	public void delete(String kokyakuId, Integer version) {
		MNyukinKakeInfo target = new MNyukinKakeInfo();
		target.setKokyakuId(kokyakuId);
		target.setVersion(version);
		repo.delete(target);
	}

	@Override
	public NyukinKakeInfoDomain getDomain(String kokyakuId) {
		// get entity
		MNyukinKakeInfo e = repo.getOne(kokyakuId);

		// set domain builder
		NyukinKakeInfoDomainBuilder b = new NyukinKakeInfoDomainBuilder();
		b.withKokyakuId(e.getKokyakuId());
		b.withNyukinDate(e.getNyukinDate());
		b.withNyukinHohoKubun(Kubun.get(NyukinHohoKubun.class, e.getNyukinHohoKubun()));
		b.withNyukinTsukiKubun(Kubun.get(NyukinTsukiKubun.class, e.getNyukinTsukiKubun()));
		b.withRecordId(e.getRecordId());
		b.withShimeDate(e.getShimebi());
		b.withVersion(e.getVersion());

		// build domain
		return b.build();
	}

	@Override
	public boolean exsists(String kokyakuId) {
		if (kokyakuId == null) {
			return false;
		}
		return repo.existsById(kokyakuId);
	}

	@Override
	public void delete(NyukinKakeInfoDomain domain) {
		delete(domain.getKokyakuId(), domain.getVersion());

	}

}
