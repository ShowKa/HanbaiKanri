package com.showka.service.search.u05;

import static com.showka.repository.specification.TUrikakeSpecification.kokyaku;
import static com.showka.repository.specification.TUrikakeSpecification.zandakaGreaterThan;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.showka.domain.Urikake;
import com.showka.entity.TUrikake;
import com.showka.repository.i.TUrikakeRepository;
import com.showka.service.crud.u05.i.UrikakeCrudService;
import com.showka.service.search.u05.i.UrikakeSearchService;

@Service
public class UrikakeSearchServiceImpl implements UrikakeSearchService {

	@Autowired
	private TUrikakeRepository tUrikakeRepository;

	@Autowired
	private UrikakeCrudService urikakeCrudService;

	@Override
	public List<Urikake> getUrikakeOfKokyaku(String kokyakuId) {
		// 残高仕様
		Specification<TUrikake> spec = Specification.where(zandakaGreaterThan(0));
		// 顧客仕様
		spec = spec.and(kokyaku(kokyakuId));
		// find entity
		List<TUrikake> result = tUrikakeRepository.findAll(spec);
		// build domain
		return result.stream().map(_urikake -> {
			return urikakeCrudService.getDomain(_urikake.getUriageId());
		}).collect(Collectors.toList());
	}
}
