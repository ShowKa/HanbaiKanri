package com.showka.service.crud.u07;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.Busho;
import com.showka.domain.Kokyaku;
import com.showka.domain.Urikake;
import com.showka.service.crud.u05.i.UrikakeCrudService;
import com.showka.service.crud.u07.i.SeikyuCrudService;
import com.showka.service.crud.u07.i.SeikyuUrikakeCrudService;
import com.showka.service.search.u01.i.NyukinKakeInfoSearchService;
import com.showka.service.search.u05.i.UrikakeSearchService;
import com.showka.service.specification.u07.SeikyuUrikakeSpecificationFactory;
import com.showka.service.specification.u07.i.SeikyuSpecification;
import com.showka.value.EigyoDate;

@Service
public class SeikyuUrikakeCrudServiceImpl implements SeikyuUrikakeCrudService {

	@Autowired
	private NyukinKakeInfoSearchService nyukinKakeInfoSearchService;

	@Autowired
	private UrikakeSearchService urikakeSearchService;

	@Autowired
	private UrikakeCrudService urikakeCrudService;

	@Autowired
	private SeikyuCrudService seikyuCrudService;

	@Autowired
	private SeikyuUrikakeSpecificationFactory seikyuUrikakeSpecificationFactory;

	@Override
	public void seikyu(Busho busho, EigyoDate shimeDate) {
		// 締日の顧客リスト
		List<Kokyaku> kokyakuList = nyukinKakeInfoSearchService.getKokyakuOnShimeDate(busho, shimeDate);
		kokyakuList.forEach(kokyaku -> {
			// 請求
			this.seikyu(kokyaku, shimeDate);
		});
	}

	@Override
	public void seikyu(Kokyaku kokyaku, EigyoDate shimeDate) {
		// 売掛リスト取得
		String kokyakuId = kokyaku.getRecordId();
		List<Urikake> urikakeList = urikakeSearchService.getUrikakeOfKokyaku(kokyakuId);
		// 請求
		this.seikyu(kokyaku, shimeDate, urikakeList);
	}

	@Override
	public void seikyu(Kokyaku kokyaku, EigyoDate shimeDate, List<Urikake> urikakeList) {
		// 請求仕様
		SeikyuSpecification spec = seikyuUrikakeSpecificationFactory.create(kokyaku, shimeDate, urikakeList);
		// save
		seikyuCrudService.save(spec);
		// 売掛の入金予定日更新
		urikakeList.forEach(urikake -> {
			EigyoDate shiharaiDate = spec.getShiharaiDate();
			// TODO OCC?
			urikakeCrudService.updateNyukinYoteiDate(urikake, shiharaiDate);
		});
	}
}
