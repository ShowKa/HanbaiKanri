package com.showka.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.showka.common.EntityBase;

import lombok.Getter;
import lombok.Setter;

/**
 * 売上明細.
 * 
 */
@Entity
@Table(name = "t_uriage_meisai")
@Getter
@Setter
public class TUriageMeisai extends EntityBase implements Serializable {

	/** SID. */
	private static final long serialVersionUID = -4916036385106678566L;

	@EmbeddedId
	private TUriageMeisaiPK pk;

	/** 商品. */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "shohin_id", referencedColumnName = "record_id")
	MShohin shohin;

	/** 売上数 */
	@Column(name = "sales_number", nullable = false)
	private Integer salesNumber;

	/** 商品販売単価. */
	@Column(name = "sales_price", nullable = false)
	private Integer slesPrice;

}