package com.showka.kubun;

import com.showka.kubun.i.Kubun;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ShohinIdoKubun implements Kubun<ShohinIdoKubun> {
	入荷("00"), 売上("10"), 売上返品("11"), 部署間移動("20"), 廃棄("90"), EMPTY("");
	private String code;
}
