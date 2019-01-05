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
 * 商品移動_入荷訂正.
 */
@Entity
@Table(name = "t_shohin_ido_nyuka_teisei")
@Getter
@Setter
public class TShohinIdoNyukaTeisei extends EntityBase implements Serializable {

	/** SID. */
	private static final long serialVersionUID = -7136464915357710337L;

	/** 商品移動ID */
	@Id
	@Column(name = "shohin_ido_id", nullable = false, length = 255)
	private String shohinIdoId;

	/** 入荷ID（訂正対象） */
	@Id
	@Column(name = "nyuka_id", nullable = false, length = 255)
	private String nyukaId;

	// fetch
	/** 商品移動. */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "shohin_ido_id", referencedColumnName = "record_id", insertable = false, updatable = false)
	private TShohinIdo shohinIdo;

	/** 入荷. */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "nyuka_id", referencedColumnName = "record_id", insertable = false, updatable = false)
	private TShohinIdoNyuka nyuka;
}
