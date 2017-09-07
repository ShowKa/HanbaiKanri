package com.showka.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TUriagePK implements Serializable {

	/** SID. */
	private static final long serialVersionUID = 310613201101911823L;

	/** 顧客 */
	@JoinColumn(name = "kokyaku_id", referencedColumnName = "record_id")
	@ManyToOne(fetch = FetchType.EAGER)
	private MKokyaku kokyaku;

	/** 伝票番号 */
	@Column(name = "denpyo_number", unique = false, nullable = false, length = 5)
	private String denpyoNumber;

}
