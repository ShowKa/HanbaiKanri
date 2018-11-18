package com.showka.service.validator.u05;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.showka.domain.u05.UriageMeisai;
import com.showka.service.validator.u05.i.UriageMeisaiValidator;
import com.showka.system.exception.NotAllowedNumberException;
import com.showka.system.exception.ValidateException;

@Service
public class UriageMeisaiValidatorImpl implements UriageMeisaiValidator {

	/**
	 * 整合性検証.
	 * 
	 * <pre>
	 * 商品販売単価マイナスの場合エラー
	 * </pre>
	 */
	@Override
	public void validate(UriageMeisai domain) throws ValidateException {
		// 商品販売単価マイナスの場合エラー
		BigDecimal tanka = domain.getHanbaiTanka();
		int compTankaAnd0 = tanka.compareTo(BigDecimal.ZERO);
		if (compTankaAnd0 < 0) {
			throw new NotAllowedNumberException("商品販売単価", BigDecimal.ZERO, null);
		}
	}
}
