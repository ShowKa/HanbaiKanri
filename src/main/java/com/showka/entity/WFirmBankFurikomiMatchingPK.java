package com.showka.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * FBマッチング主キー.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class WFirmBankFurikomiMatchingPK implements Serializable {

	/** SID. */
	private static final long serialVersionUID = 8107782774185466570L;

	/** FB振込ID. */
	@Column(name = "fb_furikomi_id", unique = false, nullable = false, length = 255)
	private String fbFurikomiId;

	/** FB振分ID */
	@Column(name = "furiwake_id", unique = false, nullable = false, length = 255)
	private String furiwakeId;
}
