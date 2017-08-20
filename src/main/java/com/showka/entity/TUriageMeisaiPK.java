package com.showka.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TUriageMeisaiPK implements Serializable {

	/** SID. */
	private static final long serialVersionUID = 3788555260979602774L;

	/** 売上ID */
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "uriage_id", referencedColumnName = "record_id")
	private TUriage uriage;

	/** 明細番号 */
	@Column(name = "meisai_number", nullable = false)
	private Integer meisaiNumber;

}
