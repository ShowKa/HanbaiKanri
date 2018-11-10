package com.showka.service.crud.u07;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.u01.Kokyaku;
import com.showka.domain.u06.Urikake;
import com.showka.domain.z00.Busho;
import com.showka.service.crud.u07.i.SeikyuCrudService;
import com.showka.service.crud.u07.i.SeikyuUrikakeCrudService;
import com.showka.service.search.u01.i.NyukinKakeInfoSearchService;
import com.showka.service.search.u06.i.UrikakeSearchService;
import com.showka.service.specification.u07.SeikyuUrikakeSpecificationFactory;
import com.showka.service.specification.u07.i.SeikyuSpecification;
import com.showka.service.specification.u07.i.ShimeDateBusinessService;
import com.showka.value.EigyoDate;
import com.showka.value.ShimeDate;

@Service
public class SeikyuUrikakeCrudServiceImpl implements SeikyuUrikakeCrudService {

	@Autowired
	private NyukinKakeInfoSearchService nyukinKakeInfoSearchService;

	@Autowired
	private UrikakeSearchService urikakeSearchService;

	@Autowired
	private SeikyuCrudService seikyuCrudService;

	@Autowired
	private SeikyuUrikakeSpecificationFactory seikyuUrikakeSpecificationFactory;

	@Autowired
	private ShimeDateBusinessService shimeDateBusinessService;

	@Override
	public void seikyu(Busho busho, EigyoDate eigyoDate) {
		// 締日リスト
		Set<ShimeDate> shimeDateSet = shimeDateBusinessService.getShimeDate(busho, eigyoDate);
		// 締日の顧客リスト
		List<Kokyaku> kokyakuList = nyukinKakeInfoSearchService.getKokyakuOnShimeDate(busho, shimeDateSet);
		kokyakuList.forEach(kokyaku -> {
			// 請求
			this.seikyu(kokyaku, eigyoDate);
		});
	}

	@Override
	public void seikyu(Kokyaku kokyaku, EigyoDate eigyoDate) {
		// 売掛リスト取得
		List<Urikake> urikakeList = urikakeSearchService.getUrikakeForSeikyu(kokyaku);
		// 請求
		this.seikyu(kokyaku, eigyoDate, urikakeList);
	}

	@Override
	// TODO 売掛の入金予定を更新しているが排他制御できていない。 ==> 締処理中は画面更新不可とする制御が必要。
	public void seikyu(Kokyaku kokyaku, EigyoDate eigyoDate, List<Urikake> urikakeList) {
		// 請求仕様
		SeikyuSpecification spec = seikyuUrikakeSpecificationFactory.create(kokyaku, eigyoDate, urikakeList);
		// save 請求
		seikyuCrudService.save(spec);
	}
}
