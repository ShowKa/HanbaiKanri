package com.showka.domain.builder;

import com.showka.domain.UriageCancel;
import com.showka.domain.Uriage;

public class UriageCancelBuilder
		extends com.showka.domain.builder.DomainBuilderBase<UriageCancel, UriageCancelBuilder> {

	// private member
	/** uriageDomain */
	private Uriage uriageDomain;

	/** recordId */
	private String recordId;

	/** version */
	private Integer version;

	// protected method
	@Override
	protected void apply(UriageCancel domain, UriageCancelBuilder builder) {
		builder.withUriageDomain(domain.getUriageDomain());
		builder.withRecordId(domain.getRecordId());
		builder.withVersion(domain.getVersion());
	}

	@Override
	protected UriageCancel createDomainObject() {
		UriageCancel domain = new UriageCancel(uriageDomain);
		domain.setRecordId(recordId);
		domain.setVersion(version);
		return domain;
	}

	@Override
	protected UriageCancelBuilder getThis() {
		return this;
	}

	@Override
	protected UriageCancelBuilder newInstance() {
		return new UriageCancelBuilder();
	}

	// public method
	/**
	 * {@link UriageCancel}に与えるuriageDomainをこのビルダに設定する。
	 *
	 * @param uriageDomain
	 *            uriageDomain
	 * @return {@link UriageCancelBuilder}
	 */
	public UriageCancelBuilder withUriageDomain(final Uriage uriageDomain) {
		addConfigurator(new BuilderConfigurator<UriageCancelBuilder>() {
			@Override
			public void configure(UriageCancelBuilder builder) {
				builder.uriageDomain = uriageDomain;
			}
		});
		return getThis();
	}

	/**
	 * {@link UriageCancel}に与えるrecordIdをこのビルダに設定する。
	 *
	 * @param recordId
	 *            recordId
	 * @return {@link UriageCancelBuilder}
	 */
	public UriageCancelBuilder withRecordId(final String recordId) {
		addConfigurator(new BuilderConfigurator<UriageCancelBuilder>() {
			@Override
			public void configure(UriageCancelBuilder builder) {
				builder.recordId = recordId;
			}
		});
		return getThis();
	}

	/**
	 * {@link UriageCancel}に与えるversionをこのビルダに設定する。
	 *
	 * @param version
	 *            version
	 * @return {@link UriageCancelBuilder}
	 */
	public UriageCancelBuilder withVersion(final Integer version) {
		addConfigurator(new BuilderConfigurator<UriageCancelBuilder>() {
			@Override
			public void configure(UriageCancelBuilder builder) {
				builder.version = version;
			}
		});
		return getThis();
	}

}