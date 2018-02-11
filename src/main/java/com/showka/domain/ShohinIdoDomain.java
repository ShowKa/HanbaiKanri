package com.showka.domain;

import java.util.List;

import com.showka.kubun.ShohinIdoKubun;
import com.showka.system.exception.SystemException;
import com.showka.value.TheDate;
import com.showka.value.TheTimestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ShohinIdoDomain extends DomainBase implements Comparable<ShohinIdoDomain> {

	// private member
	/** 部署. */
	private BushoDomain busho;

	/** 商品移動日(営業日が割り当てられる). */
	private TheDate date;

	/** 商品移動区分. */
	private ShohinIdoKubun kubun;

	/** 商品移動タイムスタンプ(営業日とは関係のないシステム日付). */
	private TheTimestamp timestamp;

	/** 商品移動明細. */
	private List<ShohinIdoMeisaiDomain> meisai;

	// public method
	@Override
	public void validate() throws SystemException {
		// nothing to do
	}

	@Override
	protected boolean equals(DomainBase other) {
		ShohinIdoDomain o = (ShohinIdoDomain) other;
		return this.getRecordId().equals(o.getRecordId());
	}

	@Override
	public int hashCode() {
		return generateHashCode(this.getRecordId());
	}

	/**
	 * 下記の順に比較.
	 * 
	 * <pre>
	 * 部署コード -> 商品移動日 -> 商品移動タイムスタンプ
	 * 
	 * <pre>
	 */
	@Override
	public int compareTo(ShohinIdoDomain o) {
		int r1 = this.busho.getCode().compareTo(o.busho.getCode());
		if (r1 != 0) {
			return r1;
		}
		int r2 = this.date.compareTo(o.date);
		if (r2 != 0) {
			return r2;
		}
		return timestamp.compareTo(o.timestamp);
	}
}
