package com.showka.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

/**
 * 商品移動.
 */
@Entity
@Table(name = "t_shohin_ido")
@Getter
@Setter
public class TShohinIdo extends EntityUsingRecordIdAsId implements Serializable {

	/** SID. */
	private static final long serialVersionUID = -3616584102119932640L;

	/** 商品移動ID(レコードIDをオーバーライド). */

	/** 部署ID */
	@Column(name = "busho_id", nullable = false, length = 255)
	private String bushoId;

	/** 商品移動日. */
	@Column(name = "date", unique = false, nullable = false)
	private Date date;

	/** 商品移動区分. */
	@Column(name = "kubun", unique = false, nullable = false, length = 2)
	private String kubun;

	/** 商品移動タイムスタンプ. */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(
			name = "sagyo_timestamp",
				unique = false,
				nullable = false,
				length = 255,
				columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date timestamp;

	/** 部署. */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "busho_id", referencedColumnName = "record_id", insertable = false, updatable = false)
	private MBusho busho;
}
