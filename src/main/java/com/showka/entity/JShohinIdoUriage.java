package com.showka.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * 商品移動売上.
 */
@Entity
@Table(name = "j_shohin_ido_uriage")
@Getter
@Setter
public class JShohinIdoUriage extends EntityBase implements Serializable {

	/**
	 * SID.
	 */
	private static final long serialVersionUID = 1500950639239669609L;

	/** 商品移動ID */
	@Id
	@Column(name = "shohin_ido_id", nullable = false, length = 255)
	private String shohinIdoId;

	/** 売上ID */
	@Column(name = "uriage_id", nullable = false, length = 255)
	private String uriageId;

	/** 商品移動. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shohin_ido_id", referencedColumnName = "record_id", insertable = false, updatable = false)
	@Setter(value = AccessLevel.NONE)
	private TShohinIdo shohinIdo;

	/** 売上. */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "uriage_id", referencedColumnName = "record_id", insertable = false, updatable = false)
	@Setter(value = AccessLevel.NONE)
	private TUriage uriage;
}
