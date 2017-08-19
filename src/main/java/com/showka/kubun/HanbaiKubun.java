package com.showka.kubun;

import com.showka.kubun.i.Kubun;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum HanbaiKubun implements Kubun<HanbaiKubun> {
	現金("00"), 掛売("10"), EMPTY("");
	String code;
}
