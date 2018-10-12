package com.showka.domain.u17;

import java.util.List;
import java.util.stream.Stream;

import com.showka.domain.DomainAggregation;
import com.showka.domain.u08.NyukinKeshikomi;
import com.showka.domain.z00.Busho;
import com.showka.system.exception.SystemException;
import com.showka.value.AmountOfMoney;
import com.showka.value.EigyoDate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class BushoNyukin extends DomainAggregation {

	/** 部署. */
	@Getter
	private Busho busho;

	/** 計上日. */
	@Getter
	private EigyoDate keijoDate;

	/** 計上済みの入金の入金消込のリスト. */
	@Getter
	private List<NyukinKeshikomi> nyukinKeshikomiList;

	/**
	 * 当日計上した現金の入金に関し、計上日時点で消込を実施した分の額を取得する。
	 * 
	 * @return 消込金額（現金）
	 */
	public AmountOfMoney getKeshikomiKingaku_Genkin() {
		// 消込金額算出
		int keshikomiKingaku = this.filterGenkinNyukin().mapToInt(nk -> {
			// 計上日以前の消込の消込金額合計を取得
			return nk.getKeshikomiKingakuGokei(keijoDate).intValue();
		}).sum();
		return new AmountOfMoney(keshikomiKingaku);
	}

	/**
	 * 当日計上した現金の入金に関し、計上日時点で未処理の金額を取得する。
	 * 
	 * <pre>
	 * 未処理=消込及び充当の対象となっていない。
	 * </pre>
	 * 
	 * @return 未処理金額（現金）
	 */
	public AmountOfMoney getMishoriKingaku_Genkin() {
		// 未処理金額算出
		int mishoriKingaku = this.filterGenkinNyukin().mapToInt(nk -> {
			// 計上日以前の消込の未処理金額合計を取得
			return nk.getMikeshikomi(keijoDate).intValue();
		}).sum();
		return new AmountOfMoney(mishoriKingaku);
	}

	/**
	 * 当日計上した振込の入金に関し、計上日時点で消込を実施した分の額を取得する。
	 * 
	 * @return 消込金額（振込）
	 */
	public AmountOfMoney getKeshikomiKingaku_Furikomi() {
		// 消込金額算出
		int keshikomiKingaku = this.filterFurikomiNyukin().mapToInt(nk -> {
			// 計上日以前の消込の消込金額合計を取得
			return nk.getKeshikomiKingakuGokei(keijoDate).intValue();
		}).sum();
		return new AmountOfMoney(keshikomiKingaku);
	}

	/**
	 * 計上した振込の入金に関し、計上日時点で未処理の金額を取得する。
	 * 
	 * <pre>
	 * 未処理=消込及び充当の対象となっていない。
	 * </pre>
	 * 
	 * @return 未処理金額（振込）
	 */
	public AmountOfMoney getMishoriKingaku_Furikomi() {
		// 未処理金額算出
		int mishoriKingaku = this.filterFurikomiNyukin().mapToInt(nk -> {
			// 計上日以前の消込の未処理金額合計を取得
			return nk.getMikeshikomi(keijoDate).intValue();
		}).sum();
		return new AmountOfMoney(mishoriKingaku);
	}

	// private
	/**
	 * 入金方法=現金のみ抽出
	 */
	private Stream<NyukinKeshikomi> filterGenkinNyukin() {
		return nyukinKeshikomiList.parallelStream().filter(nk -> {
			return nk.getNyukin().getNyukinHohoKubun().isGenkin();
		});
	}

	/**
	 * 入金方法=振込のみ抽出
	 */
	private Stream<NyukinKeshikomi> filterFurikomiNyukin() {
		return nyukinKeshikomiList.parallelStream().filter(nk -> {
			return nk.getNyukin().getNyukinHohoKubun().isFurikomi();
		});
	}

	// override
	@Override
	protected boolean equals(DomainAggregation other) {
		BushoNyukin o = (BushoNyukin) other;
		return this.busho.equals(o.busho) && this.keijoDate.equals(o.keijoDate);
	}

	@Override
	public int hashCode() {
		return super.generateHashCode(this.busho, this.keijoDate);
	}

	@Override
	public void validate() throws SystemException {
	}
}
