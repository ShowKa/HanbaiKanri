package com.showka.service.crud.u08.i;

import java.util.Set;

import com.showka.domain.MatchedFBFurikomi;
import com.showka.domain.Nyukin;
import com.showka.domain.NyukinKeshikomi;
import com.showka.value.EigyoDate;

public interface NyukinKeshikomiCrudService {
	/**
	 * 保存.
	 * 
	 * @param date
	 *            消込登録対象日付
	 * @param nyukinKeshikomi
	 *            入金消込
	 */
	public void save(EigyoDate date, NyukinKeshikomi nyukinKeshikomi);

	/**
	 * 保存.
	 * 
	 * <pre>
	 * insertしか考慮していない。
	 * 対応する既存消込データを判定するロジックがないため、updateができない。
	 * バッチ処理U08B005を二回呼ぶとバグる。
	 * 業務上問題はないだろうが。。。
	 * これを解消するには消込の同値条件をID以外にする必要がある。
	 * 
	 * 入金消込を構築して、同サービス.保存(営業日,入金消込)を呼ぶ。
	 * - 営業日=請求担当部署の営業日
	 * - 入金消込
	 *   - 入金=引数.マッチング済FB振込:-入金FB振込:-入金.ID
	 *   - 売掛=引数.マッチング済FB振込:-振分:-請求-:請求明細:-売掛
	 *   - 売掛金残高=売掛仕様サービス#残高取得
	 *   - 消込日=請求担当部署の営業日
	 * </pre>
	 * 
	 * @param matchedFBFurikomi
	 *            マッチング済FB振込
	 */
	public void save(MatchedFBFurikomi matchedFBFurikomi);

	/**
	 * 入金消込取得.
	 * 
	 * @param nyukinId
	 *            入金ID
	 * @return 入金消込
	 */
	public NyukinKeshikomi getDomain(String nyukinId);

	/**
	 * 入金消込キャンセル.
	 * 
	 * @param nyukin
	 *            入金
	 * @param target
	 *            キャンセル対象となる消込ID
	 */
	public void cancel(Nyukin nyukin, Set<String> target);
}
