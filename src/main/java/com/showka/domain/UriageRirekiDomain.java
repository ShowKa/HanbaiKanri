package com.showka.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.showka.kubun.HanbaiKubun;
import com.showka.system.exception.SystemException;
import com.showka.value.TaxRate;
import com.showka.value.TheDate;

import lombok.Getter;

/**
 * 売上履歴ドメイン
 * 
 * <pre>
 * 売上ドメインを継承。
 * 異なる点は、「顧客、伝票番号、計上日」で同値判定を行うこと。
 * </pre>
 * 
 * @author ShowKa
 *
 */

public class UriageRirekiDomain extends UriageDomain {

	/** 売上ID. */
	@Getter
	private String uriageId;

	public UriageRirekiDomain(String uriageId, KokyakuDomain kokyaku, String denpyoNumber, TheDate uriageDate,
			TheDate keijoDate, HanbaiKubun hanbaiKubun, TaxRate shohizeiritsu,
			List<UriageRirekiMeisaiDomain> uriageMeisai) {
		super(kokyaku, denpyoNumber, uriageDate, keijoDate, hanbaiKubun, shohizeiritsu, uriageMeisai);
		this.uriageId = uriageId;
	}

	@SuppressWarnings("unchecked")
	public List<UriageRirekiMeisaiDomain> getUriageRirekiMeisai() {
		return (List<UriageRirekiMeisaiDomain>) getUriageMeisai();
	}

	@Override
	public void validate() throws SystemException {
		// do nothing
	}

	@Override
	public boolean equals(DomainBase other) {
		UriageRirekiDomain o = (UriageRirekiDomain) other;
		return uriageId.equals(o.uriageId) && getKeijoDate().equals(o.getKeijoDate());
	}

	@Override
	public int hashCode() {
		return generateHashCode(uriageId, getKeijoDate());
	}

	public boolean hasSameMeisaiNumberWith(UriageMeisaiDomain uriageMeisai) {
		return getUriageRirekiMeisai().stream().filter(_meisai -> {
			return _meisai.getMeisaiNumber().equals(uriageMeisai.getMeisaiNumber());
		}).count() > 0;
	}

	public UriageRirekiDomain getOverriddenBy(UriageDomain uriage) {
		List<UriageRirekiMeisaiDomain> newMeisai = new ArrayList<UriageRirekiMeisaiDomain>();
		uriage.getUriageMeisai().stream().filter(meisaiForMerge -> {
			return this.hasSameMeisaiNumberWith(meisaiForMerge);
		}).forEach(meisaiForMerge -> {
			this.getUriageRirekiMeisai().stream().filter(meisaiOrverridden -> {
				return meisaiOrverridden.getMeisaiNumber().equals(meisaiForMerge.getMeisaiNumber());
			}).forEach(meisaiOrverridden -> {
				newMeisai.add(meisaiOrverridden.getOverriddenBy(meisaiForMerge));
			});
		});
		uriage.getUriageMeisai().stream().filter(meisaiToCreate -> {
			return !this.hasSameMeisaiNumberWith(meisaiToCreate);
		}).forEach(meisaiToCreate -> {
			UriageRirekiMeisaiDomain created = new UriageRirekiMeisaiDomain(getUriageId(),
					meisaiToCreate.getMeisaiNumber(), meisaiToCreate.getShohinDomain(),
					meisaiToCreate.getHanbaiNumber(), meisaiToCreate.getHanbaiTanka());
			created.setRecordId(UUID.randomUUID().toString());
			newMeisai.add(created);
		});
		return new UriageRirekiDomain(getUriageId(), getKokyaku(), getDenpyoNumber(), uriage.getUriageDate(),
				getKeijoDate(), uriage.getHanbaiKubun(), uriage.getShohizeiritsu(), newMeisai);
	}

}