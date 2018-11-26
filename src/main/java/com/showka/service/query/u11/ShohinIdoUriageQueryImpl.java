package com.showka.service.query.u11;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.showka.domain.u11.ShohinIdo;
import com.showka.entity.JShohinIdoUriage;
import com.showka.repository.i.JShohinIdoUriageRepository;
import com.showka.service.crud.u11.i.ShohinIdoCrud;
import com.showka.service.query.u11.i.ShohinIdoUriageQuery;

@Service
public class ShohinIdoUriageQueryImpl implements ShohinIdoUriageQuery {

	@Autowired
	private JShohinIdoUriageRepository repo;

	@Autowired
	private ShohinIdoCrud shohinIdoCrud;

	@Override
	public Optional<ShohinIdo> getNewest(String uriageId) {
		JShohinIdoUriage e = new JShohinIdoUriage();
		e.setUriageId(uriageId);
		Example<JShohinIdoUriage> example = Example.of(e);
		List<JShohinIdoUriage> results = repo.findAll(example);
		if (results.isEmpty()) {
			return Optional.empty();
		}
		Optional<JShohinIdoUriage> newest = results.stream().max((ido1, ido2) -> {
			Date timestamp1 = ido1.getShohinIdo().getTimestamp();
			Date timestamp2 = ido2.getShohinIdo().getTimestamp();
			return timestamp1.compareTo(timestamp2);
		});
		ShohinIdo shohinIdo = shohinIdoCrud.getDomain(newest.get().getShohinIdoId());
		return Optional.of(shohinIdo);
	}
}
