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
 * 売上計上.
 * 
 */
@Entity
@Table(name = "r_uriage_keijo")
@Getter
@Setter
public class RUriageKeijo extends EntityBase implements Serializable {

	/** SID. */
	private static final long serialVersionUID = -5009590018761695025L;

	// column
	/** 売上履歴ID. */
	@Id
	@Column(name = "uriage_rireki_id", nullable = false, length = 255)
	private String uriageRirekiId;

	/** 計上部署ID. */
	@Column(name = "busho_id", nullable = false, length = 255)
	private String bushoId;

	// fetch
	/** 売上履歴. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "uriage_rireki_id", referencedColumnName = "record_id", insertable = false, updatable = false)
	private RUriage uriageRireki;

	/** 計上部署. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "busho_id", referencedColumnName = "record_id", insertable = false, updatable = false)
	private MBusho Busho;
}