package com.showka.kubun;

import com.showka.kubun.i.Kubun;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ShohinIdoKubun implements Kubun<ShohinIdoKubun> {
	/** 入荷元 -> 部署在庫（正の数のみ）. */
	入荷("00"),
	/** 入力誤りによる訂正（正負あり）. */
	入荷訂正("01"),
	/** 部署在庫 -> 入荷元. */
	入荷返品("05"),
	/** 部署在庫 -> 顧客. */
	売上("10"),
	/** 顧客 -> 部署在庫. */
	売上訂正("11"),
	/** 顧客 -> 部署在庫. */
	売上返品("15"),
	/** 部署在庫 -> 他部署在庫. */
	部署間移動受入("20"), 部署間移動提供("21"),
	/** 部署在庫 -> . */
	廃棄("90"),
	/** Empty. */
	EMPTY("");
	private String code;

	/**
	 * 部署在庫増加判定.
	 * 
	 * @return 移動数が正の数で部署の在庫が増える場合、true
	 */
	public boolean increase() {
		switch (this) {
		case 入荷:
		case 入荷訂正:
		case 売上訂正:
		case 売上返品:
		case 部署間移動受入:
			return true;
		default:
			return false;
		}
	}
}
