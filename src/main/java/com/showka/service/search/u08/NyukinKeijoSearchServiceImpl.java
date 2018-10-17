package com.showka.service.search.u08;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;

import com.showka.domain.u08.Nyukin;
import com.showka.domain.z00.Busho;
import com.showka.entity.TNyukinKeijo;
import com.showka.repository.i.TNyukinKeijoRepository;
import com.showka.service.crud.u08.i.NyukinCrudService;
import com.showka.service.search.u08.i.NyukinKeijoSearchService;
import com.showka.value.EigyoDate;

public class NyukinKeijoSearchServiceImpl implements NyukinKeijoSearchService {

	@Autowired
	private TNyukinKeijoRepository repo;

	@Autowired
	private NyukinCrudService nyukinCrudService;

	@Override
	public List<Nyukin> searchNotDone(Busho busho, EigyoDate kijunDate) {
		return null;
	}

	@Override
	public List<Nyukin> seach(Busho busho, EigyoDate keijoDate) {
		TNyukinKeijo nk = new TNyukinKeijo();
		nk.setBushoId(busho.getRecordId());
		nk.setDate(keijoDate.toDate());
		Example<TNyukinKeijo> example = Example.of(nk);
		List<TNyukinKeijo> results = repo.findAll(example);
		return results.parallelStream().map(r -> {
			return nyukinCrudService.getDomain(r.getNyukinId());
		}).collect(Collectors.toList());
	}
}
