package com.showka.service.persistence.u11;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.u11.ShohinZaiko;
import com.showka.domain.z00.Busho;
import com.showka.domain.z00.Shohin;
import com.showka.entity.TShohinZaikoPK;
import com.showka.service.crud.u11.i.ShohinZaikoCrud;
import com.showka.service.persistence.u11.i.ShohinZaikoPersistence;
import com.showka.service.query.u11.i.ShohinZaikoQuery;
import com.showka.service.query.z00.i.BushoDateQuery;
import com.showka.value.EigyoDate;

@Service
public class ShohinZaikoPersistenceImpl implements ShohinZaikoPersistence {

	@Autowired
	private ShohinZaikoCrud shohinZaikoCrud;

	@Autowired
	private ShohinZaikoQuery shohinZaikoQuery;

	@Autowired
	private BushoDateQuery bushoDateQuery;

	@Override
	public void kurikoshi(Busho busho, EigyoDate eigyoDate) {
		List<ShohinZaiko> zaikoList = shohinZaikoQuery.get(busho, eigyoDate);
		EigyoDate nextEigyoDate = bushoDateQuery.getNext(busho, eigyoDate);
		zaikoList.stream().filter(zaiko -> {
			// 在庫数0以外のみ抽出
			return zaiko.getNumber().longValue() != 0;
		}).forEach(zaiko -> {
			// 営業日を更新して繰越登録
			ShohinZaiko kurikosiZaiko = zaiko.cloneForKurikoshi(nextEigyoDate);
			shohinZaikoCrud.save(kurikosiZaiko);
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
		boolean exists = shohinZaikoCrud.exists(id);
		if (!exists) {
			// save ゼロ在庫
			ShohinZaiko zaiko = ShohinZaiko.buildZeroZaiko(busho, date, shohin);
			shohinZaikoCrud.save(zaiko);
		}
	}
}
