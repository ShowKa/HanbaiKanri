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
 * 入金FB振込関係テーブル.
 */
@Entity
@Table(name = "j_nyukin_fb_furikomi")
@Getter
public class JNyukinFBFurikomi extends EntityBase implements Serializable {

	/** SID. */
	private static final long serialVersionUID = -2371522456934994062L;

	// columns
	/** 入金ID */
	@Id
	@Column(name = "nyukin_id", nullable = false, length = 255)
	@Setter
	private String nyukinId;

	/** FB振込ID */
	@Column(name = "fb_furikomi_id", nullable = false, length = 255)
	@Setter
	private String fbFurikomiId;

	// fetch
	/** 入金. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nyukin_id", referencedColumnName = "record_id", insertable = false, updatable = false)
	private TNyukin nyukin;

	/** FB振込. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fb_furikomi_id", referencedColumnName = "record_id", insertable = false, updatable = false)
	private TFirmBankFurikomi fbFurikomi;

}
