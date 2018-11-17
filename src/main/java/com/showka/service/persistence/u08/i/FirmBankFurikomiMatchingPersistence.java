package com.showka.service.persistence.u08.i;

/**
 * FB振込マッチングサービス.
 */
public interface FirmBankFurikomiMatchingPersistence {

	/**
	 * 全削除.
	 */
	public void deleteAll();

	/**
	 * 登録.
	 * 
	 * <pre>
	 * 排他制御なし。
	 * </pre>
	 * 
	 */
	public void save(String furikomiId, String furiwakeId);

}
