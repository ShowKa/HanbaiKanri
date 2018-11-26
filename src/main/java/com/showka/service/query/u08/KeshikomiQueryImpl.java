package com.showka.service.query.u08;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.showka.domain.u08.Keshikomi;
import com.showka.entity.TKeshikomi;
import com.showka.repository.i.TKeshikomiRepository;
import com.showka.service.crud.u08.i.KeshikomiCrud;
import com.showka.service.query.u08.i.KeshikomiQuery;

@Service
public class KeshikomiQueryImpl implements KeshikomiQuery {

	@Autowired
	private TKeshikomiRepository repo;

	@Autowired
	private KeshikomiCrud keshikomiCrud;

	@Override
	public Set<Keshikomi> getOfNyukin(String nyukinId) {
		// find
		List<TKeshikomi> keshikomiList = this.findEntityOfNyukin(nyukinId);
		// build
		return keshikomiList.stream().map(k -> {
			return keshikomiCrud.getDomain(k.getRecordId());
		}).collect(Collectors.toSet());
	}

	@Override
	public Set<Keshikomi> getOfUrikake(String urikakeId) {
		// find
		List<TKeshikomi> keshikomiList = this.findEntityOfUrikake(urikakeId);
		// build as map
		return keshikomiList.stream().map(k -> {
			return keshikomiCrud.getDomain(k.getRecordId());
		}).collect(Collectors.toSet());
	}

	List<TKeshikomi> findEntityOfNyukin(String nyukinId) {
		// example
		TKeshikomi e = new TKeshikomi();
		e.setNyukinId(nyukinId);
		Example<TKeshikomi> example = Example.of(e);
		// find
		List<TKeshikomi> keshikomiList = repo.findAll(example);
		return keshikomiList;
	}

	List<TKeshikomi> findEntityOfUrikake(String urikakeId) {
		// example
		TKeshikomi e = new TKeshikomi();
		e.setUrikakeId(urikakeId);
		Example<TKeshikomi> example = Example.of(e);
		// find
		List<TKeshikomi> keshikomiList = repo.findAll(example);
		return keshikomiList;
	}
}
