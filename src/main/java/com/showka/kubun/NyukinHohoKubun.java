package com.showka.kubun;

import com.showka.kubun.i.Kubun;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum NyukinHohoKubun implements Kubun<NyukinHohoKubun> {
	集金("00"), 振込("10"), 口座振替("20"), EMPTY("");
	String code;
}
