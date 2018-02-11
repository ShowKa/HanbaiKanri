package com.showka.entity;

import java.io.Serializable;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 商品移動明細主キー.
 * 
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TShohinIdoMeisaiPK implements Serializable {

	/** SID. */
	private static final long serialVersionUID = -3757863135383390002L;

	/** 商品移動ID */
	@Column(name = "shohin_ido_id", nullable = false, length = 255)
	private String shohinIdoId;

	/** 明細番号 */
	@Column(name = "meisai_number", nullable = false)
	private Integer meisaiNumber;

	@Override
	public boolean equals(Object pk) {
		if (this == pk) {
			return true;
		}
		if (!(pk instanceof TShohinIdoMeisaiPK)) {
			return false;
		}
		TShohinIdoMeisaiPK p = (TShohinIdoMeisaiPK) pk;
		return shohinIdoId.equals(p.getShohinIdoId()) && meisaiNumber.equals(p.getMeisaiNumber());
	}

}
