package com.showka.service.specification.u05.i;

import java.util.Optional;

import com.showka.domain.Uriage;
import com.showka.domain.Urikake;

/**
 * 売掛の仕様.
 *
 */
public interface UrikakeSpecificationService {

	/**
	 * 売上から売掛をビル
	 * 
	 * <pre>
	 * 掛売りじゃない場合はemptyを返却。
	 * recordId, versionは設定しない。
	 * </pre>
	 * 
	 * @param uriage
	 *            売上
	 * @return 売上の売掛
	 */
	public Optional<Urikake> buildUrikakeBy(Uriage uriage);
}
