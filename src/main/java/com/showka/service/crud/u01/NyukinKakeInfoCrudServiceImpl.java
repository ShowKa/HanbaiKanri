package com.showka.service.crud.u01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.showka.domain.NyukinKakeInfoDomain;
import com.showka.entity.MNyukinKakeInfo;
import com.showka.repository.i.MNyukinKakeInfoRepository;
import com.showka.service.crud.u01.i.NyukinKakeInfoCrudService;

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

	@Override
	public void delete(String pk, Integer version) {
		// TODO Auto-generated method stub
	}

	@Override
	public NyukinKakeInfoDomain getDomain(String pk) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exsists(String pk) {
		// TODO Auto-generated method stub
		return false;
	}

}
