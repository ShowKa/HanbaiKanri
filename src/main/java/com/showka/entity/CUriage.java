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
 * 取消売上（計上済みだが、計上を取り消した売上）
 * 
 */
@Entity
@Table(name = "c_uriage")
@Getter
@Setter
public class CUriage extends EntityBase implements Serializable {

	/** SID. */
	private static final long serialVersionUID = 1l;

	/** 売上ID */
	@Id
	@Column(name = "uriage_id", nullable = false, length = 255)
	private String uriageId;

	/** 売上. */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "uriage_id", referencedColumnName = "record_id", insertable = false, updatable = false)
	private TUriage uriage;

}