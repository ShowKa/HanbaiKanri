package com.showka.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * FB振込
 *
 */
@Entity
@Table(name = "t_firm_bank_furikomi")
@Getter
@Setter
public class TFirmBankFurikomi extends EntityBase implements Serializable {

	/** SID. */
	private static final long serialVersionUID = 2924962776195910252L;

	/** 主キー. */
	@EmbeddedId
	private TFirmBankFurikomiPK pk;

	/** 銀行コード. */
	@Column(name = "bank_code", unique = false, nullable = false, length = 4)
	private String bankCode;

	/** 口座番号. */
	@Column(name = "account_number", unique = false, nullable = false, length = 8)
	private String accountNumber;

	/** 勘定日付(決済日付). */
	@Column(name = "settlement_date", unique = false, nullable = false)
	private Date settlementDate;

	/** 振込金額. */
	@Column(name = "kingaku", unique = false, nullable = false)
	private Integer kingaku;

	/** 振込依頼人名. */
	@Column(name = "furikomi_irainin_name", unique = false, nullable = false, length = 255)
	private String furikomiIraininName;

	// getter
	/**
	 * 伝送日付取得.
	 * 
	 * @return 伝送日付
	 */
	public Date getTransmissionDate() {
		return pk.getTransmissionDate();
	}

	/**
	 * 伝送番号取得.
	 * 
	 * @return 伝送番号
	 */
	public Integer transmissionNumber() {
		return pk.getTransmissionNumber();
	}
}
