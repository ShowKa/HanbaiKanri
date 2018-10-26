package com.showka.service.validate.u05.i;

import com.showka.domain.u05.Uriage;
import com.showka.entity.TUriagePK;
import com.showka.system.exception.ValidateException;

public interface UriageValidateService {

	/**
	 * 整合性検証.
	 * 
	 * @param domain
	 *            売上ドメイン
	 * @throws ValidateException
	 *             整合性例外
	 */
	public void validate(Uriage domain) throws ValidateException;

	/**
	 * 初回登録時整合性検証.
	 * 
	 * <pre>
	 * FIXME 売上日=営業日の検証が必要。
	 * </pre>
	 * 
	 * @param domain
	 *            売上ドメイン
	 * @throws ValidateException
	 *             整合性例外
	 */
	public void validateForRegister(Uriage domain) throws ValidateException;

	/**
	 * 更新時整合性検証.
	 * 
	 * @param domain
	 *            売上ドメイン
	 * @throws ValidateException
	 *             整合性例外
	 */
	public void validateForUpdate(Uriage domain) throws ValidateException;

	/**
	 * 削除時整合性検証.
	 * 
	 * @param domain
	 *            ドメイン
	 * @throws ValidateException
	 *             整合性例外
	 */
	public void validateForDelete(TUriagePK pk) throws ValidateException;

	/**
	 * キャンセル時整合性検証.
	 * 
	 * @param pk
	 *            売上の主キー
	 * @throws ValidateException
	 *             整合性例外
	 */
	public void validateForCancel(TUriagePK pk) throws ValidateException;

}
