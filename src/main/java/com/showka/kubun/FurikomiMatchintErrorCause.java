package com.showka.kubun;

import com.showka.kubun.i.Kubun;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FurikomiMatchintErrorCause implements Kubun<FurikomiMatchintErrorCause> {
	同一振込("10"), マッチング対象なし("20"), 複数マッチング("21"), EMPTY("");
	private String code;
}
