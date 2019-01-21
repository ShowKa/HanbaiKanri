package com.showka.service.persistence.u11;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.u05.Uriage;
import com.showka.domain.u11.ShohinIdo;
import com.showka.entity.JShohinIdoUriage;
import com.showka.entity.TUriagePK;
import com.showka.repository.i.JShohinIdoUriageRepository;
import com.showka.service.crud.u05.i.UriageCrud;
import com.showka.service.persistence.u11.i.ShohinIdoPersistence;
import com.showka.service.persistence.u11.i.ShohinIdoUriagePersistence;
import com.showka.service.specification.u11.ShohinIdoSpecificationFactory;
import com.showka.service.specification.u11.i.ShohinIdoSpecification;

@Service
public class ShohinIdoUriagePersistenceImpl implements ShohinIdoUriagePersistence {

	@Autowired
	private JShohinIdoUriageRepository repo;

	@Autowired
	private ShohinIdoSpecificationFactory shohinIdoSpecificationFactory;

	@Autowired
	private ShohinIdoPersistence shohinIdoPersistence;

	@Autowired
	private UriageCrud uriageCrud;

	@Override
	public void save(Uriage uriage) {
		// 商品移動仕様
		ShohinIdoSpecification specification = shohinIdoSpecificationFactory.create(uriage);
		// 商品移動
		shohinIdoPersistence.shohinIdo(specification);
		// 商品移動売上
		List<ShohinIdo> shohinIdo = specification.getShohinIdo();
		shohinIdo.forEach(si -> {
			Optional<JShohinIdoUriage> _e = repo.findById(si.getRecordId());
			JShohinIdoUriage e = _e.orElse(new JShohinIdoUriage());
			e.setUriageId(uriage.getRecordId());
			e.setShohinIdoId(si.getRecordId());
			if (!_e.isPresent()) {
				e.initRecordId();
			}
			repo.save(e);
		});
	}

	@Override
	public void delete(TUriagePK pk) {
		Uriage uriage = uriageCrud.getDomain(pk);
		ShohinIdoSpecification specification = shohinIdoSpecificationFactory.create(uriage);
		List<ShohinIdo> idoList = specification.getShohinIdo();
		idoList.forEach(ido -> {
			repo.deleteById(ido.getRecordId());
		});
		shohinIdoPersistence.delete(specification);
	}
}
