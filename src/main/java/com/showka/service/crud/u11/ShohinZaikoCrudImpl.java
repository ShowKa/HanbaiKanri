package com.showka.service.crud.u11;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.builder.ShohinZaikoBuilder;
import com.showka.domain.u11.ShohinIdo;
import com.showka.domain.u11.ShohinZaiko;
import com.showka.domain.u11.ShohinZaiko.ShohinIdoOnDate;
import com.showka.domain.z00.Busho;
import com.showka.domain.z00.Shohin;
import com.showka.entity.TShohinZaiko;
import com.showka.entity.TShohinZaikoPK;
import com.showka.repository.i.TShohinZaikoRepository;
import com.showka.service.crud.u11.i.ShohinZaikoCrud;
import com.showka.service.crud.z00.i.BushoCrud;
import com.showka.service.crud.z00.i.ShohinCrud;
import com.showka.service.query.u11.i.ShohinIdoQuery;
import com.showka.value.EigyoDate;

@Service
public class ShohinZaikoCrudImpl implements ShohinZaikoCrud {

	@Autowired
	private TShohinZaikoRepository repo;

	@Autowired
	private BushoCrud bushoCrud;

	@Autowired
	private ShohinCrud shohinCrud;

	@Autowired
	private ShohinIdoQuery shohinIdoQuery;

	// 排他制御対象外
	@Override
	public void save(ShohinZaiko zaiko) {
		// entity
		TShohinZaiko e = new TShohinZaiko();
		// pk
		TShohinZaikoPK pk = new TShohinZaikoPK();
		e.setPk(pk);
		// 部署
		pk.setBushoId(zaiko.getBusho().getRecordId());
		// 商品
		pk.setShohinId(zaiko.getShohin().getRecordId());
		// 営業日
		pk.setEigyoDate(zaiko.getDate().toDate());
		// 在庫数
		e.setNumber(zaiko.getNumber());
		// record id
		e.setRecordId(zaiko.getRecordId());
		// save
		repo.save(e);
	}

	@Override
	public void delete(ShohinZaiko zaiko) {
		throw new RuntimeException("未実装");
	}

	@Override
	public ShohinZaiko getDomain(TShohinZaikoPK pk) {
		// entity
		TShohinZaiko e = repo.getOne(pk);
		// 部署
		String bushoCode = e.getBusho().getCode();
		Busho busho = bushoCrud.getDomain(bushoCode);
		// 営業日
		EigyoDate date = new EigyoDate(e.getEigyoDate());
		// 商品
		String shohinCode = e.getShohin().getCode();
		Shohin shohin = shohinCrud.getDomain(shohinCode);
		// build
		ShohinZaikoBuilder b = new ShohinZaikoBuilder();
		b.withBusho(busho);
		b.withDate(date);
		b.withRecordId(e.getRecordId());
		b.withShohin(shohin);
		b.withKurikoshiNumber(e.getNumber());
		// 商品移動リスト
		List<ShohinIdo> _idoList = shohinIdoQuery.getShohinIdoListInDate(busho, date, shohin);
		List<ShohinIdoOnDate> idoList = _idoList.stream().map(ido -> {
			return new ShohinIdoOnDate(ido, shohin);
		}).collect(Collectors.toList());
		b.withShohinIdoList(idoList);
		// return
		return b.build();
	}

	@Override
	public boolean exists(TShohinZaikoPK pk) {
		return repo.existsById(pk);
	}
}
