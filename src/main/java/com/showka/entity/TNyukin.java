package com.showka.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * 入金.
 */
@Entity
@Table(name = "t_nyukin")
@Getter
@Setter
public class TNyukin extends EntityUsingRecordIdAsId implements Serializable {

	/** SID. */
	private static final long serialVersionUID = -2586542706756995414L;

	// columns
	/** 顧客ID */
	@Column(name = "kokyaku_id", nullable = false, length = 255)
	private String kokyakuId;

	/** 部署ID */
	@Column(name = "busho_id", nullable = false, length = 255)
	private String bushoId;

	/** 入金日. */
	@Column(name = "date", unique = false, nullable = false)
	private Date date;

	/** 入金区分. */
	@Column(name = "kubun", unique = false, nullable = false, length = 2)
	private String kubun;

	/** 入金額. */
	@Column(name = "amount", unique = false, nullable = false)
	private Integer amount;

	// fetch
	/** 顧客. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "kokyaku_id", referencedColumnName = "record_id", insertable = false, updatable = false)
	private MKokyaku kokyaku;

	/** 部署. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "busho_id", referencedColumnName = "record_id", insertable = false, updatable = false)
	private MBusho busho;
}
