package com.showka.service.persistence.u08;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.u01.FurikomiIrainin;
import com.showka.domain.u01.FurikomiIraininSet;
import com.showka.domain.u06.Urikake;
import com.showka.domain.u07.Seikyu;
import com.showka.entity.WFirmBankFuriwake;
import com.showka.entity.WFirmBankFuriwakePK;
import com.showka.repository.i.WFirmBankFuriwakeRepository;
import com.showka.service.persistence.u08.i.FirmBankFuriwakePersistence;
import com.showka.service.query.u01.i.FurikomiIraininQuery;
import com.showka.service.specification.u06.i.UrikakeKeshikomiSpecificationService;

@Service
public class FirmBankFuriwakePersistenceImpl implements FirmBankFuriwakePersistence {

	@Autowired
	private FurikomiIraininQuery furikomiIraininSearchService;

	@Autowired
	private UrikakeKeshikomiSpecificationService urikakeKeshikomiSpecificationService;

	@Autowired
	private WFirmBankFuriwakeRepository repo;

	@Override
	public void deleteAll() {
		repo.deleteAll();
	}

	@Override
	public void save(List<Seikyu> seikyuList) {
		seikyuList.forEach(this::save);
	}

	@Override
	public void save(Seikyu seikyu) {
		// 振込依頼人set 取得
		FurikomiIraininSet furikomiIrainin = furikomiIraininSearchService.search(seikyu.getKokyaku());
		// save
		furikomiIrainin.getSet().forEach(f -> {
			this.save(seikyu, f);
		});
	}

	void save(Seikyu seikyu, FurikomiIrainin furikomiIrainin) {
		// pk
		WFirmBankFuriwakePK pk = new WFirmBankFuriwakePK();
		String seikyuId = seikyu.getRecordId();
		pk.setSeikyuId(seikyuId);
		pk.setFurikomiIraininId(furikomiIrainin.getRecordId());
		// entity
		WFirmBankFuriwake e = repo.findById(pk).orElse(new WFirmBankFuriwake());
		e.setPk(pk);
		// 請求合計金額でなく、請求明細の各売掛.残高の合計
		int urikakeZandakaGokei = seikyu.getSeikyuMeisai().stream().mapToInt(m -> {
			Urikake urikake = m.getUrikake();
			return urikakeKeshikomiSpecificationService.getZandakaOf(urikake).intValue();
		}).sum();
		e.setSaikenKingaku(urikakeZandakaGokei);
		// record id
		e.setRecordId(UUID.randomUUID().toString());
		// save
		repo.save(e);
	}
}
