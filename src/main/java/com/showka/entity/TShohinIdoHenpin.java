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
 * 商品移動_返品.
 */
@Entity
@Table(name = "t_shohin_ido_henpin")
@Getter
@Setter
public class TShohinIdoHenpin extends EntityBase implements Serializable {

	/** SID. */
	private static final long serialVersionUID = 8446425405929158187L;

	/** 商品移動ID */
	@Id
	@Column(name = "shohin_ido_id", nullable = false, length = 255)
	private String shohinIdoId;

	/** 返品先ID(=入荷先) */
	@Id
	@Column(name = "henpin_saki_id", nullable = false, length = 255)
	private String henpinSakiId;

	// fetch
	/** 商品移動. */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "shohin_ido_id", referencedColumnName = "record_id", insertable = false, updatable = false)
	private TShohinIdo shohinIdo;

	/** 返品先(=入荷先). */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "henpin_saki_id", referencedColumnName = "record_id", insertable = false, updatable = false)
	private MNyukaSaki henpinSaki;
}
