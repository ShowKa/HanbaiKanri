package com.showka.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class TFirmBankFurikomiPK implements Serializable {

	/** SID. */
	private static final long serialVersionUID = -5146917878053280618L;

	/** 伝送日付. */
	@Column(name = "transmission_date", unique = false, nullable = false)
	private Date transmissionDate;

	/** 伝送番号（日付毎の連番）. */
	@Column(name = "transmission_number", nullable = false)
	private Integer transmissionNumber;
}
