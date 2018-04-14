package com.showka.service.crud.u08;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.Keshikomi;
import com.showka.domain.builder.KeshikomiBuilder;
import com.showka.entity.TKeshikomi;
import com.showka.repository.i.TKeshikomiRepository;
import com.showka.service.crud.u08.i.KeshikomiCrudService;
import com.showka.value.AmountOfMoney;
import com.showka.value.EigyoDate;

@Service
public class KeshikomiCrudServiceImpl implements KeshikomiCrudService {

	@Autowired
	private TKeshikomiRepository repo;

	@Override
	// TODO 入金&売掛
	public void save(Keshikomi domain) {
		// entity
		Optional<TKeshikomi> _e = repo.findById(domain.getRecordId());
		TKeshikomi e = _e.orElse(new TKeshikomi());
		// set columns
		e.setDate(domain.getDate().toDate());
		e.setKingaku(domain.getKingaku().intValue());
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
	public void delete(String keshikomiId, Integer version) {
		// entity
		TKeshikomi e = repo.getOne(keshikomiId);
		// OCC
		e.setVersion(version);
		// delete
		repo.delete(e);
	}

	@Override
	public Keshikomi getDomain(String keshikomiId) {
		// entity
		TKeshikomi e = repo.getOne(keshikomiId);
		// set builder
		KeshikomiBuilder b = new KeshikomiBuilder();
		b.withDate(new EigyoDate(e.getDate()));
		b.withKingaku(new AmountOfMoney(e.getKingaku()));
		b.withRecordId(e.getRecordId());
		b.withVersion(e.getVersion());
		// build
		return b.build();
	}

	@Override
	public boolean exsists(String keshikomiId) {
		return repo.existsById(keshikomiId);
	}
}
