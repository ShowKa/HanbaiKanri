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
 * 顧客マスタ
 *
 */
@Entity
@Table(name = "m_kokyaku")
@Getter
@Setter
public class MKokyaku extends EntityBase implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 顧客コード */
	@Id
	@Column(name = "code", unique = true, nullable = false, length = 4)
	private String code;

	/** 顧客名 */
	@Column(name = "name", nullable = false, length = 255)
	private String name;

	/** 顧客住所 */
	@Column(name = "address", nullable = false, length = 255)
	private String address;

	/** 顧客区分 */
	@Column(name = "kokyaku_kubun", nullable = false, length = 2)
	private String kokyakuKubun;

	/** 販売区分 */
	@Column(name = "hanbai_kubun", nullable = false)
	private String hanbaiKubun;

	/** 主幹部署ID */
	@Column(name = "shukan_busho_id", nullable = false, length = 255)
	private String shukanBushoId;

	/** 主幹部署 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shukan_busho_id", referencedColumnName = "record_id", insertable = false, updatable = false)
	private MBusho shukanBusho;

}