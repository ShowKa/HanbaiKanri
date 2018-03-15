package com.showka.service.crud.u05;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.Busho;
import com.showka.entity.RUriage;
import com.showka.entity.RUriageKeijo;
import com.showka.repository.i.RUriageKeijoRepository;
import com.showka.service.crud.u05.i.UriageKeijoCrudService;
import com.showka.service.search.u05.i.UriageRirekiSearchService;
import com.showka.value.TheDate;

@Service
public class UriageKeijoCrudServiceImpl implements UriageKeijoCrudService {

	@Autowired
	private RUriageKeijoRepository rUriageKeijoRepository;

	@Autowired
	private UriageRirekiSearchService uriageRirekiSearchService;

	@Override
	public void keijo(Busho busho, TheDate date) {
		// search 未計上売上
		List<RUriage> uriageRirekiList = uriageRirekiSearchService.search(busho, date);
		// collect 売上計上
		List<RUriageKeijo> entities = uriageRirekiList.stream().map(uriageRireki -> {
			// entity
			RUriageKeijo e = new RUriageKeijo();
			e.setBushoId(busho.getRecordId());
			e.setUriageRirekiId(uriageRireki.getRecordId());
			// record id
			String recordId = UUID.randomUUID().toString();
			e.setRecordId(recordId);
			return e;
		}).collect(Collectors.toList());
		// save
		rUriageKeijoRepository.saveAll(entities);
	}
}
