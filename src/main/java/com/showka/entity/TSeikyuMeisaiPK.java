package com.showka.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class TSeikyuMeisaiPK implements Serializable {

	/** SID. */
	private static final long serialVersionUID = 1280853597253718675L;

	/** 請求ID. */
	@Column(name = "seikyu_id", nullable = false, length = 255)
	private String seikyuId;

	/** 売掛ID. */
	@Column(name = "urikake_id", nullable = false, length = 255)
	private String urikakeId;

	@Override
	public boolean equals(Object pk) {
		if (this == pk) {
			return true;
		}
		if (!(pk instanceof TSeikyuMeisaiPK)) {
			return false;
		}
		TSeikyuMeisaiPK p = (TSeikyuMeisaiPK) pk;
		return seikyuId.equals(p.getSeikyuId()) && urikakeId.equals(p.getUrikakeId());
	}
}
