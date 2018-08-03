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
 * 部署銀行口座.
 */
@Entity
@Table(name = "m_busho_bank_account")
@Getter
@Setter
public class MBushoBankAccount extends EntityBase implements Serializable {

	/** SID. */
	private static final long serialVersionUID = -8032873071666531864L;

	// column
	/** 部署ID. */
	@Id
	@Column(name = "busho_id", unique = false, nullable = false, length = 255)
	private String bushoId;

	/** 銀行コード. */
	@Column(name = "bank_code", unique = false, nullable = false, length = 4)
	private String bankCode;

	/** 口座番号. */
	@Column(name = "account_number", unique = false, nullable = false, length = 8)
	private String accountNumber;

	// fetch
	/** 部署. */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "busho_id", referencedColumnName = "record_id", insertable = false, updatable = false)
	private MBusho busho;

	// getter
	/**
	 * 部署ID取得
	 * 
	 * @return 部署ID
	 */
	public String getBushoId() {
		return this.busho.getRecordId();
	}

}
