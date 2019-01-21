package com.showka.domain.u11;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.showka.domain.DomainRoot;
import com.showka.domain.z00.Busho;
import com.showka.domain.z00.Shohin;
import com.showka.kubun.ShohinIdoKubun;
import com.showka.system.exception.SystemException;
import com.showka.value.EigyoDate;
import com.showka.value.TheTimestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ShohinIdo extends DomainRoot implements Comparable<ShohinIdo> {

	// private member
	/** 部署. */
	private Busho busho;

	/** 商品移動日(営業日が割り当てられる). */
	private EigyoDate date;

	/** 商品移動区分. */
	private ShohinIdoKubun kubun;

	/** 商品移動タイムスタンプ(営業日とは関係のないシステム日付). */
	private TheTimestamp timestamp;

	/** 商品移動明細. */
	private List<ShohinIdoMeisai> meisai;

	/**
	 * 移動対象商品セット取得.
	 * 
	 * @return 移動対象商品セット
	 */
	public Set<Shohin> getShohinSet() {
		return meisai.stream().map(m -> {
			return m.getShohinDomain();
		}).collect(Collectors.toSet());
	}

	/**
	 * 対象商品の部署在庫における増加数を取得する。
	 * 
	 * @param target
	 *            対象商品
	 * @return 増加数（在庫が減る場合はマイナスになる）
	 */
	public int getNumberForBushoZaiko(Shohin target) {
		int sign = this.kubun.increase() ? 1 : -1;
		return this.meisai.stream().filter(m -> {
			return m.getShohinDomain().equals(target);
		}).mapToInt(m -> {
			return m.getNumber() * sign;
		}).sum();
	}

	// public method
	@Override
	public void validate() throws SystemException {
		// nothing to do
	}

	@Override
	protected boolean equals(DomainRoot other) {
		ShohinIdo o = (ShohinIdo) other;
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
	public int compareTo(ShohinIdo o) {
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
