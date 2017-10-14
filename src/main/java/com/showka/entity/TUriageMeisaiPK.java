package com.showka.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Embeddable
public class TUriageMeisaiPK implements Serializable {

	/** SID. */
	private static final long serialVersionUID = 3788555260979602774L;

	/** 売上ID */
	@Column(name = "uriage_id", nullable = false, length = 255)
	private String uriageId;

	/** 明細番号 */
	@Column(name = "meisai_number", nullable = false)
	private Integer meisaiNumber;

}
