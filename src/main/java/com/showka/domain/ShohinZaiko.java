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
public class ShohinZaiko extends DomainBase {

	// private member
	/** 部署. */
	private Busho busho;

	/** 営業日. */
	private TheDate date;

	/** 商品. */
	private Shohin shohin;

	/** 繰越在庫数. */
	private Integer kurikoshiNumber;

	/** 商品移動 */
	private List<ShohinIdoOnDate> shohinIdoList;

	// public method
	/**
	 * 現時点の部署の商品在庫数を取得.
	 * 
	 * @return 商品在庫数
	 */
	public Integer getNumber() {
		int idoNumber = shohinIdoList.stream().mapToInt(ido -> {
			return ido.getIncreaseOrDecreaseNumber();
		}).sum();
		return kurikoshiNumber + idoNumber;
	}

	/**
	 * 指定した時刻時点での部署の商品在庫数を取得.
	 * 
	 * @return 商品在庫数
	 */
	public Integer getNumber(TheTimestamp timestamp) {
		int idoNumber = shohinIdoList.stream().filter(ido -> {
			return ido.getTimestamp().compareTo(timestamp) <= 0;
		}).mapToInt(ido -> {
			return ido.getIncreaseOrDecreaseNumber();
		}).sum();
		return kurikoshiNumber + idoNumber;
	}

	/**
	 * 在庫有無判定.
	 * 
	 * @return 在庫が1以上ならtrue
	 */
	public boolean exists() {
		return getNumber() >= 1;
	}

	/**
	 * ゼロ在庫判定.
	 * 
	 * @return ゼロ在庫状態ならtrue
	 */
	public boolean isZero() {
		return getNumber() == 0;
	}

	/**
	 * マイナス在庫判定.
	 * 
	 * @return マイナス在庫状態ならtrue
	 */
	public boolean isNegative() {
		return getNumber() < 0;
	}

	/**
	 * 前日時点の繰越数(前日の終わり時点での在庫数)を返却.
	 * 
	 * @return 繰越数
	 */
	public Integer getKurikoshiNumber() {
		return kurikoshiNumber;
	}

	// override method
	@Override
	public void validate() throws SystemException {
		// nothing to do
	}

	@Override
	protected boolean equals(DomainBase other) {
		ShohinZaiko o = (ShohinZaiko) other;
		return busho.equals(o.busho) && date.equals(o.date) && shohin.equals(o.shohin);
	}

	@Override
	public int hashCode() {
		return generateHashCode(busho, date, shohin);
	}

	@Getter
	public static class ShohinIdoOnDate {

		/** 商品移動タイムスタンプ(営業日とは関係のないシステム日付). */
		private TheTimestamp timestamp;

		/** 商品移動区分. */
		private ShohinIdoKubun kubun;

		/** 移動数. */
		private Integer number;

		/**
		 * コンストラクタ.
		 */
		public ShohinIdoOnDate(ShohinIdo ido, Shohin target) {
			this.timestamp = ido.getTimestamp();
			this.kubun = ido.getKubun();
			this.number = ido.getMeisai().stream().filter(m -> {
				return target.equals(m.getShohinDomain());
			}).mapToInt(m -> {
				return m.getNumber();
			}).sum();
		}

		/**
		 * 部署在庫の増加数を取得
		 * 
		 * <pre>
		 * 部署在庫が増えるなら正の数、減るなら負の数で移動数を返却
		 * </pre>
		 * 
		 * @return 増加数
		 */
		public Integer getIncreaseOrDecreaseNumber() {
			return kubun.increase() ? number : number * -1;
		}
	}
}
