package com.showka.service.query.u05;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.builder.BushoUriageBuilder;
import com.showka.domain.u05.Uriage;
import com.showka.domain.u05.UriageRireki;
import com.showka.domain.u17.BushoUriage;
import com.showka.domain.z00.Busho;
import com.showka.entity.RUriage;
import com.showka.entity.RUriageKeijo;
import com.showka.entity.RUriageKeijoTeisei;
import com.showka.entity.RUriagePK;
import com.showka.repository.i.RUriageKeijoRepository;
import com.showka.repository.i.RUriageKeijoTeiseiRepository;
import com.showka.repository.i.RUriageRepository;
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
	private RUriageRepository rUriageRepository;

	@Autowired
	private UriageRirekiQuery uriageRirekiQuery;

	@Override
	public BushoUriage getBushoUriage(Busho busho, EigyoDate date) {
		// 売上計上金額集計（訂正除く）
		int keijoKingaku = this.getKeijoKingaku(busho, date);
		int teiseiKingaku = this.getTeiseiKingaku(busho, date);
		// build
		BushoUriageBuilder b = new BushoUriageBuilder();
		b.withBusho(busho);
		b.withKeijoDate(date);
		b.withKeijoKingaku(keijoKingaku);
		b.withTeiseiKingaku(teiseiKingaku);
		return b.build();
	}

	@Override
	public boolean hasDone(Uriage uriage) {
		// get 売上履歴
		RUriagePK pk = new RUriagePK();
		pk.setKeijoDate(uriage.getKeijoDate().toDate());
		pk.setUriageId(uriage.getRecordId());
		RUriage uriageRireki = rUriageRepository.getOne(pk);
		// exists 売上計上
		boolean exists = repo.existsById(uriageRireki.getRecordId());
		return exists;
	}

	/**
	 * 指定した計上日の部署売上計上を取得.
	 * 
	 * @param busho
	 *            部署
	 * @param date
	 *            計上日
	 * @return 売上計上
	 */
	List<RUriageKeijo> get(Busho busho, EigyoDate date) {
		// search 計上対象売上
		List<RUriage> uriageRirekiList = uriageRirekiQuery.getEntityList(busho, date);
		// 売上履歴 record id
		Iterable<String> uriageRirekiRecordIds = uriageRirekiList.stream().map(uriageRireki -> {
			return uriageRireki.getRecordId();
		}).collect(Collectors.toList());
		// 売上計上 entities
		return repo.findAllById(uriageRirekiRecordIds);
	}

	/**
	 * 指定した計上日における部署の売上の計上金額を集計.
	 * 
	 * <pre>
	 * ただし、売上訂正の金額は除く
	 * </pre>
	 * 
	 * @param busho
	 *            部署
	 * @param date
	 *            計上日
	 * @return 集計金額
	 */
	int getKeijoKingaku(Busho busho, EigyoDate date) {
		// 売上計上 entities
		List<RUriageKeijo> keijoEntities = this.get(busho, date);
		// 売上計上金額集計
		int keijoKingaku = keijoEntities.stream().mapToInt(ke -> {
			String uriageId = ke.getUriageId();
			UriageRireki rireki = uriageRirekiQuery.get(uriageId);
			Optional<Uriage> uriage = rireki.getUriageOf(date);
			// 指定して日付での売上が取得できない場合、データ不整合なのでそのまま落ちて良い
			Kakaku uriageGokeiKingaku = uriage.get().getUriageGokeiKakaku();
			return uriageGokeiKingaku.getZeinuki().intValue();
		}).sum();
		return keijoKingaku;
	}

	/**
	 * 指定した計上日における部署の売上の訂正分の計上金額を集計.
	 * 
	 * <pre>
	 * 基本的にマイナス円として集計.
	 * </pre>
	 * 
	 * @param busho
	 *            部署
	 * @param date
	 *            計上日
	 * @return 売上訂正の集計金額
	 */
	int getTeiseiKingaku(Busho busho, EigyoDate date) {
		// 売上計上 entities
		List<RUriageKeijo> keijoEntities = this.get(busho, date);
		// 売上計上訂正分金額集計
		Iterable<String> keijoIds = keijoEntities.stream().map(e -> e.getRecordId()).collect(Collectors.toList());
		List<RUriageKeijoTeisei> teiseiEntities = repoTeisei.findAllById(keijoIds);
		int teiseiKingaku = teiseiEntities.stream().mapToInt(teisei -> {
			String uriageId = teisei.getUriageId();
			UriageRireki rireki = uriageRirekiQuery.get(uriageId);
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
