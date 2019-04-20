package com.showka.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

	/** PK */
	@EmbeddedId
	private TShohinidoNyukaTeiseiPK pk;

	// fetch
	/** 商品移動. */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "shohin_ido_id", referencedColumnName = "record_id", insertable = false, updatable = false)
	private TShohinIdo shohinIdo;

	/** 入荷. */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "nyuka_id", referencedColumnName = "record_id", insertable = false, updatable = false)
	private TShohinIdoNyuka nyuka;

	// getter
	public String getShohinIdoId() {
		return this.pk.getShohinIdoId();
	}

	public String getNyukaIdoId() {
		return this.pk.getNyukaId();
	}
}
