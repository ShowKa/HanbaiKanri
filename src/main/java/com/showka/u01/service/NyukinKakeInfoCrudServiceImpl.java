package com.showka.u01.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.showka.domain.NyukinKakeInfoDomain;
import com.showka.entity.MNyukinKakeInfo;
import com.showka.repository.i.MNyukinKakeInfoRepository;
import com.showka.u01.service.i.NyukinKakeInfoCrudService;

/**
 * 入金掛情報 CRUD Service
 * 
 * @author ShowKa
 *
 */
@Service
@Transactional
public class NyukinKakeInfoCrudServiceImpl implements NyukinKakeInfoCrudService {

	@Autowired
	private MNyukinKakeInfoRepository repo;

	@Override
	public void save(NyukinKakeInfoDomain domain) {

		// entity
		MNyukinKakeInfo e = new MNyukinKakeInfo();
		e.setKokyakuId(domain.getKokyakuId());
		e.setNyukinDate(domain.getNyukinDate());
		e.setNyukinHohoKubun(domain.getNyukinHohoKubun().getCode());
		e.setNyukinTsukiKubun(domain.getNyukinTsukiKubun().getCode());
		e.setShimebi(e.getShimebi());
		e.setVersion(domain.getVersion());

		// save
		repo.save(e);
	}

}
