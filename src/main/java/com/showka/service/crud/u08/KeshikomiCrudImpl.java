package com.showka.service.crud.u08;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.builder.KeshikomiBuilder;
import com.showka.domain.u06.Urikake;
import com.showka.domain.u08.Keshikomi;
import com.showka.domain.u08.Nyukin;
import com.showka.entity.TKeshikomi;
import com.showka.repository.i.TKeshikomiRepository;
import com.showka.service.crud.u06.i.UrikakeCrud;
import com.showka.service.crud.u08.i.KeshikomiCrud;
import com.showka.service.crud.u08.i.NyukinCrud;
import com.showka.value.AmountOfMoney;
import com.showka.value.EigyoDate;
import com.showka.value.TheTimestamp;

@Service
public class KeshikomiCrudImpl implements KeshikomiCrud {

	@Autowired
	private TKeshikomiRepository repo;

	@Autowired
	private NyukinCrud nyukinCrud;

	@Autowired
	private UrikakeCrud urikakeCrud;

	@Override
	public void save(Keshikomi keshikomi) {
		// entity
		Optional<TKeshikomi> _e = repo.findById(keshikomi.getRecordId());
		TKeshikomi e = _e.orElse(new TKeshikomi());
		// set columns
		e.setDate(keshikomi.getDate().toDate());
		e.setTimestamp(keshikomi.getTimestamp().toDate());
		e.setKingaku(keshikomi.getKingaku().intValue());
		e.setNyukinId(keshikomi.getNyukin().getRecordId());
		String urikakeId = keshikomi.getUrikakeId();
		e.setUrikakeId(urikakeId);
		// OCC
		e.setVersion(keshikomi.getVersion());
		// record id
		String recordId = _e.isPresent() ? e.getRecordId() : UUID.randomUUID().toString();
		e.setRecordId(recordId);
		keshikomi.setRecordId(recordId);
		// save
		repo.save(e);
	}

	@Override
	public void delete(Keshikomi domain) {
		TKeshikomi e = repo.getOne(domain.getRecordId());
		e.setVersion(domain.getVersion());
		repo.delete(e);
	}

	/**
	 * 消込取得.
	 * 
	 * @param keshikomiId
	 *            消込ID
	 * @return 消込
	 */
	@Override
	public Keshikomi getDomain(String keshikomiId) {
		// entity
		TKeshikomi e = repo.getOne(keshikomiId);
		// get 入金
		Nyukin nyukin = nyukinCrud.getDomain(e.getNyukinId());
		// get 売掛
		Urikake urikake = urikakeCrud.getDomainById(e.getUrikakeId());
		// set builder
		KeshikomiBuilder b = new KeshikomiBuilder();
		b.withNyukin(nyukin);
		b.withUrikake(urikake);
		b.withDate(new EigyoDate(e.getDate()));
		b.withTimestamp(new TheTimestamp(e.getTimestamp()));
		b.withKingaku(new AmountOfMoney(e.getKingaku()));
		b.withRecordId(e.getRecordId());
		b.withVersion(e.getVersion());
		// build
		return b.build();
	}

	/**
	 * 存在検証.
	 * 
	 * @param keshikomiId
	 *            消込ID
	 * @return 存在する場合true
	 */
	@Override
	public boolean exists(String keshikomiId) {
		return repo.existsById(keshikomiId);
	}
}
