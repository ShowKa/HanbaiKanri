package com.showka.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * 売掛.
 * 
 */
@Entity
@Table(name = "t_urikake")
@Getter
@Setter
public class TUrikake extends EntityBase implements Serializable {

	/** SID. */
	private static final long serialVersionUID = 4543917306406404757L;

	/** 売上ID */
	@Id
	@Column(name = "uriage_id", nullable = false, length = 255)
	private String uriageId;

	/** 金額（掛売りにより発生した売掛金）. */
	@Column(name = "kingaku", nullable = false)
	private Integer kingaku;

	/** 入金予定日. */
	@Column(name = "nyukin_yotei_date", nullable = false)
	private Date nyukinYoteiDate;

	/** 売上. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "uriage_id", referencedColumnName = "record_id", insertable = false, updatable = false)
	private TUriage uriage;

	/** 売掛未請求状態 */
	@OneToOne(fetch = FetchType.EAGER, mappedBy = "urikake", orphanRemoval = true)
	private SUrikakeSeikyuNotYet notYet;
}
