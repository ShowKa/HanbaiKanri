package com.showka.service.crud.u11;

import static java.util.stream.Collectors.*;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.showka.domain.u11.ShohinIdo;
import com.showka.entity.TShohinIdoNyukaTeisei;
import com.showka.entity.TShohinidoNyukaTeiseiPK;
import com.showka.repository.i.TShohinIdoNyukaTeiseiRepository;
import com.showka.service.crud.u11.i.NyukaTeiseiCrud;
import com.showka.service.crud.u11.i.ShohinIdoCrud;

@Service
public class NyukaTeiseiCrudImpl implements NyukaTeiseiCrud {

	@Autowired
	private TShohinIdoNyukaTeiseiRepository repo;

	@Autowired
	private ShohinIdoCrud shohinIdoCrud;

	// OCC 対象外
	@Override
	public void save(String nyukaId, ShohinIdo teiseiShohinIdo) {
		// pk
		TShohinidoNyukaTeiseiPK pk = new TShohinidoNyukaTeiseiPK();
		pk.setNyukaId(nyukaId);
		pk.setShohinIdoId(teiseiShohinIdo.getRecordId());
		// entity
		Optional<TShohinIdoNyukaTeisei> _e = repo.findById(pk);
		TShohinIdoNyukaTeisei e = _e.orElse(new TShohinIdoNyukaTeisei());
		// set column
		e.setPk(pk);
		// record id
		if (!_e.isPresent()) {
			e.initRecordId();
		}
		// save
		repo.save(e);
	}

	// OCC 対象外
	@Override
	public void delete(String nyukaId, ShohinIdo teiseiShohinIdo) {
		// pk
		TShohinidoNyukaTeiseiPK pk = new TShohinidoNyukaTeiseiPK();
		pk.setNyukaId(nyukaId);
		pk.setShohinIdoId(teiseiShohinIdo.getRecordId());
		// delete
		repo.deleteById(pk);
	}

	@Override
	public List<ShohinIdo> getAll(String nyukaId) {
		List<TShohinIdoNyukaTeisei> entities = this.findEntityByNyukaId(nyukaId);
		return entities.stream().map(e -> {
			return shohinIdoCrud.getDomain(e.getRecordId());
		}).collect(toList());
	}

	List<TShohinIdoNyukaTeisei> findEntityByNyukaId(String nyukaId) {
		// pk
		TShohinidoNyukaTeiseiPK pk = new TShohinidoNyukaTeiseiPK();
		pk.setNyukaId(nyukaId);
		// entity
		TShohinIdoNyukaTeisei e = new TShohinIdoNyukaTeisei();
		e.setPk(pk);
		// find by nyukaId
		Example<TShohinIdoNyukaTeisei> example = Example.of(e);
		List<TShohinIdoNyukaTeisei> entities = repo.findAll(example);
		return entities;
	}
}
