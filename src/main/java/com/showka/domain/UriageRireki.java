package com.showka.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.showka.domain.Uriage.UriageComparatorByKejoDate;
import com.showka.domain.builder.UriageBuilder;
import com.showka.domain.builder.UriageMeisaiBuilder;
import com.showka.system.exception.SystemException;
import com.showka.value.TheDate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UriageRireki extends DomainBase {

	/** 売上ID */
	private String uriageId;

	/** 売上履歴(直接返却禁止) */
	private List<Uriage> list;

	/** キャンセル計上日 */
	private Optional<TheDate> cancelKeijoDate;

	/**
	 * 引数に指定した計上日の売上を取得します。
	 * 
	 * @param keijoDate
	 *            計上日
	 * @return 売上
	 */
	public Optional<Uriage> getUriageOf(TheDate keijoDate) {
		Optional<Uriage> uriage = list.stream().filter(l -> {
			return l.getKeijoDate().equals(keijoDate);
		}).findFirst();
		if (uriage.isPresent()) {
			return Optional.of(convert(uriage.get()));
		}
		return Optional.empty();
	}

	/**
	 * (赤)訂正売上伝票取得.
	 * 
	 * @param keijoDate
	 *            訂正した計上日
	 * @return 訂正した伝票
	 */
	public Optional<Uriage> getTeiseiUriage(TheDate keijoDate) {

		list.sort(new UriageComparatorByKejoDate());
		Collections.reverse(list);
		Optional<Uriage> uriage = list.stream().filter(l -> {
			return l.getKeijoDate().compareTo(keijoDate) < 0;
		}).findFirst();

		if (uriage.isPresent()) {
			Uriage _u = uriage.get();
			UriageBuilder b = new UriageBuilder();
			b.withKeijoDate(keijoDate);
			// convert
			List<UriageMeisai> _meisai = _u.getUriageMeisai().stream().map(meisai -> {
				UriageMeisaiBuilder mb = new UriageMeisaiBuilder();
				mb.withHanbaiNumber(meisai.getHanbaiNumber() * -1);
				return mb.apply(meisai);
			}).collect(Collectors.toList());
			// sort
			Collections.sort(_meisai);
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
	public List<Uriage> getAllWithTeiseiDenpyo() {
		// create return value
		List<Uriage> ret = new ArrayList<Uriage>();

		// order by keijoDate desc
		list.sort(new UriageComparatorByKejoDate());
		Collections.reverse(list);

		// add 伝票
		new ArrayList<Uriage>(list).stream().forEach(uriage -> {
			ret.add(uriage);
			Optional<Uriage> teisei = getTeiseiUriage(uriage.getKeijoDate());
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
	public Uriage getNewest() {
		// get new one
		Uriage newest = list.stream().max((r1, r2) -> {
			return r1.getKeijoDate().getDate().compareTo(r2.getKeijoDate().getDate());
		}).get();
		return convert(newest);
	}

	/**
	 * 最新伝票よりひとつ前の伝票を取得.
	 * 
	 * <pre>
	 * 前回伝票が存在しない場合はemptyを返却
	 * </pre>
	 * 
	 * @return 前回の伝票
	 */
	public Optional<Uriage> getPrevious() {
		int size = list.size();
		if (size == 1) {
			return Optional.empty();
		}
		list.sort(new UriageComparatorByKejoDate());
		Uriage target = convert(list.get(size - 2));
		return Optional.of(target);
	}

	/**
	 * 全履歴売上のリスト取得.
	 * 
	 * @return 全履歴売上
	 */
	public List<Uriage> getList() {
		List<Uriage> ret = list.stream().map(l -> {
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
		UriageRireki o = (UriageRireki) other;
		Uriage newest = getNewest();
		Uriage otherNewest = o.getNewest();
		return newest.equals(otherNewest);
	}

	@Override
	public int hashCode() {
		Uriage newest = getNewest();
		return newest.hashCode();
	}

	/**
	 * 内包する履歴データを売上ドメインにコンバートして返却
	 * 
	 * @param uriage
	 *            売上履歴リストの一部
	 * @return
	 */
	// FIXME 普通にバグりそう
	private Uriage convert(Uriage uriage) {
		UriageBuilder b = new UriageBuilder();
		b.withRecordId(uriageId);
		// b.withUriageMeisai(uriage.getUriageMeisai());
		return b.apply(uriage);
	}
}
