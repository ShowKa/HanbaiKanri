package com.showka.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * 入荷先マスタ
 */
@Entity
@Table(name = "m_nyuka_saki")
@Getter
@Setter
public class MNyukaSaki extends EntityBase implements Serializable {

	/** SID. */
	private static final long serialVersionUID = 5065956526727505705L;

	/** コード */
	@Id
	@Column(name = "code", nullable = false, length = 4)
	private String code;

	/** 名称 */
	@Column(name = "name", nullable = false, length = 255)
	private String name;

	/** 住所 */
	@Column(name = "address", nullable = false, length = 255)
	private String address;
}