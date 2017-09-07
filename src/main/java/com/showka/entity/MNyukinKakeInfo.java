package com.showka.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * 入金掛情報マスタ
 *
 */
@Entity
@Table(name = "m_nyukin_kake_info")
@Setter
@Getter
public class MNyukinKakeInfo extends EntityBase implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 顧客ID */
	@Id
	@Column(name = "kokyaku_id", nullable = false, length = 4)
	private String kokyakuId;

	/** 顧客 */
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "kokyaku_id", referencedColumnName = "record_id")
	private MKokyaku kokyaku;

	/** 入金方法区分 */
	@Column(name = "nyukin_hoho_kubun", nullable = false, length = 2)
	private String nyukinHohoKubun;

	/** 入金月区分 */
	@Column(name = "nyukin_tsuki_kubun", nullable = false, length = 2)
	private String nyukinTsukiKubun;

	/** 請求締日 */
	@Column(name = "shimebi", nullable = false)
	private int shimebi;

	/** 入金日 */
	@Column(name = "nyukin_date", nullable = false)
	private int nyukinDate;

}