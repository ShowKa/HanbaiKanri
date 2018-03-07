package com.showka.service.crud.u11;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.ShohinIdo;
import com.showka.domain.Uriage;
import com.showka.entity.JShohinIdoUriage;
import com.showka.repository.i.JShohinIdoUriageRepository;
import com.showka.service.crud.u11.i.ShohinIdoCrudService;
import com.showka.service.crud.u11.i.ShohinIdoUriageCrudService;
import com.showka.service.specification.u11.ShohinIdoSpecificationAssociatedWithUriage;
import com.showka.service.specification.u11.ShohinIdoSpecificationFactory;

@Service
public class ShohinIdoUriageCrudServiceImpl implements ShohinIdoUriageCrudService {

	@Autowired
	private JShohinIdoUriageRepository jShohinIdoUriageRepository;

	@Autowired
	private ShohinIdoSpecificationFactory shohinIdoSpecificationFactory;

	@Autowired
	private ShohinIdoCrudService shohinIdoCrudService;

	@Override
	public void save(Uriage uriage) {
		// 商品移動
		ShohinIdoSpecificationAssociatedWithUriage specification = shohinIdoSpecificationFactory.create(uriage);
		shohinIdoCrudService.shohinIdo(specification);
		// 商品移動売上
		List<ShohinIdo> shohinIdo = specification.getShohinIdo();
		shohinIdo.forEach(si -> {
			// insert only
			JShohinIdoUriage e = new JShohinIdoUriage();
			e.setShohinIdoId(si.getRecordId());
			e.setUriageId(uriage.getRecordId());
			jShohinIdoUriageRepository.save(e);
		});
	}

}
