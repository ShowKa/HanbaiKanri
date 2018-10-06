package com.showka.domain.builder;

import java.math.BigDecimal;

import com.showka.domain.u05.UriageMeisai;
import com.showka.domain.z00.Shohin;

public class UriageMeisaiBuilder
		extends com.showka.domain.builder.DomainBuilderBase<UriageMeisai, UriageMeisaiBuilder> {

	// private member
	/** meisaiNumber */
	private Integer meisaiNumber;

	/** shohinDomain */
	private Shohin shohinDomain;

	/** hanbaiNumber */
	private Integer hanbaiNumber;

	/** hanbaiTanka */
	private BigDecimal hanbaiTanka;

	/** recordId */
	private String recordId;

	/** version */
	private Integer version;

	// protected method
	@Override
	protected void apply(UriageMeisai domain, UriageMeisaiBuilder builder) {
		builder.withMeisaiNumber(domain.getMeisaiNumber());
		builder.withShohinDomain(domain.getShohinDomain());
		builder.withHanbaiNumber(domain.getHanbaiNumber());
		builder.withHanbaiTanka(domain.getHanbaiTanka());
		builder.withRecordId(domain.getRecordId());
		builder.withVersion(domain.getVersion());
	}

	@Override
	protected UriageMeisai createDomainObject() {
		UriageMeisai domain = new UriageMeisai(meisaiNumber, shohinDomain, hanbaiNumber, hanbaiTanka);
		domain.setRecordId(recordId);
		domain.setVersion(version);
		return domain;
	}

	@Override
	protected UriageMeisaiBuilder getThis() {
		return this;
	}

	@Override
	protected UriageMeisaiBuilder newInstance() {
		return new UriageMeisaiBuilder();
	}

	// public method
	/**
	 * {@link UriageMeisai}に与えるmeisaiNumberをこのビルダに設定する。
	 *
	 * @param meisaiNumber
	 *            meisaiNumber
	 * @return {@link UriageMeisaiBuilder}
	 */
	public UriageMeisaiBuilder withMeisaiNumber(final Integer meisaiNumber) {
		addConfigurator(new BuilderConfigurator<UriageMeisaiBuilder>() {
			@Override
			public void configure(UriageMeisaiBuilder builder) {
				builder.meisaiNumber = meisaiNumber;
			}
		});
		return getThis();
	}

	/**
	 * {@link UriageMeisai}に与えるshohinDomainをこのビルダに設定する。
	 *
	 * @param shohinDomain
	 *            shohinDomain
	 * @return {@link UriageMeisaiBuilder}
	 */
	public UriageMeisaiBuilder withShohinDomain(final Shohin shohinDomain) {
		addConfigurator(new BuilderConfigurator<UriageMeisaiBuilder>() {
			@Override
			public void configure(UriageMeisaiBuilder builder) {
				builder.shohinDomain = shohinDomain;
			}
		});
		return getThis();
	}

	/**
	 * {@link UriageMeisai}に与えるhanbaiNumberをこのビルダに設定する。
	 *
	 * @param hanbaiNumber
	 *            hanbaiNumber
	 * @return {@link UriageMeisaiBuilder}
	 */
	public UriageMeisaiBuilder withHanbaiNumber(final Integer hanbaiNumber) {
		addConfigurator(new BuilderConfigurator<UriageMeisaiBuilder>() {
			@Override
			public void configure(UriageMeisaiBuilder builder) {
				builder.hanbaiNumber = hanbaiNumber;
			}
		});
		return getThis();
	}

	/**
	 * {@link UriageMeisai}に与えるhanbaiTankaをこのビルダに設定する。
	 *
	 * @param hanbaiTanka
	 *            hanbaiTanka
	 * @return {@link UriageMeisaiBuilder}
	 */
	public UriageMeisaiBuilder withHanbaiTanka(final BigDecimal hanbaiTanka) {
		addConfigurator(new BuilderConfigurator<UriageMeisaiBuilder>() {
			@Override
			public void configure(UriageMeisaiBuilder builder) {
				builder.hanbaiTanka = hanbaiTanka;
			}
		});
		return getThis();
	}

	/**
	 * {@link UriageMeisai}に与えるrecordIdをこのビルダに設定する。
	 *
	 * @param recordId
	 *            recordId
	 * @return {@link UriageMeisaiBuilder}
	 */
	public UriageMeisaiBuilder withRecordId(final String recordId) {
		addConfigurator(new BuilderConfigurator<UriageMeisaiBuilder>() {
			@Override
			public void configure(UriageMeisaiBuilder builder) {
				builder.recordId = recordId;
			}
		});
		return getThis();
	}

	/**
	 * {@link UriageMeisai}に与えるversionをこのビルダに設定する。
	 *
	 * @param version
	 *            version
	 * @return {@link UriageMeisaiBuilder}
	 */
	public UriageMeisaiBuilder withVersion(final Integer version) {
		addConfigurator(new BuilderConfigurator<UriageMeisaiBuilder>() {
			@Override
			public void configure(UriageMeisaiBuilder builder) {
				builder.version = version;
			}
		});
		return getThis();
	}

}