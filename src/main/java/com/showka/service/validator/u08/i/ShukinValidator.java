package com.showka.service.validator.u08.i;

import com.showka.domain.u08.Shukin;
import com.showka.system.exception.validate.CanNotUpdateOrDeleteException;
import com.showka.system.exception.validate.DuprecatedException;
import com.showka.system.exception.validate.NotAllowedNumberException;
import com.showka.system.exception.validate.NotMatchedException;

public interface ShukinValidator {

	/**
	 * 集金の業務整合性検証
	 * 
	 * <pre>
	 * 金額<=0円の場合、エラー
	 * </pre>
	 * 
	 * @param shukin
	 *            集金
	 * @throws NotAllowedNumberException
	 *             金額は1以上を設定する必要があります。
	 */
	public void validate(Shukin shukin) throws NotAllowedNumberException;

	/**
	 * 集金の登録整合性検証.
	 * 
	 * <pre>
	 * - 集金.担当社員.所属部署 != 集金.入金部署の場合、エラー
	 * - 重複する集金が登録済の場合、エラー
	 *     - 重複条件：顧客、入金日、伝票番号
	 * </pre>
	 * 
	 * @param shukin
	 *            集金
	 * @throws NotMatchedException
	 *             集金担当社員の所属部署と入金部署を一致させる必要があります。
	 * @throws DuprecatedException
	 *             重複するデータがすでに存在します。重複条件：顧客、入金日、伝票番号
	 */
	public void validateForRegister(Shukin shukin) throws NotMatchedException, DuprecatedException;

	/**
	 * 集金の更新業務整合性検証.
	 * 
	 * <pre>
	 * - 集金.担当社員.所属部署 != 集金.入金部署の場合、エラー
	 *     - ただし、担当社員が更新対象でない場合、この検証は不要。
	 * - 入金を計上済みの場合、エラー
	 * - 消込を実施済みの場合、エラー
	 * </pre>
	 * 
	 * @param shukin
	 *            集金
	 * @throws NotMatchedException
	 *             集金担当社員の所属部署と入金部署を一致させる必要があります。
	 * @throws CanNotUpdateOrDeleteException
	 *             更新できません（理由：消込済みのため） or （理由：消込済みのため）
	 * 
	 */
	public void validateForUpdate(Shukin shukin) throws NotMatchedException, CanNotUpdateOrDeleteException;

	/**
	 * 集金の削除整合性検証.
	 * 
	 * <pre>
	 * - 入金を計上済みの場合、エラー
	 * - 消込を実施済みの場合、エラー
	 * </pre>
	 * 
	 * @param shukin
	 *            集金
	 * @throws CanNotDeleteException
	 *             更新できません（理由：消込済みのため） or （理由：消込済みのため）
	 */
	public void validateForDelete(Shukin shukin) throws CanNotUpdateOrDeleteException;
}
