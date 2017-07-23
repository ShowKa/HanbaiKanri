package com.showka.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.showka.common.AbstractEntity;

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
public class MKokyaku extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "code", unique = true, nullable = false, length = 4)
	private String code;

	@Column(name = "name", nullable = false, length = 255)
	private String name;

	@Column(name = "address", nullable = false, length = 255)
	private String address;

	@Column(name = "kokyaku_kubun", nullable = false, length = 2)
	private String kokyakuKubun;

	@Column(name = "hanbai_kubun", nullable = false)
	private String hanbaiKubun;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "shukan_busho_id", referencedColumnName = "record_id")
	private MBusho shukanBusho;

}