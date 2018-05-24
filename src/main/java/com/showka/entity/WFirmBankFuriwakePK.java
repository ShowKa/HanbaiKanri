package com.showka.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * FB振分ワーク（マッチング用）の主キー.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class WFirmBankFuriwakePK implements Serializable {

	/** SID. */
	private static final long serialVersionUID = 8107782774185466570L;

	/** 振込依頼人ID */
	@Column(name = "furikomi_irainin_id", unique = false, nullable = false, length = 255)
	private String furikomiIraininId;

	/** 請求日付. */
	@Column(name = "seikyu_date", unique = false, nullable = false)
	private Date seikyuDate;

}
