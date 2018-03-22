package com.showka.service.specification.u05;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.showka.domain.Uriage;
import com.showka.domain.Urikake;
import com.showka.domain.builder.UrikakeBuilder;
import com.showka.kubun.HanbaiKubun;
import com.showka.service.specification.u05.i.UrikakeSpecificationService;
import com.showka.value.EigyoDate;

@Service
public class UrikakeSpecificationServiceImpl implements UrikakeSpecificationService {

	@Override
	public Optional<Urikake> buildUrikakeBy(Uriage uriage) {
		// 販売区分が掛売じゃない場合
		if (uriage.getHanbaiKubun() != HanbaiKubun.掛売) {
			return Optional.empty();
		}
		// 入金予定日
		EigyoDate nyukinYoteiDate = uriage.getNyukinYoteiDate();
		// 残高=売上合計金額税込
		Integer zandaka = uriage.getUriageGokeiKakaku().getZeikomiKakaku().intValue();
		// build
		UrikakeBuilder b = new UrikakeBuilder();
		b.withUriage(uriage);
		b.withNyukinYoteiDate(nyukinYoteiDate);
		b.withZandaka(zandaka);
		return Optional.of(b.build());
	}
}
