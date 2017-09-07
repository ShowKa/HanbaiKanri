package com.showka.service.validate.u05;

import java.math.BigDecimal;

import com.showka.domain.UriageMeisaiDomain;
import com.showka.service.validate.u05.i.UriageMeisaiValidateService;
import com.showka.system.exception.NotAllowedNumberException;
import com.showka.system.exception.ValidateException;

public class UriageMeisaiValidateServiceImpl implements UriageMeisaiValidateService {

	@Override
	public void validate(UriageMeisaiDomain domain) throws ValidateException {

		// 商品販売単価マイナスの場合エラー
		int compareHanbaiTanka = domain.getHanbaiTanka().compareTo(BigDecimal.ZERO);
		if (compareHanbaiTanka < 0) {
			throw new NotAllowedNumberException("商品販売単価", BigDecimal.ZERO, null);
		}
	}
}
