package com.showka.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * 請求.
 * 
 */
@Entity
@Table(name = "t_seikyu")
@Getter
@Setter
public class TSeikyu extends EntityBase implements Serializable {

	/** SID. */
	private static final long serialVersionUID = -879647709140226732L;

	// columns
	@EmbeddedId
	private TSeikyuPK pk;

	/** 主幹部署ID */
	@Column(name = "tanto_busho_id", nullable = false, length = 255)
	private String tantoBushoId;

	/** 入金方法区分 */
	@Column(name = "nyukin_hoho_kubun", nullable = false, length = 2)
	private String nyukinHohoKubun;

	/** 支払期日. */
	@Column(name = "shiharai_date", unique = false, nullable = false)
	private Date shiharaiDate;

	// join
	/** 顧客. */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "kokyaku_id", referencedColumnName = "record_id", insertable = false, updatable = false)
	private MKokyaku kokyaku;

	/** 主幹部署 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tanto_busho_id", referencedColumnName = "record_id", insertable = false, updatable = false)
	private MBusho tantoBusho;

	/** 請求明細. */
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "seikyu")
	private List<TSeikyuMeisai> meisai;

	// public methods
	/**
	 * 顧客ID取得.
	 * 
	 * @return 顧客ID
	 */
	public String getKokyakuId() {
		return pk.getKokyakuId();
	}

	/**
	 * 請求日取得.
	 * 
	 * @return 請求日
	 */
	public Date getSeikyuDate() {
		return pk.getSeikyuDate();
	}
}