package com.showka.service.search.u06;

import static com.showka.repository.specification.TUrikakeSpecification.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.showka.domain.u06.Urikake;
import com.showka.entity.TUrikake;
import com.showka.repository.i.TUrikakeRepository;
import com.showka.service.crud.u06.i.UrikakeCrudService;
import com.showka.service.search.u06.i.UrikakeSearchService;
import com.showka.service.specification.u06.i.UrikakeKeshikomiSpecificationService;

@Service
public class UrikakeSearchServiceImpl implements UrikakeSearchService {

	@Autowired
	private TUrikakeRepository tUrikakeRepository;

	@Autowired
	private UrikakeCrudService urikakeCrudService;

	@Autowired
	private UrikakeKeshikomiSpecificationService urikakeKeshikomiSpecificationService;

	@Override
	// FIXME should implement by jooq
	public List<Urikake> getUrikakeOfKokyaku(String kokyakuId) {
		// 残高仕様
		Specification<TUrikake> spec = Specification.where(zandakaGreaterThan(0));
		// 顧客仕様
		spec = spec.and(kokyaku(kokyakuId));
		// find entity
		List<TUrikake> result = tUrikakeRepository.findAll(spec);
		// build domain
		return result.stream().map(_urikake -> {
			return urikakeCrudService.getDomain(_urikake.getRecordId());
		}).filter(u -> {
			return urikakeKeshikomiSpecificationService.getZandakaOf(u).intValue() > 0;
		}).collect(Collectors.toList());
	}
}