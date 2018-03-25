package com.showka.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class TSeikyuPK implements Serializable {

	/** SID. */
	private static final long serialVersionUID = 6617338642820006240L;

	/** 顧客ID */
	@Column(name = "kokyaku_id", nullable = false, length = 255)
	private String kokyakuId;

	/** 請求日 */
	@Column(name = "seikyu_date", unique = false, nullable = false)
	private Date seikyuDate;

	@Override
	public boolean equals(Object pk) {
		if (this == pk) {
			return true;
		}
		if (!(pk instanceof TSeikyuPK)) {
			return false;
		}
		TSeikyuPK p = (TSeikyuPK) pk;
		return kokyakuId.equals(p.kokyakuId) && seikyuDate.equals(p.seikyuDate);
	}
}
