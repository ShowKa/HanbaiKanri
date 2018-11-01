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
 * 未請求の売掛.
 *
 */
@Entity
@Table(name = "t_urikake_miseikyu")
@Getter
@Setter
public class TUrikakeMiseikyu extends EntityBase implements Serializable {

	/** SID. */
	private static final long serialVersionUID = -592877635344460786L;

	// column
	/** 売掛ID. */
	@Id
	@Column(name = "urikake_id", unique = false, nullable = false, length = 255)
	private String urikakeId;

	// join
	/** 売掛. */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "urikake_id", referencedColumnName = "record_id", insertable = false, updatable = false)
	private TUrikake urikake;
}
