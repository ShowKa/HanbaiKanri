package com.showka.domain.u11;

import static java.util.stream.Collectors.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.showka.domain.DomainRoot;
import com.showka.domain.builder.ShohinIdoBuilder;
import com.showka.domain.builder.ShohinIdoMeisaiBuilder;
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
public class Nyuka extends DomainRoot {

	/** 入荷先. */
	private NyukaSaki nyukaSaki;

	/** 商品移動入荷. */
	private ShohinIdo nyukaShohinIdo;

	/** 商品移動入荷訂正リスト. */
	private List<ShohinIdo> teiseiList = new ArrayList<>();

	/**
	 * 引数.商品の入荷数(訂正数含む)取得.
	 * 
	 * @param shohin
	 *            商品
	 * @return 入荷数
	 */
	public Integer getNumber(Shohin shohin) {
		int nyukaNumber = this.nyukaShohinIdo.getNumberForBushoZaiko(shohin);
		int teiseiNumber = this.teiseiList.parallelStream().mapToInt(teisei -> {
			return teisei.getNumberForBushoZaiko(shohin);
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

	/**
	 * 訂正.
	 * 
	 * <pre>
	 * 訂正の商品移動を作成します。
	 * </pre>
	 * 
	 * @param target
	 *            対象商品
	 * @param nyukaNumber
	 *            入荷数(訂正後)
	 */
	public void teisei(Shohin target, Integer nyukaNumber) {
		// 現在入荷数=引数.入荷数の場合、何もしない
		Integer number = this.getNumber(target);
		if (number.equals(nyukaNumber)) {
			return;
		}
		// 移動数
		Integer idosuu = nyukaNumber - number;
		// 営業日
		EigyoDate eigyoDate = this.getBusho().getEigyoDate();
		// 商品移動
		Optional<ShohinIdo> _teisei = this.teiseiList.parallelStream()
				.filter(t -> t.getDate().equals(eigyoDate))
				.findFirst();
		ShohinIdo teisei = _teisei.orElseGet(() -> {
			ShohinIdoBuilder b = new ShohinIdoBuilder();
			b.withBusho(this.getBusho());
			b.withDate(eigyoDate);
			b.withKubun(ShohinIdoKubun.入荷訂正);
			b.withMeisai(new ArrayList<>());
			b.withTimestamp(new TheTimestamp());
			return b.build();
		});
		// 商品移動明細
		Optional<ShohinIdoMeisai> _meisai = teisei.getMeisai()
				.parallelStream()
				.filter(m -> m.getShohinDomain().equals(target))
				.findFirst();
		ShohinIdoMeisai meisai;
		if (_meisai.isPresent()) {
			// 移動数だけ上書き
			ShohinIdoMeisaiBuilder b = new ShohinIdoMeisaiBuilder();
			meisai = b.withNumber(idosuu).apply(_meisai.get());
		} else {
			ShohinIdoMeisaiBuilder b = new ShohinIdoMeisaiBuilder();
			Integer n = teisei.getMaxMeisaiNumber() + 1;
			b.withMeisaiNumber(n);
			b.withNumber(idosuu);
			b.withShohinDomain(target);
			meisai = b.build();
		}
		// 明細を訂正商品移動にmerge
		teisei.mergeMeisai(meisai);
		// 訂正商品移動を入荷にmerge
		this.mergeTeisei(teisei);
	}

	// getter
	/**
	 * 全商品移動リスト取得
	 * 
	 * <pre>
	 * 入荷と入荷訂正の商品移動すべてを取得
	 * </pre>
	 * 
	 * @return 全商品移動リスト
	 */
	public List<ShohinIdo> getAllShohinIdoList() {
		List<ShohinIdo> list = new ArrayList<>();
		list.add(nyukaShohinIdo);
		list.addAll(teiseiList);
		return list;
	}

	/**
	 * 商品移動取得.
	 * 
	 * <pre>
	 * 存在しないIDを渡した場合エラー
	 * </pre>
	 * 
	 * @param shohinIdoId
	 *            商品移動ID
	 * @return 商品移動
	 */
	public ShohinIdo getShohinIdoById(String shohinIdoId) {
		return this.getAllShohinIdoList().stream().filter(s -> shohinIdoId.equals(s.getRecordId())).findFirst().get();
	}

	public String getShohinIdoId() {
		return this.nyukaShohinIdo.getRecordId();
	}

	public String getNyukaSakiId() {
		return this.nyukaSaki.getRecordId();
	}

	public Busho getBusho() {
		return this.nyukaShohinIdo.getBusho();
	}

	public EigyoDate getNyukaDate() {
		return this.nyukaShohinIdo.getDate();
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

	// protected or private
	private void mergeTeisei(ShohinIdo target) {
		this.teiseiList.remove(target);
		this.teiseiList.add(target);
	}
}
