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
public class RUriagePK implements Serializable {

	/** SID. */
	private static final long serialVersionUID = 3436525359521983260L;

	/** 売上ID */
	@Column(name = "uriage_id", nullable = false, length = 255)
	private String uriageId;

	/** 計上日 */
	@Column(name = "keijo_date", unique = false, nullable = false)
	private Date keijoDate;

	@Override
	public boolean equals(Object pk) {
		if (this == pk) {
			return true;
		}
		if (!(pk instanceof RUriagePK)) {
			return false;
		}
		RUriagePK p = (RUriagePK) pk;
		return uriageId.equals(p.uriageId) && keijoDate.equals(p.keijoDate);
	}

}
