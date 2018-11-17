package com.showka.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * 請求済状態の売掛.
 * 
 * <pre>
 * 売掛を請求と紐付ける。
 * ・有効な請求のみレコードとして存在する。
 * ・再請求したら、請求IDを更新
 * ・入金や貸倒で消込されたら、レコードが消える
 * </pre>
 *
 */
@Entity
@Table(name = "s_urikake_seikyu_done")
@Getter
@Setter
public class SUrikakeSeikyuDone extends EntityBase implements Serializable {

	/** SID. */
	private static final long serialVersionUID = 2961157593426511712L;

	// column
	/** 売掛ID. */
	@Id
	@Column(name = "urikake_id", unique = false, nullable = false, length = 255)
	private String urikakeId;

	/** 請求ID. */
	@Column(name = "seikyu_id", unique = false, nullable = false, length = 255)
	private String seikyuId;

	// join
	/** 売掛. */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "urikake_id", referencedColumnName = "record_id", insertable = false, updatable = false)
	private TUrikake urikake;

	/** 請求. */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "seikyu_id", referencedColumnName = "record_id", insertable = false, updatable = false)
	private TSeikyu seikyu;
}
