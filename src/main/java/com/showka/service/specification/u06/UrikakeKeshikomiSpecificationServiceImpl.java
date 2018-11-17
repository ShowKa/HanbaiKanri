package com.showka.service.specification.u06;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.u06.Urikake;
import com.showka.domain.u06.UrikakeKeshikomi;
import com.showka.domain.u08.Keshikomi;
import com.showka.service.persistence.u06.i.UrikakeKeshikomiPersistence;
import com.showka.service.specification.u06.i.UrikakeKeshikomiSpecificationService;
import com.showka.value.AmountOfMoney;

@Service
public class UrikakeKeshikomiSpecificationServiceImpl implements UrikakeKeshikomiSpecificationService {

	@Autowired
	private UrikakeKeshikomiPersistence urikakeKeshikomiPersistence;

	@Override
	public AmountOfMoney getZandakaOf(Urikake urikake) {
		// 売掛の消込リスト取得
		String urikakeId = urikake.getRecordId();
		UrikakeKeshikomi urikakeKeshikomi = urikakeKeshikomiPersistence.getDomain(urikakeId);
		return urikakeKeshikomi.getZandaka();
	}

	@Override
	public AmountOfMoney getZandakaAsOfKeshikomi(Urikake urikake, Keshikomi keshikomi) {
		// 売掛の消込リスト取得
		String urikakeId = urikake.getRecordId();
		UrikakeKeshikomi urikakeKeshikomi = urikakeKeshikomiPersistence.getDomain(urikakeId);
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
