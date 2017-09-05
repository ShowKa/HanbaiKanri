package com.showka.service.crud.u05;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.UriageMeisaiDomain;
import com.showka.entity.TUriageMeisai;
import com.showka.entity.TUriageMeisaiPK;
import com.showka.repository.i.TUriageMeisaiRepository;
import com.showka.service.crud.u05.i.TUriageMeisaiCrudService;

/**
 * 売上明細CrudeService
 * 
 * @author ShowKa
 *
 */
@Service
public class TUriageMeisaiCrudServiceImpl implements TUriageMeisaiCrudService {

	@Autowired
	private TUriageMeisaiRepository repo;

	@Override
	public void save(UriageMeisaiDomain domain) {
		// set primary key
		TUriageMeisaiPK pk = new TUriageMeisaiPK();
		pk.setUriageId(domain.getUriageId());
		pk.setMeisaiNumber(domain.getMeisaiNumber());

		// get entity
		TUriageMeisai e = repo.findById(pk).orElse(new TUriageMeisai());

		// override entity
		e.setPk(pk);
		e.setHanbaiNumber(domain.getHanbaiNumber());
		e.setHanbaiTanka(domain.getHanbaiTanka().intValue());
		e.setRecordId(domain.getRecordId());
		e.setShohinId(domain.getShohinDomain().getRecordId());
		e.setVersion(e.getVersion());

		// save
		repo.save(e);
	}

	@Override
	public void delete(TUriageMeisaiPK pk, Integer version) {
		TUriageMeisai entity = new TUriageMeisai();
		entity.setPk(pk);
		entity.setVersion(version);
		repo.delete(entity);
	}

	@Override
	public UriageMeisaiDomain getDomain(TUriageMeisaiPK pk) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exsists(TUriageMeisaiPK pk) {

		return false;
	}

}
