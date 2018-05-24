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
 * 振込依頼人名マスタ.
 */
@Entity
@Table(name = "m_furikomi_irainin")
@Getter
@Setter
public class MFurikomiIrainin extends EntityBase implements Serializable {

	/** SID. */
	private static final long serialVersionUID = 4449885770357024461L;

	// column
	@EmbeddedId
	private MFurikomiIraininPK pk;

	// fetch
	/** 顧客 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "kokyaku_id", referencedColumnName = "record_id", insertable = false, updatable = false)
	private MKokyaku kokyaku;

	// getter
	/**
	 * 顧客ID取得.
	 * 
	 * @return 顧客ID
	 */
	public String getKokyakuId() {
		return pk.getKokyakuId();
	}

	/**
	 * 振込依頼人取得.
	 * 
	 * @return 振込依頼人
	 */
	public String getFurikomiIraininName() {
		return pk.getFurikomiIraininName();
	}
}
