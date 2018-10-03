package com.showka.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * FBマッチング
 */
@Entity
@Table(name = "w_firm_bank_furikomi_matching")
@Getter
@Setter
public class WFirmBankFurikomiMatching extends EntityBase implements Serializable {

	/** SID. */
	private static final long serialVersionUID = -9019247196157447041L;

	// column
	/** 主キー. */
	@EmbeddedId
	private WFirmBankFurikomiMatchingPK pk;

	// fetch
	/** FB振込. */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fb_furikomi_id", referencedColumnName = "record_id", insertable = false, updatable = false)
	private TFirmBankFurikomi fbFurikomi;

	/** FB振分. */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "furiwake_id", referencedColumnName = "record_id", insertable = false, updatable = false)
	private WFirmBankFuriwake furiwake;

	// getter
	/**
	 * FB振込ID取得
	 * 
	 * @return FB振込ID
	 */
	public String getFbFurikomiId() {
		return this.fbFurikomi.getRecordId();
	}

	/**
	 * 振分ID取得
	 * 
	 * @return 振分ID
	 */
	public String getFuriwakeId() {
		return this.furiwake.getRecordId();
	}
}
