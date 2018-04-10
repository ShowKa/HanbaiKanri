package com.showka.service.search.u05;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.Busho;
import com.showka.domain.Uriage;
import com.showka.domain.UriageRireki;
import com.showka.entity.RUriage;
import com.showka.entity.RUriageKeijo;
import com.showka.entity.RUriageKeijoTeisei;
import com.showka.repository.i.RUriageKeijoRepository;
import com.showka.repository.i.RUriageKeijoTeiseiRepository;
import com.showka.service.crud.u05.i.UriageRirekiCrudService;
import com.showka.service.search.u05.i.UriageKeijoSearchService;
import com.showka.service.search.u05.i.UriageRirekiSearchService;
import com.showka.value.Kakaku;
import com.showka.value.TheDate;

@Service
public class UriageKeijoSearchServiceImpl implements UriageKeijoSearchService {

	@Autowired
	private RUriageKeijoRepository repo;

	@Autowired
	private RUriageKeijoTeiseiRepository repoTeisei;

	@Autowired
	private UriageRirekiCrudService uriageRirekiCrudService;

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

	@Override
	public int getKeijoKingaku(Busho busho, TheDate date) {
		// 売上計上 entities
		List<RUriageKeijo> keijoEntities = this.search(busho, date);
		// 売上計上金額集計
		int keijoKingaku = keijoEntities.stream().mapToInt(ke -> {
			String uriageId = ke.getUriageId();
			UriageRireki rireki = uriageRirekiCrudService.getUriageRirekiList(uriageId);
			Optional<Uriage> uriage = rireki.getUriageOf(date);
			// 指定して日付での売上が取得できない場合、データ不整合なのでそのまま落ちて良い
			Kakaku uriageGokeiKingaku = uriage.get().getUriageGokeiKakaku();
			return uriageGokeiKingaku.getZeinuki().intValue();
		}).sum();
		return keijoKingaku;
	}

	@Override
	public int getTeiseiKingaku(Busho busho, TheDate date) {
		// 売上計上 entities
		List<RUriageKeijo> keijoEntities = this.search(busho, date);
		// 売上計上訂正分金額集計
		Iterable<String> keijoIds = keijoEntities.stream().map(e -> e.getRecordId()).collect(Collectors.toList());
		List<RUriageKeijoTeisei> teiseiEntities = repoTeisei.findAllById(keijoIds);
		int teiseiKingaku = teiseiEntities.stream().mapToInt(teisei -> {
			String uriageId = teisei.getUriageId();
			UriageRireki rireki = uriageRirekiCrudService.getUriageRirekiList(uriageId);
			Date pastKeijoDate = teisei.getTeiseiUriageRirekiKeijoDate();
			Optional<Uriage> uriage = rireki.getUriageOf(new TheDate(pastKeijoDate));
			// 指定して日付での売上が取得できない場合、データ不整合なのでそのまま落ちて良い
			Kakaku uriageGokeiKingaku = uriage.get().getUriageGokeiKakaku();
			// 訂正分は負数として集計
			return uriageGokeiKingaku.getZeinuki().intValue() * -1;
		}).sum();
		return teiseiKingaku;
	}
}
