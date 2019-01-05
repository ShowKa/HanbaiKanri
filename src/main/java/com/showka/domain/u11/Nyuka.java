package com.showka.domain.u11;

import java.util.List;
import java.util.Optional;

import com.showka.domain.DomainRoot;
import com.showka.domain.z00.Busho;
import com.showka.domain.z00.Shohin;
import com.showka.system.exception.SystemException;
import com.showka.value.EigyoDate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class Nyuka extends DomainRoot {

	private Busho busho;
	private NyukaSaki nyukaSaki;
	private EigyoDate nyukaDate;
	private List<ShohinIdo> nyukaMeisaiList;
	private List<ShohinIdo> teiseiMeisaiList;

	/**
	 * 引数.商品の入荷数(訂正数含む)取得.
	 * 
	 * @param shohin
	 *            商品
	 * @return 入荷数
	 */
	public abstract Integer getNumber(Shohin shohin);

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
	public abstract List<Shohin> getShohinList();

	/**
	 * 商品移動取得.
	 * 
	 * @param idoDate
	 *            移動日.
	 * @return 引数.移動日における商品移動(入荷もしくは入荷訂正)
	 */
	public abstract Optional<ShohinIdo> getShohinIdoOf(EigyoDate idoDate);

	/**
	 * 最新商品移動取得.
	 * 
	 * <pre>
	 * 訂正含むなら最近の訂正を返却する。
	 * </pre>
	 * 
	 * @return 商品移動.
	 */
	public abstract ShohinIdo getNewestShohinIdo();

	@Override
	protected boolean equals(DomainRoot other) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void validate() throws SystemException {
		// TODO Auto-generated method stub
	}
}
