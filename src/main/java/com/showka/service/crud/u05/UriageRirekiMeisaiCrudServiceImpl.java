package com.showka.service.crud.u05;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.UriageMeisaiDomain;
import com.showka.entity.RUriageMeisai;
import com.showka.entity.RUriageMeisaiPK;
import com.showka.repository.i.RUriageMeisaiRepository;
import com.showka.service.crud.u05.i.UriageRirekiMeisaiCrudService;

@Service
public class UriageRirekiMeisaiCrudServiceImpl implements UriageRirekiMeisaiCrudService {

	@Autowired
	private RUriageMeisaiRepository repo;

	@Override
	public List<UriageMeisaiDomain> getDomainList(String uriageId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(UriageMeisaiDomain domain) {
		RUriageMeisaiPK pk = new RUriageMeisaiPK();
		pk.setUriageId(domain.getUriageId());
		pk.setMeisaiNumber(domain.getMeisaiNumber());

		// get entity
		RUriageMeisai e = repo.findById(pk).orElse(new RUriageMeisai());

		// override entity
		e.setPk(pk);
		e.setHanbaiNumber(domain.getHanbaiNumber());
		e.setHanbaiTanka(domain.getHanbaiTanka().intValue());
		String recordId = e.getRecordId() != null ? e.getRecordId() : UUID.randomUUID().toString();
		e.setRecordId(recordId);
		e.setShohinId(domain.getShohinDomain().getRecordId());

		// save
		repo.save(e);
	}

	@Override
	public void overrideList(List<UriageMeisaiDomain> meisaiList) {
		// delete removed
		List<UriageMeisaiDomain> oldList = getDomainList(meisaiList.get(0).getUriageId());
		oldList.stream().filter(o -> {
			return !meisaiList.contains(o);
		}).forEach(o -> {
			delete(o);
		});

		// save
		meisaiList.forEach(m -> save(m));
	}

	@Override
	public void delete(UriageMeisaiDomain old) {
		// TODO Auto-generated method stub

	}

}
