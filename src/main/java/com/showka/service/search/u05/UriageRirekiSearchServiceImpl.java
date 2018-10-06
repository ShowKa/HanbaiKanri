package com.showka.service.search.u05;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.showka.domain.z00.Busho;
import com.showka.entity.MKokyaku;
import com.showka.entity.RUriage;
import com.showka.entity.RUriagePK;
import com.showka.entity.TUriage;
import com.showka.repository.i.RUriageRepository;
import com.showka.service.search.u05.i.UriageRirekiSearchService;
import com.showka.value.TheDate;

@Service
public class UriageRirekiSearchServiceImpl implements UriageRirekiSearchService {

	@Autowired
	private RUriageRepository repo;

	@Override
	public List<RUriage> search(Busho busho, TheDate date) {
		// entity
		RUriage e = new RUriage();
		// pk
		RUriagePK pk = new RUriagePK();
		// 計上日
		pk.setKeijoDate(date.toDate());
		e.setPk(pk);
		// 顧客+主管部署
		MKokyaku kokyaku = new MKokyaku();
		kokyaku.setShukanBushoId(busho.getRecordId());
		// 売上
		TUriage uriage = new TUriage();
		uriage.setKokyaku(kokyaku);
		e.setUriage(uriage);
		// search
		Example<RUriage> example = Example.of(e);
		return repo.findAll(example);
	}
}
