package com.showka.service.persistence.u07;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.builder.SeikyuBuilder;
import com.showka.domain.u01.Kokyaku;
import com.showka.domain.u01.NyukinKakeInfo;
import com.showka.domain.u07.Seikyu;
import com.showka.kubun.NyukinHohoKubun;
import com.showka.service.crud.u07.i.SeikyuCrud;
import com.showka.service.persistence.u07.i.SeikyuPersistence;
import com.showka.service.specification.u07.i.SeikyuSpecification;
import com.showka.system.exception.ValidateException;

@Service
public class SeikyuPersistenceImpl implements SeikyuPersistence {

	@Autowired
	private SeikyuCrud seikyuCrud;

	@Override
	public void save(SeikyuSpecification spec) {
		SeikyuBuilder b = new SeikyuBuilder();
		// 顧客
		Kokyaku kokyaku = spec.getKokyaku();
		b.withKokyaku(kokyaku);
		// 請求担当部署
		b.withTantoBusho(kokyaku.getShukanBusho());
		// 入金方法
		Optional<NyukinKakeInfo> _nhk = kokyaku.getNyukinKakeInfo();
		if (!_nhk.isPresent()) {
			throw new ValidateException("請求対象の顧客に入金方法を設定してください。");
		}
		NyukinHohoKubun nyukinHohoKubun = _nhk.get().getNyukinHohoKubun();
		b.withNyukinHohoKubun(nyukinHohoKubun);
		// 請求日
		b.withSeikyuDate(spec.getSeikyuDate());
		// 支払日
		b.withShiharaiDate(spec.getShiharaiDate());
		// 明細
		b.withSeikyuMeisai(spec.getSeikyuMeisai());
		Seikyu seikyu = b.build();
		seikyuCrud.save(seikyu);
	}
}
