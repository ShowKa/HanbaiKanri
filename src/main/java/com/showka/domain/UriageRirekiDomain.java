package com.showka.domain;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.showka.domain.builder.UriageDomainBuilder;
import com.showka.domain.builder.UriageMeisaiDomainBuilder;
import com.showka.system.exception.SystemException;
import com.showka.value.TheDate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class UriageRirekiDomain extends DomainBase {

	/** 売上ID */
	@Getter
	private String uriageId;

	/** 売上履歴(直接返却禁止) */
	private List<UriageDomain> list;

	/** キャンセル計上日 */
	@Getter
	private Optional<TheDate> cancelKeijoDate;

	/**
	 * キャンセル判定.
	 * 
	 * @return キャンセルした売上であるならtrue
	 */
	public boolean isCanceld() {
		return cancelKeijoDate.isPresent();
	}

	/**
	 * 最新伝票取得.
	 * 
	 * <pre>
	 * 計上日が最も新しい売上ドメインを返す。
	 * </pre>
	 * 
	 * @return 最新伝票
	 */
	public UriageDomain getNewest() {
		// get new one
		UriageDomain newest = list.stream().max((r1, r2) -> {
			return r1.getKeijoDate().getDate().compareTo(r2.getKeijoDate().getDate());
		}).get();
		return convert(newest);
	}

	/**
	 * 全履歴売上のリスト取得.
	 * 
	 * @return 全履歴売上
	 */
	public List<UriageDomain> getList() {
		return list.stream().map(l -> {
			return convert(l);
		}).collect(Collectors.toList());
	}

	@Override
	public void validate() throws SystemException {
		// nothing to do
	}

	@Override
	protected boolean equals(DomainBase other) {
		UriageRirekiDomain o = (UriageRirekiDomain) other;
		UriageDomain newest = getNewest();
		UriageDomain otherNewest = o.getNewest();
		return newest.equals(otherNewest);
	}

	@Override
	public int hashCode() {
		UriageDomain newest = getNewest();
		return newest.hashCode();
	}

	/**
	 * 内包する履歴データを売上ドメインにコンバートして返却
	 * 
	 * @param uriage
	 *            売上履歴リストの一部
	 * @return
	 */
	private UriageDomain convert(UriageDomain uriage) {
		// set uriage id
		UriageDomainBuilder b = new UriageDomainBuilder();
		b.withRecordId(uriageId);
		// set uriage id to list
		List<UriageMeisaiDomain> meisai = uriage.getUriageMeisai().stream().map(m -> {
			UriageMeisaiDomainBuilder bm = new UriageMeisaiDomainBuilder();
			bm.withUriageId(uriageId);
			return bm.apply(m);
		}).collect(Collectors.toList());
		b.withUriageMeisai(meisai);
		// build & return
		return b.apply(uriage);
	}
}
