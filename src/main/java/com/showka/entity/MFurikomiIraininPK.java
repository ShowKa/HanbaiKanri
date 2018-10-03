package com.showka.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 振込依頼人名マスタの主キー.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class MFurikomiIraininPK implements Serializable {

	/** SID. */
	private static final long serialVersionUID = 786593766539137184L;

	/** 顧客ID. */
	@Column(name = "kokyaku_id", unique = false, nullable = false, length = 255)
	private String kokyakuId;

	/** 振込依頼人名. */
	@Column(name = "furikomi_irainin_name", unique = false, nullable = false, length = 255)
	private String furikomiIraininName;
}
