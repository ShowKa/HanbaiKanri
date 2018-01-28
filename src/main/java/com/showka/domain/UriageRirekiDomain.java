package com.showka.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.showka.domain.UriageDomain.UriageComparatorByKejoDate;
import com.showka.domain.UriageMeisaiDomain.UriageMeisaiComparatorByMeisaiNumber;
import com.showka.domain.builder.UriageDomainBuilder;
import com.showka.domain.builder.UriageMeisaiDomainBuilder;
import com.showka.system.exception.SystemException;
import com.showka.value.TheDate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UriageRirekiDomain extends DomainBase {

	/** 売上ID */
	private String uriageId;

	/** 売上履歴(直接返却禁止) */
	private List<UriageDomain> list;

	/** キャンセル計上日 */
	private Optional<TheDate> cancelKeijoDate;

	/**
	 * (赤)訂正売上伝票取得.
	 * 
	 * @param keijoDate
	 *            訂正した計上日
	 * @return 訂正した伝票
	 */
	public Optional<UriageDomain> getTeiseiUriage(TheDate keijoDate) {

		list.sort(new UriageComparatorByKejoDate());
		Collections.reverse(list);
		Optional<UriageDomain> uriage = list.stream().filter(l -> {
			return l.getKeijoDate().compareTo(keijoDate) < 0;
		}).findFirst();

		if (uriage.isPresent()) {
			UriageDomain _u = uriage.get();
			UriageDomainBuilder b = new UriageDomainBuilder();
			b.withKeijoDate(keijoDate);
			// convert
			List<UriageMeisaiDomain> _meisai = _u.getUriageMeisai().stream().map(meisai -> {
				UriageMeisaiDomainBuilder mb = new UriageMeisaiDomainBuilder();
				mb.withHanbaiNumber(meisai.getHanbaiNumber() * -1);
				return mb.apply(meisai);
			}).collect(Collectors.toList());
			// sort
			_meisai.sort(new UriageMeisaiComparatorByMeisaiNumber());
			b.withUriageMeisai(_meisai);
			return Optional.of(b.apply(_u));
		}

		return Optional.empty();
	}

	/**
	 * (赤)訂正伝票も含めた全伝票取得.
	 * 
	 * <pre>
	 * 訂正伝票が不要な場合は、getList()を利用すること。
	 * </pre>
	 * 
	 * @return 全伝票
	 */
	public List<UriageDomain> getAllWithTeiseiDenpyo() {
		// create return value
		List<UriageDomain> ret = new ArrayList<UriageDomain>();

		// order by keijoDate desc
		list.sort(new UriageComparatorByKejoDate());
		Collections.reverse(list);

		// add 伝票
		new ArrayList<UriageDomain>(list).stream().forEach(uriage -> {
			ret.add(uriage);
			Optional<UriageDomain> teisei = getTeiseiUriage(uriage.getKeijoDate());
			if (teisei.isPresent()) {
				ret.add(teisei.get());
			}
		});

		// sort and return
		return ret;
	}

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
		List<UriageDomain> ret = list.stream().map(l -> {
			return convert(l);
		}).collect(Collectors.toList());
		ret.sort(new UriageComparatorByKejoDate());
		return ret;
	}

	@Override
	public void validate() throws SystemException {
		for (int i = 0; i < list.size(); i++) {
			for (int j = i + 1; j < list.size(); j++) {
				if (list.get(i).getKeijoDate().equals(list.get(j).getKeijoDate())) {
					throw new SystemException("売上履歴に同じ計上日の伝票が混じっています。");
				}
			}
		}
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
