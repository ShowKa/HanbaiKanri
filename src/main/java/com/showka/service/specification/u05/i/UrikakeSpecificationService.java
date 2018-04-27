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
	 * 売上から売掛をビルド
	 * 
	 * <pre>
	 * 掛売りじゃない場合はemptyを返却。
	 * recordId, versionは設定しない。
	 * 入金予定日については、顧客の入金掛売情報から予定日を算出して設定する。
	 * ただし、顧客の入金掛売情報がない場合は、翌月26日払いとする（月末締め26日払いを想定）。
	 * </pre>
	 * 
	 * @param uriage
	 *            売上
	 * @return 売掛
	 */
	public Optional<Urikake> buildUrikakeBy(Uriage uriage);
}
