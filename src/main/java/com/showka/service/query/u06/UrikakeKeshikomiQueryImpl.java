package com.showka.service.query.u06;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.builder.UrikakeKeshikomiBuilder;
import com.showka.domain.u06.Urikake;
import com.showka.domain.u06.UrikakeKeshikomi;
import com.showka.domain.u08.Keshikomi;
import com.showka.service.crud.u06.i.UrikakeCrud;
import com.showka.service.query.u06.i.UrikakeKeshikomiQuery;
import com.showka.service.query.u08.i.KeshikomiQuery;
import com.showka.value.AmountOfMoney;

@Service
public class UrikakeKeshikomiQueryImpl implements UrikakeKeshikomiQuery {

	@Autowired
	private UrikakeCrud urikakeCrud;

	@Autowired
	private KeshikomiQuery keshikomiQuery;

	@Override
	public UrikakeKeshikomi get(String urikakeId) {
		// get 売掛
		Urikake urikake = urikakeCrud.getDomainById(urikakeId);
		// get 消込リスト
		Set<Keshikomi> keshikomiList = keshikomiQuery.getOfUrikake(urikakeId);
		// build 売掛消込
		UrikakeKeshikomiBuilder ub = new UrikakeKeshikomiBuilder();
		ub.withUrikake(urikake);
		ub.withKeshikomiSet(keshikomiList);
		return ub.build();
	}

	@Override
	public AmountOfMoney getAsOf(Urikake urikake, Keshikomi keshikomi) {
		// 売掛の消込リスト取得
		String urikakeId = urikake.getRecordId();
		UrikakeKeshikomi urikakeKeshikomi = this.get(urikakeId);
		// 引数.消込と同様の消込を除去
		Set<Keshikomi> keshikomiSet = urikakeKeshikomi.getKeshikomiSet();
		keshikomiSet.removeIf(k -> {
			return k.equals(keshikomi);
		});
		// 引数.タイムスタンプ < 対象 の消込を除去
		keshikomiSet.removeIf(k -> {
			return k.getTimestamp().isAfter(keshikomi.getTimestamp());
		});
		// 残高返却
		return urikakeKeshikomi.getZandaka();
	}

}
