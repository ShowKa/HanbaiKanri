package com.showka.service.persistence.u05;

import java.util.List;
import java.util.Optional;

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
import com.showka.service.persistence.u05.i.UriageKeijoPersistence;
import com.showka.service.query.u05.i.UriageRirekiQuery;
import com.showka.value.EigyoDate;

@Service
public class UriageKeijoPersistenceImpl implements UriageKeijoPersistence {

	@Autowired
	private RUriageKeijoRepository repo;

	@Autowired
	private RUriageKeijoTeiseiRepository repoTeisei;

	@Autowired
	private UriageRirekiQuery uriageRirekiQuery;

	@Override
	public void keijo(Busho busho, EigyoDate date) {
		// search 計上対象売上
		List<RUriage> uriageRirekiList = uriageRirekiQuery.getEntityList(busho, date);
		// 売上計上
		uriageRirekiList.forEach(uriageRireki -> {
			// entity
			RUriageKeijo e = new RUriageKeijo();
			e.setBushoId(busho.getRecordId());
			e.setUriageRirekiId(uriageRireki.getRecordId());
			// record id
			e.initRecordId();
			// save
			repo.save(e);
		});
		// 売上計上訂正
		uriageRirekiList.forEach(uriageRireki -> {
			// 売上履歴取得
			String uriageId = uriageRireki.getPk().getUriageId();
			UriageRireki rirekiDomain = uriageRirekiQuery.get(uriageId);
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
				et.initRecordId();
				// save
				repoTeisei.save(et);
			}
		});
	}
}
