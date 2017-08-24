package com.showka.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * 売上
 * 
 */
@Entity
@Table(name = "t_uriage")
@Getter
@Setter
public class TUriage extends EntityBase implements Serializable {

	/** SID. */
	private static final long serialVersionUID = -4916036385106678566L;

	@EmbeddedId
	private TUriagePK pk;

	/** 売上日 */
	@Column(name = "uriage_date", unique = false, nullable = false)
	private Date uriageDate;

	/** 販売区分 */
	@Column(name = "hanbai_kubun", unique = false, nullable = false, length = 2)
	private String hanbaiKubun;

	/** 消費税率. */
	@Column(name = "shohizeiritsu", unique = false, nullable = false)
	private Double shohizeiritsu;

}