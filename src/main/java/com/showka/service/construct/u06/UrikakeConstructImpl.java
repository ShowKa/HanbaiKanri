package com.showka.service.construct.u06;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.showka.domain.builder.UrikakeBuilder;
import com.showka.domain.u05.Uriage;
import com.showka.domain.u06.Urikake;
import com.showka.kubun.HanbaiKubun;
import com.showka.service.construct.u06.i.UrikakeConstruct;
import com.showka.value.AmountOfMoney;
import com.showka.value.TheDate;

@Service
public class UrikakeConstructImpl implements UrikakeConstruct {

	@Override
	public Optional<Urikake> by(Uriage uriage) {
		// 販売区分が掛売じゃない場合
		if (uriage.getHanbaiKubun() != HanbaiKubun.掛売) {
			return Optional.empty();
		}
		// 入金予定日
		TheDate nyukinYoteiDate;
		Optional<TheDate> _nyd = uriage.getDefaultNyukinYoteiDate();
		if (_nyd.isPresent()) {
			nyukinYoteiDate = _nyd.get();
		} else {
			// 入金予定日が取得できない(=>顧客販売区分=現金)場合は、翌月26日払いとする。
			TheDate temp = uriage.getKeijoDate().plusMonths(1);
			nyukinYoteiDate = temp.withDayOfMonth(26);
		}
		// 売掛.金額=売上合計金額税込
		AmountOfMoney kingaku = uriage.getUriageGokeiKakaku().getZeikomi();
		// build
		UrikakeBuilder b = new UrikakeBuilder();
		b.withUriage(uriage);
		b.withNyukinYoteiDate(nyukinYoteiDate);
		b.withKingaku(kingaku);
		return Optional.of(b.build());
	}
}
