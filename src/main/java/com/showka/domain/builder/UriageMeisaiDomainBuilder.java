package com.showka.domain.builder;

import java.math.BigDecimal;

import com.showka.domain.ShohinDomain;
import com.showka.domain.UriageMeisaiDomain;

public class UriageMeisaiDomainBuilder
		extends com.showka.domain.builder.DomainBuilderBase<UriageMeisaiDomain, UriageMeisaiDomainBuilder> {

	// private member
	/** uriageId */
	private String uriageId;

	/** meisaiNumber */
	private Integer meisaiNumber;

	/** shohinDomain */
	private ShohinDomain shohinDomain;

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
	protected void apply(UriageMeisaiDomain domain, UriageMeisaiDomainBuilder builder) {
		builder.withUriageId(domain.getUriageId());
		builder.withMeisaiNumber(domain.getMeisaiNumber());
		builder.withShohinDomain(domain.getShohinDomain());
		builder.withHanbaiNumber(domain.getHanbaiNumber());
		builder.withHanbaiTanka(domain.getHanbaiTanka());
		builder.withRecordId(domain.getRecordId());
		builder.withVersion(domain.getVersion());
	}

	@Override
	protected UriageMeisaiDomain createDomainObject() {
		UriageMeisaiDomain domain = new UriageMeisaiDomain(uriageId, meisaiNumber, shohinDomain, hanbaiNumber,
				hanbaiTanka);
		domain.setRecordId(recordId);
		domain.setVersion(version);
		return domain;
	}

	@Override
	protected UriageMeisaiDomainBuilder getThis() {
		return this;
	}

	@Override
	protected UriageMeisaiDomainBuilder newInstance() {
		return new UriageMeisaiDomainBuilder();
	}

	// public method
	/**
	 * {@link UriageMeisaiDomain}に与えるuriageIdをこのビルダに設定する。
	 *
	 * @param uriageId
	 *            uriageId
	 * @return {@link UriageMeisaiDomainBuilder}
	 */
	public UriageMeisaiDomainBuilder withUriageId(final String uriageId) {
		addConfigurator(new BuilderConfigurator<UriageMeisaiDomainBuilder>() {
			@Override
			public void configure(UriageMeisaiDomainBuilder builder) {
				builder.uriageId = uriageId;
			}
		});
		return getThis();
	}

	/**
	 * {@link UriageMeisaiDomain}に与えるmeisaiNumberをこのビルダに設定する。
	 *
	 * @param meisaiNumber
	 *            meisaiNumber
	 * @return {@link UriageMeisaiDomainBuilder}
	 */
	public UriageMeisaiDomainBuilder withMeisaiNumber(final Integer meisaiNumber) {
		addConfigurator(new BuilderConfigurator<UriageMeisaiDomainBuilder>() {
			@Override
			public void configure(UriageMeisaiDomainBuilder builder) {
				builder.meisaiNumber = meisaiNumber;
			}
		});
		return getThis();
	}

	/**
	 * {@link UriageMeisaiDomain}に与えるshohinDomainをこのビルダに設定する。
	 *
	 * @param shohinDomain
	 *            shohinDomain
	 * @return {@link UriageMeisaiDomainBuilder}
	 */
	public UriageMeisaiDomainBuilder withShohinDomain(final ShohinDomain shohinDomain) {
		addConfigurator(new BuilderConfigurator<UriageMeisaiDomainBuilder>() {
			@Override
			public void configure(UriageMeisaiDomainBuilder builder) {
				builder.shohinDomain = shohinDomain;
			}
		});
		return getThis();
	}

	/**
	 * {@link UriageMeisaiDomain}に与えるhanbaiNumberをこのビルダに設定する。
	 *
	 * @param hanbaiNumber
	 *            hanbaiNumber
	 * @return {@link UriageMeisaiDomainBuilder}
	 */
	public UriageMeisaiDomainBuilder withHanbaiNumber(final Integer hanbaiNumber) {
		addConfigurator(new BuilderConfigurator<UriageMeisaiDomainBuilder>() {
			@Override
			public void configure(UriageMeisaiDomainBuilder builder) {
				builder.hanbaiNumber = hanbaiNumber;
			}
		});
		return getThis();
	}

	/**
	 * {@link UriageMeisaiDomain}に与えるhanbaiTankaをこのビルダに設定する。
	 *
	 * @param hanbaiTanka
	 *            hanbaiTanka
	 * @return {@link UriageMeisaiDomainBuilder}
	 */
	public UriageMeisaiDomainBuilder withHanbaiTanka(final BigDecimal hanbaiTanka) {
		addConfigurator(new BuilderConfigurator<UriageMeisaiDomainBuilder>() {
			@Override
			public void configure(UriageMeisaiDomainBuilder builder) {
				builder.hanbaiTanka = hanbaiTanka;
			}
		});
		return getThis();
	}

	/**
	 * {@link UriageMeisaiDomain}に与えるrecordIdをこのビルダに設定する。
	 *
	 * @param recordId
	 *            recordId
	 * @return {@link UriageMeisaiDomainBuilder}
	 */
	public UriageMeisaiDomainBuilder withRecordId(final String recordId) {
		addConfigurator(new BuilderConfigurator<UriageMeisaiDomainBuilder>() {
			@Override
			public void configure(UriageMeisaiDomainBuilder builder) {
				builder.recordId = recordId;
			}
		});
		return getThis();
	}

	/**
	 * {@link UriageMeisaiDomain}に与えるversionをこのビルダに設定する。
	 *
	 * @param version
	 *            version
	 * @return {@link UriageMeisaiDomainBuilder}
	 */
	public UriageMeisaiDomainBuilder withVersion(final Integer version) {
		addConfigurator(new BuilderConfigurator<UriageMeisaiDomainBuilder>() {
			@Override
			public void configure(UriageMeisaiDomainBuilder builder) {
				builder.version = version;
			}
		});
		return getThis();
	}

}