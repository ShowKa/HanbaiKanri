package com.showka.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 商品在庫主キー.
 * 
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TShohinZaikoPK implements Serializable {

	/** SID. */
	private static final long serialVersionUID = -3912732390812063368L;

	/** 部署ID */
	@Column(name = "busho_id", unique = false, nullable = false, length = 255)
	private String bushoId;

	/** 営業日. */
	@Column(name = "eigyo_date", unique = false, nullable = false, length = 5)
	private Date eigyoDate;

	/** 商品ID */
	@Column(name = "shohin_id", unique = false, nullable = false, length = 255)
	private String shohinId;

	@Override
	public boolean equals(Object pk) {
		if (this == pk) {
			return true;
		}
		if (!(pk instanceof TShohinZaikoPK)) {
			return false;
		}
		TShohinZaikoPK p = (TShohinZaikoPK) pk;
		return bushoId.equals(p.getBushoId()) && shohinId.equals(p.getShohinId()) && eigyoDate.equals(p.getEigyoDate());
	}

}
