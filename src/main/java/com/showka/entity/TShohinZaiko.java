package com.showka.entity;

import java.io.Serializable;
import java.util.Date;

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
@Table(name = "t_shohin_zaiko")
@Getter
@Setter
public class TShohinZaiko extends EntityBase implements Serializable {

	/**
	 * SID.
	 */
	private static final long serialVersionUID = 4531203945128958798L;

	// column
	@EmbeddedId
	private TShohinZaikoPK pk;

	/** 在庫数（=繰越数） */
	@Column(name = "number", nullable = false)
	private Integer number;

	// fetch
	/** 商品 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "shohin_id", referencedColumnName = "record_id", insertable = false, updatable = false)
	@Setter(value = AccessLevel.NONE)
	private MShohin shohin;

	/** 部署 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "busho_id", referencedColumnName = "record_id", insertable = false, updatable = false)
	@Setter(value = AccessLevel.NONE)
	private MBusho busho;

	// getter
	public Date getEigyoDate() {
		return this.pk.getEigyoDate();
	}
}
