package com.showka.service.crud.u11;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.showka.domain.BushoDomain;
import com.showka.domain.ShohinDomain;
import com.showka.domain.ShohinIdoDomain;
import com.showka.domain.ShohinZaikoDomain;
import com.showka.domain.ShohinZaikoDomain.ShohinIdoOnDate;
import com.showka.domain.builder.ShohinZaikoDomainBuilder;
import com.showka.entity.TShohinZaiko;
import com.showka.entity.TShohinZaikoPK;
import com.showka.repository.i.TShohinZaikoRepository;
import com.showka.service.crud.u11.i.ShohinIdoCrudService;
import com.showka.service.crud.u11.i.ShohinZaikoCrudService;
import com.showka.service.crud.z00.i.MShohinCrudService;
import com.showka.value.TheDate;

@Service
public class ShohinZaikoCrudServiceImpl implements ShohinZaikoCrudService {

	@Autowired
	private TShohinZaikoRepository repo;

	@Autowired
	private ShohinIdoCrudService shohinIdoCrudService;

	@Autowired
	private MShohinCrudService shohinCrudService;

	@Override
	public ShohinZaikoDomain getShohinZaiko(BushoDomain busho, TheDate date, ShohinDomain shohin) {
		// 在庫データ取得
		TShohinZaikoPK pk = new TShohinZaikoPK();
		pk.setBushoId(busho.getRecordId());
		pk.setEigyoDate(date.toDate());
		pk.setShohinId(shohin.getRecordId());
		Optional<TShohinZaiko> _e = repo.findById(pk);
		// 在庫データがない場合
		if (!_e.isPresent()) {
			ShohinZaikoDomainBuilder b = new ShohinZaikoDomainBuilder();
			b.withBusho(busho);
			b.withDate(date);
			b.withRecordId("");
			b.withShohin(shohin);
			b.withKurikoshiNumber(0);
			b.withShohinIdoList(new ArrayList<ShohinIdoOnDate>());
			// build & return
			return b.build();
		}
		// ある場合
		TShohinZaiko e = _e.get();
		ShohinZaikoDomainBuilder b = new ShohinZaikoDomainBuilder();
		b.withBusho(busho);
		b.withDate(date);
		b.withRecordId(e.getRecordId());
		b.withShohin(shohin);
		b.withKurikoshiNumber(e.getNumber());
		// 商品移動リスト
		List<ShohinIdoDomain> _idoList = shohinIdoCrudService.getShohinIdoListInDate(busho, date, shohin);
		List<ShohinIdoOnDate> idoList = _idoList.stream().map(ido -> {
			return new ShohinIdoOnDate(ido, shohin);
		}).collect(Collectors.toList());
		b.withShohinIdoList(idoList);
		// build & return
		return b.build();
	}

	@Override
	public List<ShohinZaikoDomain> getShohinZaiko(BushoDomain busho, TheDate date) {
		TShohinZaiko entity = new TShohinZaiko();
		TShohinZaikoPK pk = new TShohinZaikoPK();
		pk.setBushoId(busho.getRecordId());
		pk.setEigyoDate(date.toDate());
		entity.setPk(pk);
		Example<TShohinZaiko> example = Example.of(entity);
		List<TShohinZaiko> shohinZaikoList = repo.findAll(example);
		return shohinZaikoList.stream().map(z -> {
			return getShohinZaiko(busho, date, shohinCrudService.getDomain(z.getShohin().getCode()));
		}).collect(Collectors.toList());
	}

}
