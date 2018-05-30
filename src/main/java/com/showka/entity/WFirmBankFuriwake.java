package com.showka.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * FB振分ワーク（マッチング用）
 */
@Entity
@Table(name = "w_firm_bank_furiwake")
@Getter
@Setter
public class WFirmBankFuriwake extends EntityBase implements Serializable {

	/** SID. */
	private static final long serialVersionUID = -737211007749872181L;

	// column
	/** 主キー. */
	@EmbeddedId
	private WFirmBankFuriwakePK pk;

	/** 債権金額（請求金額でなく、現時点での売掛金額の残高の合計）. */
	@Column(name = "saiken_kingaku", unique = false, nullable = false)
	private Integer saikenKingaku;

	// fetch
	/** 振込依頼人. */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "furikomi_irainin_id", referencedColumnName = "record_id", insertable = false, updatable = false)
	private MFurikomiIrainin furikomiIrainin;

	/** 請求. */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "seikyu_id", referencedColumnName = "record_id", insertable = false, updatable = false)
	private TSeikyu seikyu;

	// getter
	/**
	 * 振込依頼人ID取得.
	 * 
	 * @return 振込依頼人ID
	 */
	public String getFurikomiIraininId() {
		return pk.getFurikomiIraininId();
	}

	/**
	 * 請求日取得.
	 * 
	 * @return 請求日
	 */
	public Date getSeikyuId() {
		return pk.getSeikyuId();
	}
}
