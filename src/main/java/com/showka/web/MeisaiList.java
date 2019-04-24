package com.showka.web;

import java.util.ArrayList;
import java.util.stream.Stream;

public class MeisaiList<T extends MeisaiFormBase> extends ArrayList<T> {

	/** SID. */
	private static final long serialVersionUID = -3518253282788354145L;

	/**
	 * 非削除明細取得.
	 */
	public Stream<T> filterNotDeleted() {
		return this.stream().filter(m -> !m.isDeleted());
	}

	/**
	 * 削除明細取得.
	 */
	public Stream<T> filterDeleted() {
		return this.stream().filter(m -> m.isDeleted());
	}
}
