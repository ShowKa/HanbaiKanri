package com.showka.service.crud.u08;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.Busho;
import com.showka.domain.Kokyaku;
import com.showka.domain.Nyukin;
import com.showka.domain.builder.NyukinBuilder;
import com.showka.entity.TNyukin;
import com.showka.kubun.NyukinHohoKubun;
import com.showka.kubun.i.Kubun;
import com.showka.repository.i.TNyukinRepository;
import com.showka.service.crud.u01.i.KokyakuCrudService;
import com.showka.service.crud.u08.i.NyukinCrudService;
import com.showka.service.crud.z00.i.BushoCrudService;
import com.showka.value.AmountOfMoney;
import com.showka.value.EigyoDate;

@Service
public class NyukinCrudServiceImpl implements NyukinCrudService {

	@Autowired
	private TNyukinRepository repo;

	@Autowired
	private BushoCrudService bushoCrudService;

	@Autowired
	private KokyakuCrudService kokyakuCrudService;

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
	public void delete(String nyukinId, Integer version) {
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
		Busho busho = bushoCrudService.getDomain(e.getBushoCode());
		Kokyaku kokyaku = kokyakuCrudService.getDomain(e.getKokyakuCode());
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
