package com.showka.service.crud.u08;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.FurikomiIrainin;
import com.showka.domain.FurikomiIraininSet;
import com.showka.domain.Seikyu;
import com.showka.domain.Urikake;
import com.showka.entity.WFirmBankFuriwake;
import com.showka.entity.WFirmBankFuriwakePK;
import com.showka.repository.i.WFirmBankFuriwakeRepository;
import com.showka.service.crud.u08.i.FirmBankFuriwakeCrudService;
import com.showka.service.search.u01.i.FurikomiIraininSearchService;
import com.showka.service.specification.u06.i.UrikakeKeshikomiSpecificationService;

@Service
public class FirmBankFuriwakeCrudServiceImpl implements FirmBankFuriwakeCrudService {

	@Autowired
	private FurikomiIraininSearchService furikomiIraininSearchService;

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
		WFirmBankFuriwake e = new WFirmBankFuriwake();
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
