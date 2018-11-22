package com.showka.service.persistence.u08;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.showka.domain.u08.Keshikomi;
import com.showka.entity.CKeshikomi;
import com.showka.entity.TKeshikomi;
import com.showka.repository.i.CKeshikomiRepository;
import com.showka.repository.i.TKeshikomiRepository;
import com.showka.service.crud.u08.i.KeshikomiCrud;
import com.showka.service.persistence.u08.i.KeshikomiPersistence;
import com.showka.value.EigyoDate;

@Service
public class KeshikomiPersistenceImpl implements KeshikomiPersistence {

	@Autowired
	private TKeshikomiRepository repo;

	@Autowired
	private CKeshikomiRepository cancelRepo;

	@Autowired
	private KeshikomiCrud keshikomiCrud;

	@Override
	public void override(String nyukinId, EigyoDate date, Collection<Keshikomi> keshikomiList) {
		// get old
		List<Keshikomi> oldList = this.getKeshikomiSetOfNyukin(nyukinId).stream().filter(k -> {
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
	public Set<Keshikomi> getKeshikomiSetOfNyukin(String nyukinId) {
		// example
		TKeshikomi e = new TKeshikomi();
		e.setNyukinId(nyukinId);
		Example<TKeshikomi> example = Example.of(e);
		// find
		List<TKeshikomi> keshikomiList = repo.findAll(example);
		// build as map
		return keshikomiList.stream().map(k -> {
			return keshikomiCrud.getDomain(k.getRecordId());
		}).collect(Collectors.toSet());
	}

	@Override
	public Set<Keshikomi> getKeshikomiSetOfUrikake(String urikakeId) {
		// example
		TKeshikomi e = new TKeshikomi();
		e.setUrikakeId(urikakeId);
		Example<TKeshikomi> example = Example.of(e);
		// find
		List<TKeshikomi> keshikomiList = repo.findAll(example);
		// build as map
		return keshikomiList.stream().map(k -> {
			return keshikomiCrud.getDomain(k.getRecordId());
		}).collect(Collectors.toSet());
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
