package com.showka.service.crud.u05;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
import com.showka.service.crud.u05.i.UriageKeijoCrudService;
import com.showka.service.crud.u05.i.UriageRirekiCrudService;
import com.showka.service.search.u05.i.UriageRirekiSearchService;
import com.showka.value.TheDate;

@Service
public class UriageKeijoCrudServiceImpl implements UriageKeijoCrudService {

	@Autowired
	private RUriageKeijoRepository repo;

	@Autowired
	private RUriageKeijoTeiseiRepository repoTeisei;

	@Autowired
	private UriageRirekiSearchService uriageRirekiSearchService;

	@Autowired
	private UriageRirekiCrudService uriageRirekiCrudService;

	@Override
	public void keijo(Busho busho, TheDate date) {
		// search 未計上売上
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
			UriageRireki rirekiDomain = uriageRirekiCrudService.getUriageRirekiList(uriageId);
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
}
