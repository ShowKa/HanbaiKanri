package com.showka.service.query.u08.i;

import java.util.List;

import com.showka.domain.u08.MatchedFBFurikomi;
import com.showka.value.TheDate;

/**
 * マッチング済FB振込を検索.
 */
public interface MatchedFBFurikomiQuery {
	/**
	 * 
	 * マッチング済FB振込を検索.
	 * 
	 * <pre>
	 * FB振込マッチングテーブルにデータがあるのが前提。
	 * テーブルデータを削除していた場合は、結果 = 0件
	 * </pre>
	 * 
	 * @param transmissionDate
	 *            伝送日付.
	 */
	public List<MatchedFBFurikomi> get(TheDate transmissionDate);
}
