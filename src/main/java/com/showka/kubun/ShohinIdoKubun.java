package com.showka.kubun;

import com.showka.kubun.i.Kubun;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ShohinIdoKubun implements Kubun<ShohinIdoKubun> {
	/** 入荷元 -> 部署在庫. */
	入荷("00"),
	/** 部署在庫 -> 入荷元. */
	入荷返品("00"),
	/** 部署在庫 -> 顧客. */
	売上("10"),
	/** 顧客 -> 部署在庫. */
	売上訂正("11"),
	/** 顧客 -> 部署在庫. */
	売上返品("15"),
	/** 部署在庫 -> 他部署在庫. */
	部署間移動("20"),
	/** 部署在庫 -> . */
	廃棄("90"),
	/** Empty. */
	EMPTY("");
	private String code;
}
