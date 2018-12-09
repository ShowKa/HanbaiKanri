package com.showka.entity;

import java.io.Serializable;
import java.util.Date;

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
 * 部署日付マスタ
 *
 * @author Administrator
 *
 */
@Entity
@Table(name = "m_busho_date")
@Getter
@Setter
public class MBushoDate extends EntityBase implements Serializable {

	/**
	 * SID.
	 */
	private static final long serialVersionUID = -7674341128234836220L;

	/** 部署ID */
	@Id
	@Column(name = "busho_id", nullable = false, length = 255)
	private String bushoId;

	/** 営業日 */
	@Column(name = "eigyo_date", nullable = false)
	private Date eigyoDate;

	/** 部署 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "busho_id", referencedColumnName = "record_id", insertable = false, updatable = false)
	private MBusho busho;

}