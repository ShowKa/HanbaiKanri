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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("---- ↓ 振込マッチング結果 ↓ ---");
		sp(sb);
		sb.append("マッチ（振込ID=振分ID）件数: ");
		sb.append(matched.size());
		sp(sb);
		matched.entrySet().parallelStream().forEach(e -> {
			sb.append(e);
			sp(sb);
		});
		sb.append("----");
		sp(sb);
		sb.append("複数マッチング件数: ");
		sb.append(multipleMathed.size());
		sp(sb);
		multipleMathed.parallelStream().forEach(m -> {
			sb.append(m);
			sp(sb);
		});
		sb.append("----");
		sp(sb);
		sb.append("同一振込件数: ");
		sb.append(repetition.size());
		sp(sb);
		repetition.parallelStream().forEach(m -> {
			sb.append(m);
			sp(sb);
		});
		sb.append("---- ↑ 振込マッチング結果 ↑ ---");
		return sb.toString();
	}

	/**
	 * 改行する.
	 * 
	 * @param sb
	 */
	private void sp(StringBuilder sb) {
		sb.append(System.lineSeparator());
	}
}
