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
 * 売上計上訂正.
 * 
 */
@Entity
@Table(name = "r_uriage_keijo_teisei")
@Getter
@Setter
public class RUriageKeijoTeisei extends EntityBase implements Serializable {

	/** SID. */
	private static final long serialVersionUID = 3229187890198139756L;

	// column
	/** 売上計上ID. */
	@Id
	@Column(name = "uriage_keijo_id", nullable = false, length = 255)
	private String uriageKeijoId;

	/** 売上履歴ID（訂正対象）. */
	@Column(name = "uriage_rireki_id", nullable = false, length = 255)
	private String uriageRirekiId;

	// fetch
	/** 売上計上. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "uriage_keijo_id", referencedColumnName = "record_id", insertable = false, updatable = false)
	private RUriageKeijo uriageKeijo;

	/** 売上履歴（訂正対象伝票）. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "uriage_rireki_id", referencedColumnName = "record_id", insertable = false, updatable = false)
	private RUriage teiseiUriageRireki;
}