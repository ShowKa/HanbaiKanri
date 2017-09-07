package com.showka.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * 部署マスタ
 *
 * @author Administrator
 *
 */
@Entity
@Table(name = "m_busho")
@Getter
@Setter
public class MBusho extends EntityBase implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 部署コード */
	@Id
	@Column(name = "code", nullable = false, length = 4)
	private String code;

	/** 部署区分 */
	@Column(name = "busho_kubun", nullable = false, length = 2)
	private String bushoKubun;

	/** 事業区分 */
	@Column(name = "jigyo_kubun", nullable = false, length = 2)
	private String jigyoKubun;

	/** 部署名 */
	@Column(name = "name", nullable = false, length = 255)
	private String name;

}