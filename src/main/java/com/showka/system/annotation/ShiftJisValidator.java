package com.showka.system.annotation;

import java.io.UnsupportedEncodingException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Shif-JIS Validator.
 *
 * <pre>
 * 文字コードがshift_jis(Windows-31j)か否かを判定します。
 * </pre>
 *
 * @author 樋岡真菜美
 *
 */
class ShiftJisValidator implements ConstraintValidator<ShiftJis, String> {

	/**
	 * 文字コード
	 */
	private static final String CHARACTER_CODE = "Windows-31j";

	@Override
	public void initialize(ShiftJis constraintAnnotation) {
		// do nothing
	}

	/**
	 * 文字コードのshift_jis判定
	 *
	 * @param value
	 *            チェック対象文字列
	 * @return shift_jisだったらtrue
	 */
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		if (value == null) {
			return true;
		}

		try {
			byte[] bytes = value.getBytes(CHARACTER_CODE);
			return value.equals(new String(bytes, CHARACTER_CODE));
		} catch (UnsupportedEncodingException ex) {
			throw new RuntimeException("文字コード判定の処理に失敗しました。", ex);
		}
	}

}