package com.showka.service.specification.u07;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.Busho;
import com.showka.domain.Kokyaku;
import com.showka.domain.NyukinKakeInfo;
import com.showka.domain.Seikyu;
import com.showka.domain.SeikyuMeisai;
import com.showka.domain.Urikake;
import com.showka.domain.builder.SeikyuBuilder;
import com.showka.domain.builder.SeikyuMeisaiBuilder;
import com.showka.service.crud.u05.i.UrikakeCrudService;
import com.showka.service.crud.u07.i.SeikyuCrudService;
import com.showka.service.search.u01.i.NyukinKakeInfoSearchService;
import com.showka.service.search.u05.i.UrikakeSearchService;
import com.showka.service.specification.u07.i.SeikyuSpecification;
import com.showka.value.EigyoDate;

@Service
public class SeikyuSpecificationImpl implements SeikyuSpecification {

	@Autowired
	private NyukinKakeInfoSearchService nyukinKakeInfoSearchService;

	@Autowired
	private UrikakeSearchService urikakeSearchService;

	@Autowired
	private SeikyuCrudService seikyuCrudService;

	@Autowired
	private UrikakeCrudService urikakeCrudService;

	@Override
	public void seikyu(Busho busho, EigyoDate shimeDate) {
		// 締日の顧客リスト
		List<Kokyaku> kokyakuList = nyukinKakeInfoSearchService.getKokyakuOnShimeDate(busho, shimeDate);
		kokyakuList.forEach(kokyaku -> {
			this.seikyu(kokyaku, shimeDate);
		});
	}

	@Override
	public void seikyu(Kokyaku kokyaku, EigyoDate shimeDate) {
		// 売掛
		String kokyakuCode = kokyaku.getCode();
		List<Urikake> urikakeList = urikakeSearchService.getUrikakeOfKokyaku(kokyakuCode);
		// 請求明細
		List<SeikyuMeisai> meisai = urikakeList.stream().map(urikake -> {
			SeikyuMeisaiBuilder b = new SeikyuMeisaiBuilder();
			b.withKingaku(urikake.getZandaka());
			b.withUrikake(urikake);
			return b.build();
		}).collect(Collectors.toList());
		// 支払日=顧客の入金掛売情報から入金予定日を算出
		// TODO 販売区分=現金の顧客
		NyukinKakeInfo nyukinKakeInfo = kokyaku.getNyukinKakeInfo().get();
		EigyoDate shiharaiDate = nyukinKakeInfo.getNyukinYoteiDate(shimeDate);
		// 請求
		SeikyuBuilder b = new SeikyuBuilder();
		b.withKokyaku(kokyaku);
		b.withSeikyuDate(shimeDate);
		b.withSeikyuMeisai(meisai);
		b.withShiharaiDate(shiharaiDate);
		Seikyu seikyu = b.build();
		// save
		seikyuCrudService.save(seikyu);
		// 売掛の入金予定日更新
		meisai.forEach(m -> {
			Urikake urikake = m.getUrikake();
			// TODO OCC?
			urikakeCrudService.updateNyukinYoteiDate(urikake, shiharaiDate);
		});
	}
}
