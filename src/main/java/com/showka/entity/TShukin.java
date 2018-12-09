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
 * 集金
 */
@Entity
@Table(name = "t_shukin")
@Getter
public class TShukin extends EntityBase implements Serializable {

	/** SID. */
	private static final long serialVersionUID = 7131330216589464178L;

	/** 入金ID */
	@Id
	@Column(name = "nyukin_id", nullable = false, length = 255)
	@Setter
	private String nyukinId;

	/** 集金.担当社員ID */
	@Column(name = "tanto_shain_id", nullable = false, length = 255)
	@Setter
	private String tantoShainId;

	/** 伝票番号 */
	@Column(name = "denpyo_number", unique = false, nullable = false, length = 5)
	@Setter
	private String denpyoNumber;

	// fetch
	/** 入金. */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "nyukin_id", referencedColumnName = "record_id", insertable = false, updatable = false)
	@Setter
	private TNyukin nyukin;

	/** 集金.担当社員. */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tanto_shain_id", referencedColumnName = "record_id", insertable = false, updatable = false)
	private MShain tantoShain;
}
