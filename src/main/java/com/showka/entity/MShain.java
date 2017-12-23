package com.showka.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * 社員マスタ
 *
 * @author Administrator
 *
 */
@Entity
@Table(name = "m_shain")
@Getter
@Setter
public class MShain extends EntityBase implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 社員コード */
	@Id
	@Column(name = "code", nullable = false, length = 6)
	private String code;

	/** 社員名 */
	@Column(name = "name", nullable = false, length = 255)
	private String name;

	/** 部署ID */
	@Column(name = "shozoku_busho_id", nullable = false, length = 255)
	private String bushoId;

	/** 部署 */
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "shozoku_busho_id", referencedColumnName = "record_id", insertable = false, updatable = false)
	private MBusho busho;

}