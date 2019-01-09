package com.showka.domain.u11;

import static java.util.stream.Collectors.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.showka.domain.DomainRoot;
import com.showka.domain.z00.Busho;
import com.showka.domain.z00.Shohin;
import com.showka.kubun.ShohinIdoKubun;
import com.showka.system.exception.SystemException;
import com.showka.value.EigyoDate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Nyuka extends DomainRoot {

	/** 部署. */
	private Busho busho;

	/** 入荷先. */
	private NyukaSaki nyukaSaki;

	/** 入荷日. */
	private EigyoDate nyukaDate;

	/** 商品移動(入荷). */
	private ShohinIdo nyukaShohinIdo;

	/** 入荷訂正商品移動リスト. */
	private List<ShohinIdo> teiseiList = new ArrayList<>();

	/**
	 * 引数.商品の入荷数(訂正数含む)取得.
	 * 
	 * @param shohin
	 *            商品
	 * @return 入荷数
	 */
	public Integer getNumber(Shohin shohin) {
		int nyukaNumber = this.nyukaShohinIdo.getAbusoluteIdoNumberForBushoZaiko(shohin);
		int teiseiNumber = this.teiseiList.parallelStream().mapToInt(teisei -> {
			return teisei.getAbusoluteIdoNumberForBushoZaiko(shohin);
		}).sum();
		return nyukaNumber + teiseiNumber;
	}

	/**
	 * 入荷商品リスト取得.
	 * 
	 * <pre>
	 * 入荷対象の商品のリストを取得する。
	 * ただし訂正後、入荷数=0となった商品は対象外。
	 * </pre>
	 * 
	 * @return
	 */
	public Set<Shohin> getShohinSet() {
		Set<Shohin> shohinSet = this.getShohinSetAll();
		return shohinSet.parallelStream().filter(s -> {
			return this.getNumber(s) != 0;
		}).collect(toSet());
	}

	/**
	 * 入荷商品のセットを取得.
	 * 
	 * <pre>
	 * 入荷数=0の商品も含む
	 * </pre>
	 * 
	 * @return
	 */
	private Set<Shohin> getShohinSetAll() {
		Set<Shohin> shohinSet = this.nyukaShohinIdo.getShohinSet();
		Set<Shohin> teiseiSet = this.teiseiList.parallelStream()
				.map(ShohinIdo::getShohinSet)
				.flatMap(Set::stream)
				.collect(toSet());
		shohinSet.addAll(teiseiSet);
		return shohinSet;
	}

	/**
	 * 訂正済判定.
	 * 
	 * @return 訂正済みならtrue
	 */
	public boolean doneTeisei() {
		return this.teiseiList.size() > 0;
	}

	/**
	 * 訂正済判定
	 * 
	 * @param teiseiDate
	 *            訂正日
	 * @return 引数.訂正日に訂正を行っていたらtrue
	 */
	public boolean doneTeisei(EigyoDate teiseiDate) {
		return this.teiseiList.parallelStream().anyMatch(t -> {
			return t.getDate().equals(teiseiDate);
		});
	}

	/**
	 * 商品移動取得.
	 * 
	 * @param idoDate
	 *            移動日.
	 * @return 引数.移動日における商品移動(入荷もしくは入荷訂正)
	 */
	public Optional<ShohinIdo> getShohinIdoOf(EigyoDate idoDate) {
		List<ShohinIdo> _shohinIdoList = this.teiseiList.parallelStream().collect(toList());
		_shohinIdoList.add(this.nyukaShohinIdo);
		return _shohinIdoList.parallelStream().filter(s -> {
			return s.getDate().equals(idoDate);
		}).findFirst();
	}

	/**
	 * 最新商品移動取得.
	 * 
	 * <pre>
	 * 訂正含むなら最近の訂正を返却する。
	 * </pre>
	 * 
	 * @return 商品移動.
	 */
	public ShohinIdo getNewestShohinIdo() {
		if (this.teiseiList.size() == 0) {
			return this.nyukaShohinIdo;
		}
		return this.teiseiList.parallelStream().max((one, two) -> {
			return one.getDate().compareTo(two.getDate());
		}).get();
	}

	// getter
	public String getShohinIdoId() {
		return this.nyukaShohinIdo.getRecordId();
	}

	public String getNyukaSakiId() {
		return this.nyukaSaki.getRecordId();
	}

	// override
	@Override
	protected boolean equals(DomainRoot other) {
		Nyuka o = (Nyuka) other;
		return this.nyukaShohinIdo.equals(o.nyukaShohinIdo);
	}

	@Override
	public int hashCode() {
		return super.generateHashCode(this.nyukaShohinIdo);
	}

	@Override
	public void validate() throws SystemException {
		if (this.nyukaShohinIdo.getKubun() != ShohinIdoKubun.入荷) {
			throw new SystemException("商品移動区分が誤っています。");
		}
		boolean includesInccorrect = this.teiseiList.parallelStream()
				.map(ShohinIdo::getKubun)
				.collect(toSet())
				.parallelStream()
				.anyMatch(kubun -> {
					return kubun != ShohinIdoKubun.入荷訂正;
				});
		if (includesInccorrect) {
			throw new SystemException("訂正に誤った商品移動区分が含まれています。");
		}
	}
}
