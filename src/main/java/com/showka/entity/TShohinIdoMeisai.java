package com.showka.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * 商品在庫.
 */
@Entity
@Table(name = "t_shohin_ido_meisai")
@Getter
@Setter
public class TShohinIdoMeisai extends EntityBase implements Serializable {

	/** SID. */
	private static final long serialVersionUID = -4072825350276979644L;

	/** 主キー */
	@EmbeddedId
	private TShohinIdoMeisaiPK pk;

	/** 商品ID. */
	@Column(name = "shohin_id")
	private String shohinId;

	/** 移動数 */
	@Column(name = "number", nullable = false)
	private Integer number;

	/** 商品 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shohin_id", referencedColumnName = "record_id", insertable = false, updatable = false)
	@Setter(value = AccessLevel.NONE)
	private MShohin shohin;

	/** 商品移動. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shohin_ido_id", referencedColumnName = "record_id", insertable = false, updatable = false)
	private TShohinIdo shohinIdo;
}
