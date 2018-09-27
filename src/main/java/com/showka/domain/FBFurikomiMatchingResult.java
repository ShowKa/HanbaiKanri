package com.showka.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class FBFurikomiMatchingResult {

	/** 正常マッチング(振込ID:振分ID) */
	@Getter
	private List<MatchedPair> matchedNormally;

	/** 複数マッチング */
	// FB振込：マッチする請求が2つ以上ある場合エラー
	@Getter
	private List<String> multipleMathed;

	/** 同一振込 */
	// 同一部署内に「同一振込依頼人名」&「同一金額」の振込が存在する場合エラー
	// 1つの請求に対し複数の振込がマッチングされるため。
	@Getter
	private List<String> repetition;

	// constructor
	public FBFurikomiMatchingResult(List<MatchedPair> matched) {
		this.extractAndSetMultipleMathed(matched);
		this.extractAndSetRepetition(matched);
		this.matchedNormally = matched;
	}

	// private method
	// 注意=引数破壊
	private void extractAndSetMultipleMathed(List<MatchedPair> matched) {
		// 複数マッチングの振込を抽出
		List<String> multipledMathced = new ArrayList<>();
		// group key = FB振込#id
		Map<String, List<MatchedPair>> groupedByFB = matched.parallelStream()
				.collect(Collectors.groupingBy(MatchedPair::getFbFurikomiId));
		// 1つのFB振込に対し、振分が複数マッチングしたものだけ抽出
		groupedByFB.entrySet().parallelStream().filter(e -> {
			return e.getValue().size() > 1;
		}).forEach(e -> {
			String id = e.getKey();
			multipledMathced.add(id);
		});
		// 複数マッチングの振込を除去
		matched.removeIf(m -> {
			String id = m.getFbFurikomiId();
			return multipledMathced.contains(id);
		});
		// set
		this.multipleMathed = multipledMathced;
	}

	// 注意=引数破壊
	private void extractAndSetRepetition(List<MatchedPair> matched) {
		// 同一振込を抽出
		List<String> repetition = new ArrayList<>();
		Map<String, List<MatchedPair>> groupedBySeikyu = matched.parallelStream()
				.collect(Collectors.groupingBy(MatchedPair::getSeikyuId));
		// 1つの請求に対してマッチする振込が複数あるもの（=同一振込）だけ抽出
		groupedBySeikyu.entrySet().parallelStream().filter(e -> {
			return e.getValue().size() > 1;
		}).forEach(e -> {
			List<MatchedPair> _result = e.getValue();
			_result.forEach(r -> {
				String id = r.getFbFurikomiId();
				repetition.add(id);
			});
		});
		// 同一振込を除去
		matched.removeIf(m -> {
			String id = m.getFbFurikomiId();
			return repetition.contains(id);
		});
		// set
		this.repetition = repetition;
	}

	// override
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("---- ↓ 振込マッチング結果 ↓ ---");
		sp(sb);
		sb.append("正常マッチ（振込ID=振分ID）件数: ");
		sb.append(matchedNormally.size());
		sp(sb);
		matchedNormally.parallelStream().forEach(m -> {
			sb.append(m.getFbFurikomiId());
			sb.append("=");
			sb.append(m.getFuriwakeId());
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

	@AllArgsConstructor
	@Getter
	public static class MatchedPair {
		private String fbFurikomiId;
		private String furiwakeId;
		private String seikyuId;
	}
}
