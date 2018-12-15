package com.showka.service.crud.u01;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.builder.NyukinKakeInfoBuilder;
import com.showka.domain.u01.NyukinKakeInfo;
import com.showka.entity.MNyukinKakeInfo;
import com.showka.kubun.NyukinHohoKubun;
import com.showka.kubun.NyukinTsukiKubun;
import com.showka.kubun.i.Kubun;
import com.showka.repository.i.MNyukinKakeInfoRepository;
import com.showka.service.crud.u01.i.NyukinKakeInfoCrud;

/**
 * 入金掛情報 CRUD Service
 *
 * @author ShowKa
 *
 */
@Service
public class NyukinKakeInfoCrudImpl implements NyukinKakeInfoCrud {

	@Autowired
	private MNyukinKakeInfoRepository repo;

	@Override
	public void save(String id, NyukinKakeInfo domain) {
		// entity
		Optional<MNyukinKakeInfo> entity = repo.findById(id);
		MNyukinKakeInfo e = entity.orElse(new MNyukinKakeInfo());
		// set columns
		e.setKokyakuId(id);
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
	public void delete(String kokyakuId) {
		MNyukinKakeInfo target = repo.getOne(kokyakuId);
		target.setKokyakuId(kokyakuId);
		repo.delete(target);
	}

	@Override
	public NyukinKakeInfo getDomain(String kokyakuId) {
		// get entity
		MNyukinKakeInfo e = repo.getOne(kokyakuId);
		// set domain builder
		NyukinKakeInfoBuilder b = new NyukinKakeInfoBuilder();
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
	public boolean exists(String kokyakuId) {
		return repo.existsById(kokyakuId);
	}

	@Override
	public void deleteIfExists(String kokyakuId) {
		if (this.exists(kokyakuId)) {
			this.delete(kokyakuId);
		}
	}
}
