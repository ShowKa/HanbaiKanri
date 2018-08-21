package com.showka.domain;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FBFurikomiMatchingResult {

	/** 振込ID:振分ID */
	Map<String, String> matched;

	/** 複数マッチング */
	// FB振込：マッチする請求が2つ以上ある場合エラー
	List<String> multipleMathed;

	/** 同一振込 */
	// 同一部署内に「同一振込依頼人名」&「同一金額」の振込が存在する場合エラー
	// 1つの請求に対し複数の振込がマッチングされるため。
	List<String> repetition;
}
