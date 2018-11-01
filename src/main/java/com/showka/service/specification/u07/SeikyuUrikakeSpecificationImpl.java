package com.showka.service.specification.u07;

import java.util.List;
import java.util.stream.Collectors;

import com.showka.domain.builder.SeikyuMeisaiBuilder;
import com.showka.domain.u01.Kokyaku;
import com.showka.domain.u01.NyukinKakeInfo;
import com.showka.domain.u06.Urikake;
import com.showka.domain.u07.SeikyuMeisai;
import com.showka.service.specification.u07.i.SeikyuSpecification;
import com.showka.value.EigyoDate;
import com.showka.value.TheDate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SeikyuUrikakeSpecificationImpl implements SeikyuSpecification {

	/** 顧客. */
	private Kokyaku kokyaku;

	/** 請求日. */
	private EigyoDate seikyuDate;

	/** 売掛リスト. */
	private List<Urikake> urikakeList;

	@Override
	public TheDate getShiharaiDate() {
		// 支払日=顧客の入金掛売情報から入金予定日を算出
		// TODO 販売区分=現金の顧客の場合、請求日の翌月末日。顧客クラスにmethodを作るべき。
		NyukinKakeInfo nyukinKakeInfo = kokyaku.getNyukinKakeInfo().get();
		TheDate shiharaiDate = nyukinKakeInfo.getNyukinYoteiDate(seikyuDate);
		return shiharaiDate;
	}

	@Override
	public List<SeikyuMeisai> getSeikyuMeisai() {
		// 請求明細
		return urikakeList.stream().map(urikake -> {
			SeikyuMeisaiBuilder b = new SeikyuMeisaiBuilder();
			// FIXME 売掛金残高にするべき（これだと、消込されてても請求金額に含まれてしまう）
			// urikakeKeshikomiSpecificationService.getZandakaOf(urikake).intValue();
			b.withKingaku(urikake.getKingaku());
			b.withUrikake(urikake);
			return b.build();
		}).collect(Collectors.toList());
	}
}
