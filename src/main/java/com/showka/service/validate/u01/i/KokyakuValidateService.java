package com.showka.service.validate.u01.i;

import com.showka.domain.KokyakuDomain;
import com.showka.system.exception.ValidateException;

public interface KokyakuValidateService {

	/**
	 * 参照時の整合性検証
	 *
	 * @param kokyakuCode
	 * @throws ValidateException
	 */
	public void validateForRefer(String kokyakuCode) throws ValidateException;

	/**
	 * 新規登録時の整合性検証
	 *
	 * <pre>
	 * 採番した顧客コードが未登録であることを確認する。
	 * </pre>
	 *
	 * @param domain
	 * @throws ValidateException
	 */
	public void validateForRegister(KokyakuDomain domain) throws ValidateException;

	/**
	 *
	 * 整合性検証.
	 *
	 * <pre>
	 * 顧客区分と販売区分の整合性検証
	 * 個人顧客に掛売りの販売区分を設定していないことを確認する.
	 * </pre>
	 *
	 * @param domain
	 * @throws ValidateException
	 */
	public void validate(KokyakuDomain domain) throws ValidateException;

}
