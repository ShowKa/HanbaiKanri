package com.showka.domain.builder;

import java.util.List;

import com.showka.domain.KokyakuDomain;
import com.showka.domain.UriageDomain;
import com.showka.kubun.HanbaiKubun;
import com.showka.value.TaxRate;
import com.showka.value.TheDate;

public class UriageDomainBuilder
		extends com.showka.domain.builder.DomainBuilderBase<UriageDomain, UriageDomainBuilder> {

	// private member
	/** kokyaku */
	private KokyakuDomain kokyaku;

	/** denpyoNumber */
	private String denpyoNumber;

	/** uriageDate */
	private TheDate uriageDate;

	/** keijoDate */
	private TheDate keijoDate;

	/** hanbaiKubun */
	private HanbaiKubun hanbaiKubun;

	/** shohizeiritsu */
	private TaxRate shohizeiritsu;

	/** uriageMeisai */
	private List uriageMeisai;

	/** recordId */
	private String recordId;

	/** version */
	private Integer version;

	// protected method
	@Override
	protected void apply(UriageDomain domain, UriageDomainBuilder builder) {
		builder.withKokyaku(domain.getKokyaku());
		builder.withDenpyoNumber(domain.getDenpyoNumber());
		builder.withUriageDate(domain.getUriageDate());
		builder.withKeijoDate(domain.getKeijoDate());
		builder.withHanbaiKubun(domain.getHanbaiKubun());
		builder.withShohizeiritsu(domain.getShohizeiritsu());
		builder.withUriageMeisai(domain.getUriageMeisai());
		builder.withRecordId(domain.getRecordId());
		builder.withVersion(domain.getVersion());
	}

	@Override
	protected UriageDomain createDomainObject() {
		UriageDomain domain = new UriageDomain(kokyaku, denpyoNumber, uriageDate, keijoDate, hanbaiKubun, shohizeiritsu,
				uriageMeisai);
		domain.setRecordId(recordId);
		domain.setVersion(version);
		return domain;
	}

	@Override
	protected UriageDomainBuilder getThis() {
		return this;
	}

	@Override
	protected UriageDomainBuilder newInstance() {
		return new UriageDomainBuilder();
	}

	// public method
	/**
	 * {@link UriageDomain}に与えるkokyakuをこのビルダに設定する。
	 *
	 * @param kokyaku
	 *            kokyaku
	 * @return {@link UriageDomainBuilder}
	 */
	public UriageDomainBuilder withKokyaku(final KokyakuDomain kokyaku) {
		addConfigurator(new BuilderConfigurator<UriageDomainBuilder>() {
			@Override
			public void configure(UriageDomainBuilder builder) {
				builder.kokyaku = kokyaku;
			}
		});
		return getThis();
	}

	/**
	 * {@link UriageDomain}に与えるdenpyoNumberをこのビルダに設定する。
	 *
	 * @param denpyoNumber
	 *            denpyoNumber
	 * @return {@link UriageDomainBuilder}
	 */
	public UriageDomainBuilder withDenpyoNumber(final String denpyoNumber) {
		addConfigurator(new BuilderConfigurator<UriageDomainBuilder>() {
			@Override
			public void configure(UriageDomainBuilder builder) {
				builder.denpyoNumber = denpyoNumber;
			}
		});
		return getThis();
	}

	/**
	 * {@link UriageDomain}に与えるuriageDateをこのビルダに設定する。
	 *
	 * @param uriageDate
	 *            uriageDate
	 * @return {@link UriageDomainBuilder}
	 */
	public UriageDomainBuilder withUriageDate(final TheDate uriageDate) {
		addConfigurator(new BuilderConfigurator<UriageDomainBuilder>() {
			@Override
			public void configure(UriageDomainBuilder builder) {
				builder.uriageDate = uriageDate;
			}
		});
		return getThis();
	}

	/**
	 * {@link UriageDomain}に与えるkeijoDateをこのビルダに設定する。
	 *
	 * @param keijoDate
	 *            keijoDate
	 * @return {@link UriageDomainBuilder}
	 */
	public UriageDomainBuilder withKeijoDate(final TheDate keijoDate) {
		addConfigurator(new BuilderConfigurator<UriageDomainBuilder>() {
			@Override
			public void configure(UriageDomainBuilder builder) {
				builder.keijoDate = keijoDate;
			}
		});
		return getThis();
	}

	/**
	 * {@link UriageDomain}に与えるhanbaiKubunをこのビルダに設定する。
	 *
	 * @param hanbaiKubun
	 *            hanbaiKubun
	 * @return {@link UriageDomainBuilder}
	 */
	public UriageDomainBuilder withHanbaiKubun(final HanbaiKubun hanbaiKubun) {
		addConfigurator(new BuilderConfigurator<UriageDomainBuilder>() {
			@Override
			public void configure(UriageDomainBuilder builder) {
				builder.hanbaiKubun = hanbaiKubun;
			}
		});
		return getThis();
	}

	/**
	 * {@link UriageDomain}に与えるshohizeiritsuをこのビルダに設定する。
	 *
	 * @param shohizeiritsu
	 *            shohizeiritsu
	 * @return {@link UriageDomainBuilder}
	 */
	public UriageDomainBuilder withShohizeiritsu(final TaxRate shohizeiritsu) {
		addConfigurator(new BuilderConfigurator<UriageDomainBuilder>() {
			@Override
			public void configure(UriageDomainBuilder builder) {
				builder.shohizeiritsu = shohizeiritsu;
			}
		});
		return getThis();
	}

	/**
	 * {@link UriageDomain}に与えるuriageMeisaiをこのビルダに設定する。
	 *
	 * @param uriageMeisai
	 *            uriageMeisai
	 * @return {@link UriageDomainBuilder}
	 */
	public UriageDomainBuilder withUriageMeisai(final List uriageMeisai) {
		addConfigurator(new BuilderConfigurator<UriageDomainBuilder>() {
			@Override
			public void configure(UriageDomainBuilder builder) {
				builder.uriageMeisai = uriageMeisai;
			}
		});
		return getThis();
	}

	/**
	 * {@link UriageDomain}に与えるrecordIdをこのビルダに設定する。
	 *
	 * @param recordId
	 *            recordId
	 * @return {@link UriageDomainBuilder}
	 */
	public UriageDomainBuilder withRecordId(final String recordId) {
		addConfigurator(new BuilderConfigurator<UriageDomainBuilder>() {
			@Override
			public void configure(UriageDomainBuilder builder) {
				builder.recordId = recordId;
			}
		});
		return getThis();
	}

	/**
	 * {@link UriageDomain}に与えるversionをこのビルダに設定する。
	 *
	 * @param version
	 *            version
	 * @return {@link UriageDomainBuilder}
	 */
	public UriageDomainBuilder withVersion(final Integer version) {
		addConfigurator(new BuilderConfigurator<UriageDomainBuilder>() {
			@Override
			public void configure(UriageDomainBuilder builder) {
				builder.version = version;
			}
		});
		return getThis();
	}

}