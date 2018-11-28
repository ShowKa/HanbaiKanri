package com.showka.service.persistence.u07;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.u01.Kokyaku;
import com.showka.domain.u06.Urikake;
import com.showka.domain.z00.Busho;
import com.showka.service.persistence.u07.i.SeikyuPersistence;
import com.showka.service.persistence.u07.i.SeikyuUrikakePersistence;
import com.showka.service.query.u01.i.KokyakuQuery;
import com.showka.service.query.u06.i.UrikakeQuery;
import com.showka.service.query.u07.i.ShimeDateQuery;
import com.showka.service.specification.u07.SeikyuUrikakeSpecificationFactory;
import com.showka.service.specification.u07.i.SeikyuSpecification;
import com.showka.value.EigyoDate;
import com.showka.value.ShimeDate;

@Service
public class SeikyuUrikakePersistenceImpl implements SeikyuUrikakePersistence {

	@Autowired
	private KokyakuQuery nyukinKakeInfoQuery;

	@Autowired
	private UrikakeQuery urikakeQuery;

	@Autowired
	private SeikyuPersistence seikyuPersistence;

	@Autowired
	private SeikyuUrikakeSpecificationFactory seikyuUrikakeSpecificationFactory;

	@Autowired
	private ShimeDateQuery shimeDateBusinessService;

	@Override
	public void seikyu(Busho busho, EigyoDate eigyoDate) {
		// 締日リスト
		Set<ShimeDate> shimeDateSet = shimeDateBusinessService.get(busho, eigyoDate);
		// 締日の顧客リスト
		List<Kokyaku> kokyakuList = nyukinKakeInfoQuery.getOnShimeDate(busho, shimeDateSet);
		kokyakuList.forEach(kokyaku -> {
			// 請求
			this.seikyu(kokyaku, eigyoDate);
		});
	}

	@Override
	public void seikyu(Kokyaku kokyaku, EigyoDate eigyoDate) {
		// 売掛リスト取得
		List<Urikake> urikakeList = urikakeQuery.getForSeikyu(kokyaku);
		// 請求
		this.seikyu(kokyaku, eigyoDate, urikakeList);
	}

	@Override
	// TODO 売掛の入金予定を更新しているが排他制御できていない。 ==> 締処理中は画面更新不可とする制御が必要。
	public void seikyu(Kokyaku kokyaku, EigyoDate eigyoDate, List<Urikake> urikakeList) {
		// 請求仕様
		SeikyuSpecification spec = seikyuUrikakeSpecificationFactory.create(kokyaku, eigyoDate, urikakeList);
		// save 請求
		seikyuPersistence.save(spec);
	}
}
