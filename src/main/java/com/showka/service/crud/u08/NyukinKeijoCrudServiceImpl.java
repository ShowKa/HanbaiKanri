package com.showka.service.crud.u08;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.u08.Nyukin;
import com.showka.domain.u08.NyukinKeshikomi;
import com.showka.domain.u17.BushoNyukin;
import com.showka.domain.z00.Busho;
import com.showka.entity.TNyukinKeijo;
import com.showka.repository.i.TNyukinKeijoRepository;
import com.showka.service.crud.u08.i.NyukinKeijoCrudService;
import com.showka.service.crud.u08.i.NyukinKeshikomiCrudService;
import com.showka.service.search.u08.i.NyukinKeijoSearchService;
import com.showka.value.EigyoDate;

@Service
public class NyukinKeijoCrudServiceImpl implements NyukinKeijoCrudService {

	@Autowired
	private NyukinKeijoSearchService nyukinKeijoSearchService;

	@Autowired
	private NyukinKeshikomiCrudService nyukinKeshikomiCrudService;

	@Autowired
	private TNyukinKeijoRepository repo;

	@Override
	public void keijo(Busho busho, EigyoDate keijoDate) {
		List<Nyukin> nyukinList = nyukinKeijoSearchService.searchNotDone(busho, keijoDate);
		nyukinList.forEach(nyukin -> {
			TNyukinKeijo e = new TNyukinKeijo();
			e.setBushoId(nyukin.getBushoId());
			e.setDate(nyukin.getDate().toDate());
			e.setNyukinId(nyukin.getRecordId());
			// idは同じ
			e.setRecordId(nyukin.getRecordId());
			repo.save(e);
		});
	}

	@Override
	public BushoNyukin getBushoNyukin(Busho busho, EigyoDate keijoDate) {
		List<Nyukin> nyukinList = nyukinKeijoSearchService.seach(busho, keijoDate);
		List<NyukinKeshikomi> nyukinKeshikomiList = nyukinList.stream().map(nyukin -> {
			String nyukinId = nyukin.getRecordId();
			return nyukinKeshikomiCrudService.getDomain(nyukinId);
		}).collect(Collectors.toList());
		return new BushoNyukin(busho, keijoDate, nyukinKeshikomiList);
	}
}
