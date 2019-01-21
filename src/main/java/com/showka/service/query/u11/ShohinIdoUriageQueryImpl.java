package com.showka.service.query.u11;

import static java.util.stream.Collectors.*;

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
import com.showka.value.EigyoDate;

@Service
public class ShohinIdoUriageQueryImpl implements ShohinIdoUriageQuery {

	@Autowired
	private JShohinIdoUriageRepository repo;

	@Autowired
	private ShohinIdoCrud shohinIdoCrud;

	@Override
	public Optional<ShohinIdo> getNewest(String uriageId) {
		List<JShohinIdoUriage> results = this.getEntity(uriageId);
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

	@Override
	public List<ShohinIdo> get(String uriageId, EigyoDate date) {
		List<JShohinIdoUriage> _results = this.getEntity(uriageId);
		return _results.stream().filter(e -> {
			EigyoDate _date = new EigyoDate(e.getShohinIdo().getDate());
			return date.equals(_date);
		}).map(e -> {
			return shohinIdoCrud.getDomain(e.getShohinIdoId());
		}).collect(toList());
	}

	private List<JShohinIdoUriage> getEntity(String uriageId) {
		JShohinIdoUriage e = new JShohinIdoUriage();
		e.setUriageId(uriageId);
		Example<JShohinIdoUriage> example = Example.of(e);
		List<JShohinIdoUriage> results = repo.findAll(example);
		return results;
	}
}
