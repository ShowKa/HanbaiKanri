package com.showka.service.validate.u08.i;

import com.showka.domain.u08.Shukin;
import com.showka.system.exception.CanNotDeleteException;
import com.showka.system.exception.CanNotUpdateException;
import com.showka.system.exception.DuprecatedException;
import com.showka.system.exception.NotAllowedNumberException;
import com.showka.system.exception.NotMatchedException;

public interface ShukinValidateService {

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
	 * - 消込を実施済みの場合、エラー
	 * </pre>
	 * 
	 * @param shukin
	 *            集金
	 * @throws NotMatchedException
	 *             集金担当社員の所属部署と入金部署を一致させる必要があります。
	 * @throws CanNotUpdateException
	 *             更新できません（理由：消込済みのため）
	 * 
	 */
	public void validateForUpdate(Shukin shukin) throws NotMatchedException, CanNotUpdateException;

	/**
	 * 集金の削除整合性検証.
	 * 
	 * <pre>
	 * 消込を実施済みの場合、エラー
	 * </pre>
	 * 
	 * @param shukin
	 *            集金
	 * @throws CanNotDeleteException
	 *             削除できません（理由：消込済みのため）
	 */
	public void validateForDelete(Shukin shukin) throws CanNotDeleteException;
}
