package com.showka.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * 売上明細の履歴.
 * 
 */
@Entity
@Table(name = "r_uriage_meisai")
@Getter
@Setter
public class RUriageMeisai extends EntityBase implements Serializable {

	/** SID. */
	private static final long serialVersionUID = -4916036385106678566L;

	/** 主キー */
	@EmbeddedId
	private RUriageMeisaiPK pk;

	/** 商品ID. */
	@Column(name = "shohin_id")
	private String shohinId;

	/** 売上数 */
	@Column(name = "hanbai_number", nullable = false)
	private Integer hanbaiNumber;

	/** 商品販売単価. */
	@Column(name = "hanbai_tanka", nullable = false)
	private Integer hanbaiTanka;

	/** 商品 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shohin_id", referencedColumnName = "record_id", insertable = false, updatable = false)
	@Setter(value = AccessLevel.NONE)
	private MShohin shohin;

	/** 売上. */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "uriage_id", referencedColumnName = "record_id", insertable = false, updatable = false)
	@Setter(value = AccessLevel.NONE)
	private RUriage uriage;
}