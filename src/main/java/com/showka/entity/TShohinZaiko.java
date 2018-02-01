package com.showka.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * 商品在庫.
 */
@Entity
@Table(name = "t_sohin_zaiko")
@Getter
@Setter
public class TShohinZaiko extends EntityBase implements Serializable {

	/**
	 * SID.
	 */
	private static final long serialVersionUID = 4531203945128958798L;

	/**
	 * 主キー
	 */
	@EmbeddedId
	private TShohinZaikoPK pk;

	/** 在庫数 */
	@Column(name = "number", nullable = false)
	private Integer number;

}
