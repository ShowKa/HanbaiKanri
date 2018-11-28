package com.showka.service.query.u11;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.showka.domain.u11.ShohinZaiko;
import com.showka.domain.z00.Busho;
import com.showka.domain.z00.Shohin;
import com.showka.entity.TShohinZaiko;
import com.showka.entity.TShohinZaikoPK;
import com.showka.repository.i.TShohinZaikoRepository;
import com.showka.service.crud.u11.i.ShohinZaikoCrud;
import com.showka.service.query.u11.i.ShohinZaikoQuery;
import com.showka.value.EigyoDate;

@Service
public class ShohinZaikoQueryImpl implements ShohinZaikoQuery {

	@Autowired
	private TShohinZaikoRepository repo;

	@Autowired
	private ShohinZaikoCrud shohinZaikoCrud;

	@Override
	public ShohinZaiko get(Busho busho, EigyoDate date, Shohin shohin) {
		// 在庫データ取得
		TShohinZaikoPK pk = new TShohinZaikoPK();
		pk.setBushoId(busho.getRecordId());
		pk.setEigyoDate(date.toDate());
		pk.setShohinId(shohin.getRecordId());
		boolean exists = shohinZaikoCrud.exists(pk);
		// 在庫データがない場合
		if (!exists) {
			return ShohinZaiko.buildZeroZaiko(busho, date, shohin);
		}
		// ある場合
		return shohinZaikoCrud.getDomain(pk);
	}

	@Override
	public List<ShohinZaiko> get(Busho busho, EigyoDate date) {
		List<TShohinZaiko> shohinZaikoList = this.findEntity(busho, date);
		return shohinZaikoList.stream().map(_z -> {
			return shohinZaikoCrud.getDomain(_z.getPk());
		}).collect(Collectors.toList());
	}

	/**
	 * 部署・日付を条件にして商品在庫を取得
	 * 
	 * @param busho
	 *            部署
	 * @param date
	 *            日付
	 * @return 商品在庫リスト
	 */
	List<TShohinZaiko> findEntity(Busho busho, EigyoDate date) {
		TShohinZaiko entity = new TShohinZaiko();
		TShohinZaikoPK pk = new TShohinZaikoPK();
		pk.setBushoId(busho.getRecordId());
		pk.setEigyoDate(date.toDate());
		entity.setPk(pk);
		Example<TShohinZaiko> example = Example.of(entity);
		List<TShohinZaiko> shohinZaikoList = repo.findAll(example);
		return shohinZaikoList;
	}
}
