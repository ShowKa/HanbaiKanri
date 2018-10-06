package com.showka.system.exception;

import java.util.List;

import com.showka.domain.z00.Shohin;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * マイナス在庫例外.
 * 
 */
@Getter
public class MinusZaikoException extends UnsatisfiedSpecificationException {

	/**
	 * SID.
	 */
	private static final long serialVersionUID = 7616809728892353050L;

	/**
	 * マイナス在庫リスト.
	 */
	private List<MinusZaiko> minusZaikoList;

	/**
	 * Constructor.
	 * 
	 * @param minusZaikoList
	 *            マイナス在庫リスト.
	 */
	public MinusZaikoException(List<MinusZaiko> minusZaikoList) {
		super("");
		this.minusZaikoList = minusZaikoList;
		StringBuilder m = new StringBuilder();
		m.append("在庫数が不足しています。");
		String lineCd = System.getProperty("line.separator");
		m.append(lineCd);
		minusZaikoList.forEach(mz -> {
			Shohin shohin = mz.getShohin();
			Integer number = mz.getPresentNumber();
			Integer rest = mz.getAfterNumber();
			m.append("商品 : " + shohin.getName() + ", ");
			m.append("在庫数 : " + number + " -> " + rest);
			m.append(lineCd);
		});
		super.message = m.toString();
	}

	/**
	 * マイナス在庫.
	 * 
	 */
	@AllArgsConstructor
	@Getter
	public static class MinusZaiko {
		private Shohin shohin;
		private Integer presentNumber;
		private Integer afterNumber;
	}

}
