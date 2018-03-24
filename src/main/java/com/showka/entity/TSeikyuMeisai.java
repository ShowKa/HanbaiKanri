package com.showka.entity;

import java.io.Serializable;

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
 * 請求明細.
 * 
 */
@Entity
@Table(name = "t_seikyu_meisai")
@Getter
@Setter
public class TSeikyuMeisai extends EntityBase implements Serializable {

	/** SID. */
	private static final long serialVersionUID = -8868738454776359553L;

	// columns
	/** 主キー. */
	@EmbeddedId
	private TSeikyuMeisaiPK pk;

	/** 請求金額(=請求時の売掛残高). */
	@Column(name = "kingaku", nullable = false)
	private Integer kingaku;

	// join
	/** 請求. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "seikyu_id", referencedColumnName = "record_id", insertable = false, updatable = false)
	private TSeikyu seikyu;

	/** 売掛. */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "urikake_id", referencedColumnName = "record_id", insertable = false, updatable = false)
	private TUrikake urikake;
}