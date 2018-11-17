package com.showka.service.persistence.u05;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
import com.showka.repository.i.RUriageKeijoRepository;
import com.showka.repository.i.RUriageKeijoTeiseiRepository;
import com.showka.service.persistence.u05.i.UriageKeijoPersistence;
import com.showka.service.persistence.u05.i.UriageRirekiPersistence;
import com.showka.service.search.u05.i.UriageKeijoSearchService;
import com.showka.service.search.u05.i.UriageRirekiSearchService;
import com.showka.value.EigyoDate;

@Service
public class UriageKeijoPersistenceImpl implements UriageKeijoPersistence {

	@Autowired
	private RUriageKeijoRepository repo;

	@Autowired
	private RUriageKeijoTeiseiRepository repoTeisei;

	@Autowired
	private UriageRirekiSearchService uriageRirekiSearchService;

	@Autowired
	private UriageRirekiPersistence uriageRirekiPersistence;

	@Autowired
	private UriageKeijoSearchService uriageKeijoSearchService;

	@Override
	public void keijo(Busho busho, EigyoDate date) {
		// search 計上対象売上
		List<RUriage> uriageRirekiList = uriageRirekiSearchService.search(busho, date);
		// 売上計上
		uriageRirekiList.forEach(uriageRireki -> {
			// entity
			RUriageKeijo e = new RUriageKeijo();
			e.setBushoId(busho.getRecordId());
			e.setUriageRirekiId(uriageRireki.getRecordId());
			// record id
			String recordId = UUID.randomUUID().toString();
			e.setRecordId(recordId);
			// save
			repo.save(e);
		});
		// 売上計上訂正
		uriageRirekiList.forEach(uriageRireki -> {
			// 売上履歴取得
			String uriageId = uriageRireki.getPk().getUriageId();
			UriageRireki rirekiDomain = uriageRirekiPersistence.getUriageRirekiList(uriageId);
			Optional<Uriage> teisei = rirekiDomain.getTeiseiUriage(date);
			if (teisei.isPresent()) {
				// 売上計上ID取得
				RUriageKeijo keijo = repo.getOne(uriageRireki.getRecordId());
				String uriageKeijoId = keijo.getRecordId();
				// entity
				RUriageKeijoTeisei et = new RUriageKeijoTeisei();
				et.setUriageKeijoId(uriageKeijoId);
				String uriageRirekiId = teisei.get().getRecordId();
				et.setUriageRirekiId(uriageRirekiId);
				// record id
				String recordId = UUID.randomUUID().toString();
				et.setRecordId(recordId);
				// save
				repoTeisei.save(et);
			}
		});
	}

	@Override
	public BushoUriage getBushoUriage(Busho busho, EigyoDate date) {
		// 売上計上金額集計（訂正除く）
		int keijoKingaku = uriageKeijoSearchService.getKeijoKingaku(busho, date);
		int teiseiKingaku = uriageKeijoSearchService.getTeiseiKingaku(busho, date);
		// build
		BushoUriageBuilder b = new BushoUriageBuilder();
		b.withBusho(busho);
		b.withKeijoDate(date);
		b.withKeijoKingaku(keijoKingaku);
		b.withTeiseiKingaku(teiseiKingaku);
		return b.build();
	}
}
