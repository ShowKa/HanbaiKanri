package com.showka.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FBFurikomiMatched {

	/** 振込ID */
	String furikomiId;

	/** 振分ID */
	String furiwakeId;
}
