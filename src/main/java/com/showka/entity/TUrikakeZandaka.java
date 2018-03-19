package com.showka.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * 売掛の残高.
 * 
 */
@Entity
@Table(name = "t_urikake_zandaka")
@Getter
@Setter
public class TUrikakeZandaka extends EntityBase implements Serializable {

	/** SID. */
	private static final long serialVersionUID = 4543917306406404757L;

	/** 売上ID */
	@Column(name = "uriage_id", nullable = false, length = 255)
	private String uriageId;

	/** 残高. */
	@Column(name = "zandaka", nullable = false)
	private Integer zandaka;

	/** 売上. */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "uriage_id", referencedColumnName = "record_id", insertable = false, updatable = false)
	private TUriage uriage;
}