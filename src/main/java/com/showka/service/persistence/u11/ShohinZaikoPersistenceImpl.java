package com.showka.service.persistence.u11;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
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
import com.showka.service.crud.z00.i.ShohinCrud;
import com.showka.service.persistence.u11.i.ShohinZaikoPersistence;
import com.showka.service.query.u11.i.ShohinIdoQuery;
import com.showka.service.query.z00.i.BushoDateQuery;
import com.showka.value.EigyoDate;

@Service
public class ShohinZaikoPersistenceImpl implements ShohinZaikoPersistence {

	// private members
	@Autowired
	private TShohinZaikoRepository repo;

	@Autowired
	private ShohinIdoQuery shohinIdoQuery;

	@Autowired
	private ShohinCrud shohinPersistence;

	@Autowired
	private BushoDateQuery bushoDateBusinessService;

	// public methods
	@Override
	public ShohinZaiko getShohinZaiko(Busho busho, EigyoDate date, Shohin shohin) {
		// 在庫データ取得
		TShohinZaikoPK pk = new TShohinZaikoPK();
		pk.setBushoId(busho.getRecordId());
		pk.setEigyoDate(date.toDate());
		pk.setShohinId(shohin.getRecordId());
		Optional<TShohinZaiko> _e = repo.findById(pk);
		// 在庫データがない場合
		if (!_e.isPresent()) {
			ShohinZaikoBuilder b = new ShohinZaikoBuilder();
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
		// build & return
		return b.build();
	}

	@Override
	public List<ShohinZaiko> getShohinZaiko(Busho busho, EigyoDate date) {
		TShohinZaiko entity = new TShohinZaiko();
		TShohinZaikoPK pk = new TShohinZaikoPK();
		pk.setBushoId(busho.getRecordId());
		pk.setEigyoDate(date.toDate());
		entity.setPk(pk);
		Example<TShohinZaiko> example = Example.of(entity);
		List<TShohinZaiko> shohinZaikoList = repo.findAll(example);
		return shohinZaikoList.stream().map(z -> {
			return getShohinZaiko(busho, date, shohinPersistence.getDomain(z.getShohin().getCode()));
		}).collect(Collectors.toList());
	}

	@Override
	public void kurikoshi(Busho busho, EigyoDate eigyoDate) {
		List<ShohinZaiko> zaikoList = this.getShohinZaiko(busho, eigyoDate);
		EigyoDate nextEigyoDate = bushoDateBusinessService.getNext(busho, eigyoDate);
		zaikoList.stream().filter(zaiko -> {
			// 在庫数0以外のみ抽出
			return zaiko.getNumber().longValue() != 0;
		}).forEach(zaiko -> {
			// 営業日を更新して登録
			ShohinZaikoBuilder b = new ShohinZaikoBuilder();
			b.withDate(nextEigyoDate);
			b.withKurikoshiNumber(zaiko.getNumber());
			b.withShohinIdoList(new ArrayList<>());
			ShohinZaiko kurikosiZaiko = b.apply(zaiko);
			this.save(kurikosiZaiko);
		});
	}

	@Override
	public void saveZeroIfEmpty(Busho busho, EigyoDate date, Shohin shohin) {
		// pk
		TShohinZaikoPK id = new TShohinZaikoPK();
		id.setBushoId(busho.getRecordId());
		id.setEigyoDate(date.toDate());
		id.setShohinId(shohin.getRecordId());
		// check exists
		boolean exists = repo.existsById(id);
		if (!exists) {
			// 下記メソッドをよべば空のドメインが作られるので利用。
			ShohinZaiko zaiko = this.getShohinZaiko(busho, date, shohin);
			this.save(zaiko);
		}
	}

	// private methods
	/**
	 * 商品在庫登録.
	 * 
	 * <pre>
	 * 現時点の在庫数でレコードの在庫数の値を登録します。
	 * なおinsertのみを想定しています。
	 * </pre>
	 * 
	 * @param zaiko
	 *            商品在庫
	 */
	private void save(ShohinZaiko zaiko) {
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
		String recordId = UUID.randomUUID().toString();
		e.setRecordId(recordId);
		// save
		repo.save(e);
	}
}
