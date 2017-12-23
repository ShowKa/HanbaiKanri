package com.showka.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * 売上伝票の履歴
 * 
 */
@Entity
@Table(name = "t_uriage_rireki")
@Getter
@Setter
public class TUriageRireki extends EntityBase implements Serializable {

	/** SID. */
	private static final long serialVersionUID = 5084030502613634293L;

	@EmbeddedId
	private TUriageRirekiPK pk;

	/** 売上日 */
	@Column(name = "uriage_date", unique = false, nullable = false)
	private Date uriageDate;

	/** 販売区分 */
	@Column(name = "hanbai_kubun", unique = false, nullable = false, length = 2)
	private String hanbaiKubun;

	/** 消費税率. */
	@Column(name = "shohizeiritsu", unique = false, nullable = false)
	private Double shohizeiritsu;

	/** 売上履歴明細. */
	// @OneToMany(fetch = FetchType.EAGER, mappedBy = "uriage")
	// private List<TUriageMeisai> meisai;

	/** 売上. */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "uriage_id", referencedColumnName = "record_id", insertable = false, updatable = false)
	private TUriage uriage;
}