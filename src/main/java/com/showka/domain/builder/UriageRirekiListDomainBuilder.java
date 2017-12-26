package com.showka.domain.builder;

import java.util.Set;

import com.showka.domain.UriageRirekiDomain;
import com.showka.domain.UriageRirekiListDomain;

public class UriageRirekiListDomainBuilder
		extends com.showka.domain.builder.DomainBuilderBase<UriageRirekiListDomain, UriageRirekiListDomainBuilder> {

	// private member
	/** uriageRireki */
	private Set<UriageRirekiDomain> uriageRireki;

	/** recordId */
	private String recordId;

	/** version */
	private Integer version;

	// protected method
	@Override
	protected void apply(UriageRirekiListDomain domain, UriageRirekiListDomainBuilder builder) {
		builder.withUriageRirekiList(domain.getList());
		builder.withRecordId(domain.getRecordId());
		builder.withVersion(domain.getVersion());
	}

	@Override
	protected UriageRirekiListDomain createDomainObject() {
		UriageRirekiListDomain domain = new UriageRirekiListDomain(uriageRireki);
		domain.setRecordId(recordId);
		domain.setVersion(version);
		return domain;
	}

	@Override
	protected UriageRirekiListDomainBuilder getThis() {
		return this;
	}

	@Override
	protected UriageRirekiListDomainBuilder newInstance() {
		return new UriageRirekiListDomainBuilder();
	}

	// public method
	/**
	 * {@link UriageRirekiListDomain}に与えるuriageRirekiをこのビルダに設定する。
	 *
	 * @param uriageRireki
	 *            uriageRireki
	 * @return {@link UriageRirekiListDomainBuilder}
	 */
	public UriageRirekiListDomainBuilder withUriageRirekiList(final Set<UriageRirekiDomain> uriageRireki) {
		addConfigurator(new BuilderConfigurator<UriageRirekiListDomainBuilder>() {
			@Override
			public void configure(UriageRirekiListDomainBuilder builder) {
				builder.uriageRireki = uriageRireki;
			}
		});
		return getThis();
	}

	/**
	 * {@link UriageRirekiListDomain}に与えるrecordIdをこのビルダに設定する。
	 *
	 * @param recordId
	 *            recordId
	 * @return {@link UriageRirekiListDomainBuilder}
	 */
	public UriageRirekiListDomainBuilder withRecordId(final String recordId) {
		addConfigurator(new BuilderConfigurator<UriageRirekiListDomainBuilder>() {
			@Override
			public void configure(UriageRirekiListDomainBuilder builder) {
				builder.recordId = recordId;
			}
		});
		return getThis();
	}

	/**
	 * {@link UriageRirekiListDomain}に与えるversionをこのビルダに設定する。
	 *
	 * @param version
	 *            version
	 * @return {@link UriageRirekiListDomainBuilder}
	 */
	public UriageRirekiListDomainBuilder withVersion(final Integer version) {
		addConfigurator(new BuilderConfigurator<UriageRirekiListDomainBuilder>() {
			@Override
			public void configure(UriageRirekiListDomainBuilder builder) {
				builder.version = version;
			}
		});
		return getThis();
	}

}