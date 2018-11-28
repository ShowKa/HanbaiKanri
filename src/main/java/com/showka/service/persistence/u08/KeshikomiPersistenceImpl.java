package com.showka.service.persistence.u08;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.u08.Keshikomi;
import com.showka.entity.CKeshikomi;
import com.showka.repository.i.CKeshikomiRepository;
import com.showka.service.crud.u08.i.KeshikomiCrud;
import com.showka.service.persistence.u08.i.KeshikomiPersistence;
import com.showka.service.query.u08.i.KeshikomiQuery;
import com.showka.value.EigyoDate;

@Service
public class KeshikomiPersistenceImpl implements KeshikomiPersistence {

	@Autowired
	private CKeshikomiRepository cancelRepo;

	@Autowired
	private KeshikomiCrud keshikomiCrud;

	@Autowired
	private KeshikomiQuery keshikomiQuery;

	@Override
	public void override(String nyukinId, EigyoDate date, Collection<Keshikomi> keshikomiList) {
		// get old
		List<Keshikomi> oldList = keshikomiQuery.getOfNyukin(nyukinId).stream().filter(k -> {
			// 営業日が同じもののみ抽出
			return k.getDate().equals(date);
		}).collect(Collectors.toList());
		// delete removed
		oldList.stream().filter(o -> {
			return !keshikomiList.contains(o);
		}).forEach(o -> {
			keshikomiCrud.delete(o);
		});
		// save
		keshikomiList.forEach(keshikomiCrud::save);
	}

	@Override
	public void cancel(String keshikomiId, EigyoDate date) {
		Keshikomi keshikomi = keshikomiCrud.getDomain(keshikomiId);
		// delete
		keshikomiCrud.delete(keshikomi);
		// cancel
		CKeshikomi ce = new CKeshikomi();
		ce.setCancelDate(date.toDate());
		ce.setDate(keshikomi.getDate().toDate());
		ce.setKingaku(keshikomi.getKingaku().intValue());
		ce.setNyukinId(keshikomi.getNyukinId());
		ce.setTimestamp(keshikomi.getTimestamp().toDate());
		ce.setUrikakeId(keshikomi.getUrikakeId());
		ce.setRecordId(keshikomi.getRecordId());
		cancelRepo.save(ce);
	}
}
