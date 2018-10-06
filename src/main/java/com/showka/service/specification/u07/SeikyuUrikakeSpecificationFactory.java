package com.showka.service.specification.u07;

import java.util.List;

import org.springframework.stereotype.Component;

import com.showka.domain.u01.Kokyaku;
import com.showka.domain.u06.Urikake;
import com.showka.service.specification.u07.i.SeikyuSpecification;
import com.showka.value.EigyoDate;

@Component
public class SeikyuUrikakeSpecificationFactory {

	/**
	 * SeikyuSpecification 生成
	 * 
	 * @param kokyaku
	 *            顧客
	 * @param seikyuDate
	 *            請求日
	 * @param urikakeList
	 *            売掛リスト
	 * @return 請求仕様
	 */
	public SeikyuSpecification create(Kokyaku kokyaku, EigyoDate seikyuDate, List<Urikake> urikakeList) {
		return new SeikyuUrikakeSpecificationImpl(kokyaku, seikyuDate, urikakeList);
	}
}
