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

@Entity
@Table(name = "t_nyukin_keijo")
@Getter
public class TNyukinKeijo extends EntityBase implements Serializable {

	/** SID. */
	private static final long serialVersionUID = 4052262891383408252L;

	/** 入金ID. */
	@Id
	@Column(name = "nyukin_id", nullable = false, length = 255)
	@Setter
	private String nyukinId;

	/** 計上部署ID. */
	@Column(name = "busho_id", nullable = false, length = 255)
	@Setter
	private String bushoId;

	/** 計上日. */
	@Column(name = "date", nullable = false)
	@Setter
	private Date date;

	// fetch
	/** 入金. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nyukin_id", referencedColumnName = "record_id", insertable = false, updatable = false)
	private TNyukin nyukin;

	/** 計上部署. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "busho_id", referencedColumnName = "record_id", insertable = false, updatable = false)
	private MBusho Busho;
}
