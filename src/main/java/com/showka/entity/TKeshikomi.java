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
 * 消込.
 */
@Entity
@Table(name = "t_keshikomi")
@Getter
@Setter
public class TKeshikomi extends EntityUsingRecordIdAsId implements Serializable {

	/** SID. */
	private static final long serialVersionUID = -7422622850688365730L;

	// columns
	/** 消込日. */
	@Column(name = "date", unique = false, nullable = false)
	private Date date;

	/** タイムスタンプ. */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(
			name = "timestamp",
				unique = false,
				nullable = false,
				length = 255,
				columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date timestamp;

	/** 金額. */
	@Column(name = "kingaku", unique = false, nullable = false)
	private Integer kingaku;

	/** 入金ID. */
	@Column(name = "nyukin_id", nullable = false, length = 255)
	private String nyukinId;

	/** 売掛ID. */
	@Column(name = "urikake_id", nullable = false, length = 255)
	private String urikakeId;

	// fetch
	/** 入金. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nyukin_id", referencedColumnName = "record_id", insertable = false, updatable = false)
	private TNyukin nyukin;

	/** 売掛. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "urikake_id", referencedColumnName = "record_id", insertable = false, updatable = false)
	private TUrikake urikake;
}
