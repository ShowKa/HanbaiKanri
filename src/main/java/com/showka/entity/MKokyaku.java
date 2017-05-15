package com.showka.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.showka.common.AbstractEntity;

/**
 * The persistent class for the m_kokyaku database table.
 * 
 */
@Entity
@Table(name = "m_kokyaku")
public class MKokyaku extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique = true, nullable = false, length = 255)
	private String code;

	@Column(nullable = false, length = 255)
	private String address;

	@Column(name = "gentei_kubun", nullable = false, length = 255)
	private String genteiKubun;

	@Column(name = "kokyaku_kubun", nullable = false, length = 255)
	private String kokyakuKubun;

	@Column(nullable = false, length = 255)
	private String name;

	@Column(name = "shukan_busho_code", nullable = false, length = 255)
	private String shukanBushoCode;

}