package com.showka.service.specification.u07;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.showka.domain.builder.SeikyuMeisaiBuilder;
import com.showka.domain.u01.Kokyaku;
import com.showka.domain.u01.NyukinKakeInfo;
import com.showka.domain.u06.Urikake;
import com.showka.domain.u06.UrikakeKeshikomi;
import com.showka.domain.u07.SeikyuMeisai;
import com.showka.service.query.u06.i.UrikakeKeshikomiQuery;
import com.showka.service.specification.u07.i.SeikyuSpecification;
import com.showka.value.EigyoDate;
import com.showka.value.TheDate;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
public class SeikyuUrikakeSpecificationImpl implements SeikyuSpecification {

	/** 顧客. */
	private Kokyaku kokyaku;

	/** 請求日. */
	private EigyoDate seikyuDate;

	/** 売掛リスト. */
	private List<Urikake> urikakeList;

	/** 売掛消込Query(Setterはテスト用) */
	@Autowired
	@Setter(value = AccessLevel.PACKAGE)
	private UrikakeKeshikomiQuery urikakeKeshikomiQuery;

	// constructor
	protected SeikyuUrikakeSpecificationImpl(Kokyaku kokyaku, EigyoDate seikyuDate, List<Urikake> urikakeList) {
		this.kokyaku = kokyaku;
		this.seikyuDate = seikyuDate;
		this.urikakeList = urikakeList;
	}

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
			// 売掛金残高
			UrikakeKeshikomi keshikomi = urikakeKeshikomiQuery.get(urikake.getRecordId());
			b.withKingaku(keshikomi.getZandaka());
			b.withUrikake(urikake);
			return b.build();
		}).collect(Collectors.toList());
	}
}
