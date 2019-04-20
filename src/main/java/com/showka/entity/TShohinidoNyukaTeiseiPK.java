package com.showka.entity;

import java.io.Serializable;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TShohinidoNyukaTeiseiPK implements Serializable {

	/** SID. */
	private static final long serialVersionUID = 2448098252741603737L;

	/** 商品移動ID */
	@Column(name = "shohin_ido_id", nullable = false, length = 255)
	private String shohinIdoId;

	/** 入荷ID（訂正対象） */
	@Column(name = "nyuka_id", nullable = false, length = 255)
	private String nyukaId;
}
