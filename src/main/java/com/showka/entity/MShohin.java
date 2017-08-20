package com.showka.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.showka.common.EntityBase;

import lombok.Getter;
import lombok.Setter;

/**
 * 商品マスタ
 * 
 */
@Entity
@Table(name = "m_shohin")
@Getter
// @Proxy(lazy = false)
@Setter
public class MShohin extends EntityBase implements Serializable {

	/** SID. */
	private static final long serialVersionUID = -8602345559515966651L;

	@Id
	@Column(name = "code", unique = true, nullable = false, length = 4)
	private String code;

	@Column(name = "name", nullable = false, length = 255)
	private String name;

	@Column(name = "standard_price", nullable = false)
	private Integer standardPrice;

}
