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
public class TUriageMeisaiPK implements Serializable {

	/** SID. */
	private static final long serialVersionUID = 3788555260979602774L;

	/** 売上ID */
	@Column(name = "uriage_id", nullable = false, length = 255)
	private String uriageId;

	/** 明細番号 */
	@Column(name = "meisai_number", nullable = false)
	private Integer meisaiNumber;

	@Override
	public boolean equals(Object pk) {
		if (this == pk) {
			return true;
		}
		if (!(pk instanceof TUriageMeisaiPK)) {
			return false;
		}
		TUriageMeisaiPK p = (TUriageMeisaiPK) pk;
		return uriageId.equals(p.getUriageId()) && meisaiNumber.equals(p.getMeisaiNumber());
	}

}
