package com.showka.service.search.u05;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.Busho;
import com.showka.entity.RUriage;
import com.showka.entity.RUriageKeijo;
import com.showka.repository.i.RUriageKeijoRepository;
import com.showka.service.search.u05.i.UriageKeijoSearchService;
import com.showka.service.search.u05.i.UriageRirekiSearchService;
import com.showka.value.TheDate;

@Service
public class UriageKeijoSearchServiceImpl implements UriageKeijoSearchService {

	@Autowired
	private RUriageKeijoRepository repo;

	@Autowired
	private UriageRirekiSearchService uriageRirekiSearchService;

	@Override
	public List<RUriageKeijo> search(Busho busho, TheDate date) {
		// search 計上対象売上
		List<RUriage> uriageRirekiList = uriageRirekiSearchService.search(busho, date);
		// 売上履歴 record id
		Iterable<String> uriageRirekiRecordIds = uriageRirekiList.stream().map(uriageRireki -> {
			return uriageRireki.getRecordId();
		}).collect(Collectors.toList());
		// 売上計上 entities
		return repo.findAllById(uriageRirekiRecordIds);
	}
}
