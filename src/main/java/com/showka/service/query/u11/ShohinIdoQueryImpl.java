package com.showka.service.query.u11;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.showka.domain.u11.ShohinIdo;
import com.showka.domain.z00.Busho;
import com.showka.domain.z00.Shohin;
import com.showka.entity.TShohinIdo;
import com.showka.entity.TShohinIdoMeisai;
import com.showka.repository.i.TShohinIdoMeisaiRepository;
import com.showka.service.crud.u11.i.ShohinIdoCrud;
import com.showka.service.query.u11.i.ShohinIdoQuery;
import com.showka.value.TheDate;

@Service
public class ShohinIdoQueryImpl implements ShohinIdoQuery {

	@Autowired
	private ShohinIdoCrud shohinIdoCrud;

	@Autowired
	private TShohinIdoMeisaiRepository tShohinIdoMeisaiRepository;

	@Override
	public List<ShohinIdo> getShohinIdoListInDate(Busho busho, TheDate date, Shohin shohin) {
		// find
		List<TShohinIdoMeisai> idoMeisaiList = this.findIdoMeisaiEntity(busho, date, shohin);
		// distinct
		Stream<String> idList = idoMeisaiList.stream().map(meisai -> {
			return meisai.getShohinIdoId();
		}).distinct();
		// build domain
		return idList.map(id -> {
			return shohinIdoCrud.getDomain(id);
		}).collect(Collectors.toList());
	}

	List<TShohinIdoMeisai> findIdoMeisaiEntity(Busho busho, TheDate date, Shohin shohin) {
		// criteria
		TShohinIdo ido = new TShohinIdo();
		TShohinIdoMeisai idoMeisai = new TShohinIdoMeisai();
		ido.setBushoId(busho.getRecordId());
		ido.setDate(date.toDate());
		idoMeisai.setShohinId(shohin.getRecordId());
		idoMeisai.setShohinIdo(ido);
		// find
		Example<TShohinIdoMeisai> example = Example.of(idoMeisai);
		List<TShohinIdoMeisai> idoMeisaiList = tShohinIdoMeisaiRepository.findAll(example);
		return idoMeisaiList;
	}
}
