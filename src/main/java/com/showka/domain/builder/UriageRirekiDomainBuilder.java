package com.showka.domain.builder;

import java.util.List;

import com.showka.domain.UriageDomain;
import com.showka.domain.UriageRirekiDomain;

public class UriageRirekiDomainBuilder
		extends com.showka.domain.builder.DomainBuilderBase<UriageRirekiDomain, UriageRirekiDomainBuilder> {

	// private member
	/** uriageRireki */
	private List<UriageDomain> uriageRireki;

	/** recordId */
	private String recordId;

	/** version */
	private Integer version;

	// protected method
	@Override
	protected void apply(UriageRirekiDomain domain, UriageRirekiDomainBuilder builder) {
		builder.withUriageRireki(domain.getList());
		builder.withRecordId(domain.getRecordId());
		builder.withVersion(domain.getVersion());
	}

	@Override
	protected UriageRirekiDomain createDomainObject() {
		UriageRirekiDomain domain = new UriageRirekiDomain(uriageRireki);
		domain.setRecordId(recordId);
		domain.setVersion(version);
		return domain;
	}

	@Override
	protected UriageRirekiDomainBuilder getThis() {
		return this;
	}

	@Override
	protected UriageRirekiDomainBuilder newInstance() {
		return new UriageRirekiDomainBuilder();
	}

	// public method
	/**
	 * {@link UriageRirekiDomain}に与えるuriageRirekiをこのビルダに設定する。
	 *
	 * @param uriageRireki
	 *            uriageRireki
	 * @return {@link UriageRirekiDomainBuilder}
	 */
	public UriageRirekiDomainBuilder withUriageRireki(final List<UriageDomain> uriageRireki) {
		addConfigurator(new BuilderConfigurator<UriageRirekiDomainBuilder>() {
			@Override
			public void configure(UriageRirekiDomainBuilder builder) {
				builder.uriageRireki = uriageRireki;
			}
		});
		return getThis();
	}

	/**
	 * {@link UriageRirekiDomain}に与えるrecordIdをこのビルダに設定する。
	 *
	 * @param recordId
	 *            recordId
	 * @return {@link UriageRirekiDomainBuilder}
	 */
	public UriageRirekiDomainBuilder withRecordId(final String recordId) {
		addConfigurator(new BuilderConfigurator<UriageRirekiDomainBuilder>() {
			@Override
			public void configure(UriageRirekiDomainBuilder builder) {
				builder.recordId = recordId;
			}
		});
		return getThis();
	}

	/**
	 * {@link UriageRirekiDomain}に与えるversionをこのビルダに設定する。
	 *
	 * @param version
	 *            version
	 * @return {@link UriageRirekiDomainBuilder}
	 */
	public UriageRirekiDomainBuilder withVersion(final Integer version) {
		addConfigurator(new BuilderConfigurator<UriageRirekiDomainBuilder>() {
			@Override
			public void configure(UriageRirekiDomainBuilder builder) {
				builder.version = version;
			}
		});
		return getThis();
	}

}