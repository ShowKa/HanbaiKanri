package com.showka.service.query.u05;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.u05.Uriage;
import com.showka.domain.u05.UriageRireki;
import com.showka.domain.z00.Busho;
import com.showka.entity.RUriage;
import com.showka.entity.RUriageKeijo;
import com.showka.entity.RUriageKeijoTeisei;
import com.showka.repository.i.RUriageKeijoRepository;
import com.showka.repository.i.RUriageKeijoTeiseiRepository;
import com.showka.service.persistence.u05.i.UriageRirekiPersistence;
import com.showka.service.query.u05.i.UriageKeijoQuery;
import com.showka.service.query.u05.i.UriageRirekiQuery;
import com.showka.value.EigyoDate;
import com.showka.value.Kakaku;

@Service
public class UriageKeijoQueryImpl implements UriageKeijoQuery {

	@Autowired
	private RUriageKeijoRepository repo;

	@Autowired
	private RUriageKeijoTeiseiRepository repoTeisei;

	@Autowired
	private UriageRirekiPersistence uriageRirekiPersistence;

	@Autowired
	private UriageRirekiQuery uriageRirekiQuery;

	@Override
	public List<RUriageKeijo> search(Busho busho, EigyoDate date) {
		// search 計上対象売上
		List<RUriage> uriageRirekiList = uriageRirekiQuery.search(busho, date);
		// 売上履歴 record id
		Iterable<String> uriageRirekiRecordIds = uriageRirekiList.stream().map(uriageRireki -> {
			return uriageRireki.getRecordId();
		}).collect(Collectors.toList());
		// 売上計上 entities
		return repo.findAllById(uriageRirekiRecordIds);
	}

	@Override
	public int getKeijoKingaku(Busho busho, EigyoDate date) {
		// 売上計上 entities
		List<RUriageKeijo> keijoEntities = this.search(busho, date);
		// 売上計上金額集計
		int keijoKingaku = keijoEntities.stream().mapToInt(ke -> {
			String uriageId = ke.getUriageId();
			UriageRireki rireki = uriageRirekiPersistence.getUriageRirekiList(uriageId);
			Optional<Uriage> uriage = rireki.getUriageOf(date);
			// 指定して日付での売上が取得できない場合、データ不整合なのでそのまま落ちて良い
			Kakaku uriageGokeiKingaku = uriage.get().getUriageGokeiKakaku();
			return uriageGokeiKingaku.getZeinuki().intValue();
		}).sum();
		return keijoKingaku;
	}

	@Override
	public int getTeiseiKingaku(Busho busho, EigyoDate date) {
		// 売上計上 entities
		List<RUriageKeijo> keijoEntities = this.search(busho, date);
		// 売上計上訂正分金額集計
		Iterable<String> keijoIds = keijoEntities.stream().map(e -> e.getRecordId()).collect(Collectors.toList());
		List<RUriageKeijoTeisei> teiseiEntities = repoTeisei.findAllById(keijoIds);
		int teiseiKingaku = teiseiEntities.stream().mapToInt(teisei -> {
			String uriageId = teisei.getUriageId();
			UriageRireki rireki = uriageRirekiPersistence.getUriageRirekiList(uriageId);
			Date pastKeijoDate = teisei.getTeiseiUriageRirekiKeijoDate();
			Optional<Uriage> uriage = rireki.getUriageOf(new EigyoDate(pastKeijoDate));
			// 指定して日付での売上が取得できない場合、データ不整合なのでそのまま落ちて良い
			Kakaku uriageGokeiKingaku = uriage.get().getUriageGokeiKakaku();
			// 訂正分は負数として集計
			return uriageGokeiKingaku.getZeinuki().intValue() * -1;
		}).sum();
		return teiseiKingaku;
	}
}
