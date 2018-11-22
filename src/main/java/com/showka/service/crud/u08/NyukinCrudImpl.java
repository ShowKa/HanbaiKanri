package com.showka.service.crud.u08;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.builder.NyukinBuilder;
import com.showka.domain.u01.Kokyaku;
import com.showka.domain.u08.Nyukin;
import com.showka.domain.z00.Busho;
import com.showka.entity.TNyukin;
import com.showka.kubun.NyukinHohoKubun;
import com.showka.kubun.i.Kubun;
import com.showka.repository.i.TNyukinRepository;
import com.showka.service.crud.u01.i.KokyakuCrud;
import com.showka.service.crud.u08.i.NyukinCrud;
import com.showka.service.crud.z00.i.BushoCrud;
import com.showka.value.AmountOfMoney;
import com.showka.value.EigyoDate;

@Service
public class NyukinCrudImpl implements NyukinCrud {

	@Autowired
	private TNyukinRepository repo;

	@Autowired
	private BushoCrud bushoPersistence;

	@Autowired
	private KokyakuCrud kokyakuPersistence;

	@Override
	public void save(Nyukin domain) {
		// entity
		Optional<TNyukin> _e = repo.findById(domain.getRecordId());
		TNyukin e = _e.orElse(new TNyukin());
		// set columns
		e.setBushoId(domain.getBushoId());
		e.setDate(domain.getDate().toDate());
		e.setKingaku(domain.getKingaku().intValue());
		e.setKokyakuId(domain.getKokyakuId());
		e.setNyukinHohoKubun(domain.getNyukinHohoKubun().getCode());
		// OCC
		e.setVersion(domain.getVersion());
		// record id
		String recordId = _e.isPresent() ? e.getRecordId() : UUID.randomUUID().toString();
		e.setRecordId(recordId);
		domain.setRecordId(recordId);
		// save
		repo.save(e);
	}

	@Override
	public void delete(Nyukin domain) {
		String nyukinId = domain.getRecordId();
		Integer version = domain.getVersion();
		// entity
		TNyukin e = repo.getOne(nyukinId);
		// OCC
		e.setVersion(version);
		// delete
		repo.delete(e);
	}

	@Override
	public Nyukin getDomain(String nyukinId) {
		// entity
		TNyukin e = repo.getOne(nyukinId);
		// other domains
		Busho busho = bushoPersistence.getDomain(e.getBushoCode());
		Kokyaku kokyaku = kokyakuPersistence.getDomain(e.getKokyakuCode());
		// set builder
		NyukinBuilder b = new NyukinBuilder();
		b.withBusho(busho);
		b.withDate(new EigyoDate(e.getDate()));
		b.withKingaku(new AmountOfMoney(e.getKingaku()));
		b.withKokyaku(kokyaku);
		b.withNyukinHohoKubun(Kubun.get(NyukinHohoKubun.class, e.getNyukinHohoKubun()));
		b.withRecordId(e.getRecordId());
		b.withVersion(e.getVersion());
		// build
		return b.build();
	}

	@Override
	public boolean exsists(String nyukinId) {
		return repo.existsById(nyukinId);
	}

}
