package com.showka.domain.builder;

import com.showka.domain.UriageCancelDomain;
import com.showka.domain.UriageDomain;

public class UriageCancelDomainBuilder
		extends com.showka.domain.builder.DomainBuilderBase<UriageCancelDomain, UriageCancelDomainBuilder> {

	// private member
	/** uriageDomain */
	private UriageDomain uriageDomain;

	/** recordId */
	private String recordId;

	/** version */
	private Integer version;

	// protected method
	@Override
	protected void apply(UriageCancelDomain domain, UriageCancelDomainBuilder builder) {
		builder.withUriageDomain(domain.getUriageDomain());
		builder.withRecordId(domain.getRecordId());
		builder.withVersion(domain.getVersion());
	}

	@Override
	protected UriageCancelDomain createDomainObject() {
		UriageCancelDomain domain = new UriageCancelDomain(uriageDomain);
		domain.setRecordId(recordId);
		domain.setVersion(version);
		return domain;
	}

	@Override
	protected UriageCancelDomainBuilder getThis() {
		return this;
	}

	@Override
	protected UriageCancelDomainBuilder newInstance() {
		return new UriageCancelDomainBuilder();
	}

	// public method
	/**
	 * {@link UriageCancelDomain}に与えるuriageDomainをこのビルダに設定する。
	 *
	 * @param uriageDomain
	 *            uriageDomain
	 * @return {@link UriageCancelDomainBuilder}
	 */
	public UriageCancelDomainBuilder withUriageDomain(final UriageDomain uriageDomain) {
		addConfigurator(new BuilderConfigurator<UriageCancelDomainBuilder>() {
			@Override
			public void configure(UriageCancelDomainBuilder builder) {
				builder.uriageDomain = uriageDomain;
			}
		});
		return getThis();
	}

	/**
	 * {@link UriageCancelDomain}に与えるrecordIdをこのビルダに設定する。
	 *
	 * @param recordId
	 *            recordId
	 * @return {@link UriageCancelDomainBuilder}
	 */
	public UriageCancelDomainBuilder withRecordId(final String recordId) {
		addConfigurator(new BuilderConfigurator<UriageCancelDomainBuilder>() {
			@Override
			public void configure(UriageCancelDomainBuilder builder) {
				builder.recordId = recordId;
			}
		});
		return getThis();
	}

	/**
	 * {@link UriageCancelDomain}に与えるversionをこのビルダに設定する。
	 *
	 * @param version
	 *            version
	 * @return {@link UriageCancelDomainBuilder}
	 */
	public UriageCancelDomainBuilder withVersion(final Integer version) {
		addConfigurator(new BuilderConfigurator<UriageCancelDomainBuilder>() {
			@Override
			public void configure(UriageCancelDomainBuilder builder) {
				builder.version = version;
			}
		});
		return getThis();
	}

}