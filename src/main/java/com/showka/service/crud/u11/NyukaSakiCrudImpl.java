package com.showka.service.crud.u11;

import com.showka.domain.builder.NyukaSakiBuilder;
import com.showka.domain.u11.NyukaSaki;
import com.showka.entity.MNyukaSaki;
import com.showka.repository.i.MNyukaSakiRepository;
import com.showka.service.crud.u11.i.NyukaSakiCrud;

public class NyukaSakiCrudImpl implements NyukaSakiCrud {

	private MNyukaSakiRepository repo;

	@Override
	public void save(NyukaSaki domain) {
		throw new RuntimeException("未実装");
	}

	@Override
	public void delete(NyukaSaki domain) {
		throw new RuntimeException("未実装");
	}

	@Override
	public NyukaSaki getDomain(String code) {
		// entity
		MNyukaSaki e = repo.getOne(code);
		// build
		NyukaSakiBuilder nsb = new NyukaSakiBuilder();
		nsb.withAddress(e.getAddress());
		nsb.withCode(e.getCode());
		nsb.withName(e.getName());
		nsb.withRecordId(e.getRecordId());
		nsb.withVersion(e.getVersion());
		return nsb.build();
	}

	@Override
	public boolean exists(String code) {
		return repo.existsById(code);
	}
}
