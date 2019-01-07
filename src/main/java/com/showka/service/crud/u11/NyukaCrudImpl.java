package com.showka.service.crud.u11;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.builder.NyukaBuilder;
import com.showka.domain.u11.Nyuka;
import com.showka.domain.u11.NyukaSaki;
import com.showka.domain.u11.ShohinIdo;
import com.showka.domain.z00.Busho;
import com.showka.entity.TShohinIdoNyuka;
import com.showka.repository.i.TShohinIdoNyukaRepository;
import com.showka.service.crud.u11.i.NyukaCrud;
import com.showka.service.crud.u11.i.NyukaSakiCrud;
import com.showka.service.crud.u11.i.NyukaTeiseiCrud;
import com.showka.service.crud.u11.i.ShohinIdoCrud;
import com.showka.service.crud.z00.i.BushoCrud;
import com.showka.value.EigyoDate;

@Service
public class NyukaCrudImpl implements NyukaCrud {

	@Autowired
	private TShohinIdoNyukaRepository repo;

	@Autowired
	private BushoCrud bushoCrud;

	@Autowired
	private NyukaSakiCrud nyukaSakiCrud;

	@Autowired
	private ShohinIdoCrud shohinIdoCrud;

	@Autowired
	private NyukaTeiseiCrud nyukaTeiseiCrud;

	@Override
	public void save(Nyuka nyuka) {
		// entity
		Optional<TShohinIdoNyuka> _e = repo.findById(nyuka.getShohinIdoId());
		TShohinIdoNyuka e = _e.orElse(new TShohinIdoNyuka());
		// set columns
		e.setNyukaSakiId(nyuka.getNyukaSakiId());
		e.setShohinIdoId(nyuka.getShohinIdoId());
		// record id
		e.setRecordId(nyuka.getRecordId());
		// OCC
		e.setVersion(nyuka.getVersion());
		// save
		repo.save(e);
	}

	@Override
	public void delete(Nyuka nyuka) {
		TShohinIdoNyuka e = repo.getOne(nyuka.getShohinIdoId());
		e.setVersion(nyuka.getVersion());
		repo.delete(e);
	}

	@Override
	public Nyuka getDomain(String shohinIdoId) {
		// entity
		TShohinIdoNyuka e = repo.getOne(shohinIdoId);
		// 部署
		Busho busho = bushoCrud.getDomain(e.getBusho().getCode());
		// 入荷先
		NyukaSaki nyukaSaki = nyukaSakiCrud.getDomain(e.getNyukaSaki().getCode());
		// 商品移動
		ShohinIdo shohinIdo = shohinIdoCrud.getDomain(e.getShohinIdoId());
		// 入荷訂正
		List<ShohinIdo> teiseiList = nyukaTeiseiCrud.get(null);
		// build
		NyukaBuilder nb = new NyukaBuilder();
		nb.withBusho(busho);
		nb.withNyukaDate(new EigyoDate(e.getShohinIdo().getDate()));
		nb.withNyukaSaki(nyukaSaki);
		nb.withNyukaShohinIdo(shohinIdo);
		nb.withTeiseiList(teiseiList);
		nb.withRecordId(e.getRecordId());
		nb.withVersion(e.getVersion());
		return nb.build();
	}

	@Override
	public boolean exists(String shohinIdoId) {
		return repo.existsById(shohinIdoId);
	}
}
