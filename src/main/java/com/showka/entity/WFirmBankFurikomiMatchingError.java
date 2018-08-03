package com.showka.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * FBマッチング
 */
@Entity
@Table(name = "w_firm_bank_furikomi_matching_error")
@Getter
@Setter
public class WFirmBankFurikomiMatchingError extends EntityBase implements Serializable {

	/** SID. */
	private static final long serialVersionUID = -6919697281645907979L;

	// column
	/** FB振込ID. */
	@Id
	@Column(name = "fb_furikomi_id", unique = false, nullable = false, length = 255)
	private String fbFurikomiId;

	/** マッチングエラー原因 */
	@Column(name = "cause", nullable = false, length = 2)
	private String cause;

	// fetch
	/** FB振込. */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fb_furikomi_id", referencedColumnName = "record_id", insertable = false, updatable = false)
	private TFirmBankFurikomi fbFurikomi;

	// getter
	/**
	 * FB振込ID取得
	 * 
	 * @return FB振込ID
	 */
	public String getFbFurikomiId() {
		return this.fbFurikomi.getRecordId();
	}

}
