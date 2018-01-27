package com.showka.domain.builder;

import java.util.List;
import java.util.Optional;

import com.showka.domain.UriageDomain;
import com.showka.domain.UriageRirekiDomain;
import com.showka.value.TheDate;

public class UriageRirekiDomainBuilder
		extends com.showka.domain.builder.DomainBuilderBase<UriageRirekiDomain, UriageRirekiDomainBuilder> {

	// private member
	/** uriageId */
	private String uriageId;

	/** list */
	private List<UriageDomain> list;

	/** cancelKeijoDate */
	private Optional<TheDate> cancelKeijoDate;

	/** recordId */
	private String recordId;

	/** version */
	private Integer version;

	// protected method
	@Override
	protected void apply(UriageRirekiDomain domain, UriageRirekiDomainBuilder builder) {
		builder.withUriageId(domain.getUriageId());
		builder.withList(domain.getList());
		builder.withCancelKeijoDate(domain.getCancelKeijoDate());
		builder.withRecordId(domain.getRecordId());
		builder.withVersion(domain.getVersion());
	}

	@Override
	protected UriageRirekiDomain createDomainObject() {
		UriageRirekiDomain domain = new UriageRirekiDomain(uriageId, list, cancelKeijoDate);
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
	 * {@link UriageRirekiDomain}に与えるuriageIdをこのビルダに設定する。
	 *
	 * @param uriageId
	 *            uriageId
	 * @return {@link UriageRirekiDomainBuilder}
	 */
	public UriageRirekiDomainBuilder withUriageId(final String uriageId) {
		addConfigurator(new BuilderConfigurator<UriageRirekiDomainBuilder>() {
			@Override
			public void configure(UriageRirekiDomainBuilder builder) {
				builder.uriageId = uriageId;
			}
		});
		return getThis();
	}

	/**
	 * {@link UriageRirekiDomain}に与えるlistをこのビルダに設定する。
	 *
	 * @param list
	 *            list
	 * @return {@link UriageRirekiDomainBuilder}
	 */
	public UriageRirekiDomainBuilder withList(final List<UriageDomain> list) {
		addConfigurator(new BuilderConfigurator<UriageRirekiDomainBuilder>() {
			@Override
			public void configure(UriageRirekiDomainBuilder builder) {
				builder.list = list;
			}
		});
		return getThis();
	}

	/**
	 * {@link UriageRirekiDomain}に与えるcancelKeijoDateをこのビルダに設定する。
	 *
	 * @param cancelKeijoDate
	 *            cancelKeijoDate
	 * @return {@link UriageRirekiDomainBuilder}
	 */
	public UriageRirekiDomainBuilder withCancelKeijoDate(final Optional<TheDate> cancelKeijoDate) {
		addConfigurator(new BuilderConfigurator<UriageRirekiDomainBuilder>() {
			@Override
			public void configure(UriageRirekiDomainBuilder builder) {
				builder.cancelKeijoDate = cancelKeijoDate;
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