package com.showka.entity;

import java.io.Serializable;

import javax.persistence.Column;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TUriagePK implements Serializable {

	/** SID. */
	private static final long serialVersionUID = 310613201101911823L;

	/** 顧客ID */
	@Column(name = "kokyaku_id", unique = false, nullable = false, length = 255)
	private String kokyakuId;

	/** 伝票番号 */
	@Column(name = "denpyo_number", unique = false, nullable = false, length = 5)
	private String denpyoNumber;

	@Override
	public boolean equals(Object pk) {
		if (this == pk) {
			return true;
		}
		if (!(pk instanceof TUriagePK)) {
			return false;
		}
		TUriagePK p = (TUriagePK) pk;
		return kokyakuId.equals(p.getKokyakuId()) && denpyoNumber.equals(p.getDenpyoNumber());
	}

}
