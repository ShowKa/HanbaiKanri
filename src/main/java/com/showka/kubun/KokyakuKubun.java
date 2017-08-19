package com.showka.kubun;

import com.showka.kubun.i.Kubun;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum KokyakuKubun implements Kubun<KokyakuKubun> {
	法人("01"), 個人("02"), EMPTY("");
	private String code;
}
