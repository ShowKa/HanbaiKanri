package com.showka.service.specification.u06;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.Urikake;
import com.showka.domain.UrikakeKeshikomi;
import com.showka.service.crud.u06.i.UrikakeKeshikomiCrudService;
import com.showka.service.specification.u06.i.UrikakeKeshikomiSpecificationService;
import com.showka.value.AmountOfMoney;

@Service
public class UrikakeKeshikomiSpecificationServiceImpl implements UrikakeKeshikomiSpecificationService {

	@Autowired
	private UrikakeKeshikomiCrudService urikakeKeshikomiCrudService;

	@Override
	public AmountOfMoney getZandakaOf(Urikake urikake) {
		// 売掛の消込リスト取得
		String urikakeId = urikake.getRecordId();
		UrikakeKeshikomi urikakeKeshikomi = urikakeKeshikomiCrudService.getDomain(urikakeId);
		return urikakeKeshikomi.getZandaka();
	}
}
