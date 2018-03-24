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
 * 請求.
 * 
 */
@Entity
@Table(name = "t_seikyu")
@Getter
@Setter
public class TSeikyu extends EntityBase implements Serializable {

	/** SID. */
	private static final long serialVersionUID = -879647709140226732L;

	@EmbeddedId
	private TSeikyuPK pk;

	/** 顧客. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "kokyaku_id", referencedColumnName = "record_id", insertable = false, updatable = false)
	private MKokyaku kokyaku;
}