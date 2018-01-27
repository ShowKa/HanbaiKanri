package com.showka.domain.builder;

import java.util.List;
import java.util.Optional;

import com.showka.domain.UriageDomain;
import com.showka.domain.UriageRirekiListDomain;
import com.showka.value.TheDate;

public class UriageRirekiListDomainBuilder
		extends com.showka.domain.builder.DomainBuilderBase<UriageRirekiListDomain, UriageRirekiListDomainBuilder> {

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
	protected void apply(UriageRirekiListDomain domain, UriageRirekiListDomainBuilder builder) {
		builder.withUriageId(domain.getUriageId());
		builder.withList(domain.getList());
		builder.withCancelKeijoDate(domain.getCancelKeijoDate());
		builder.withRecordId(domain.getRecordId());
		builder.withVersion(domain.getVersion());
	}

	@Override
	protected UriageRirekiListDomain createDomainObject() {
		UriageRirekiListDomain domain = new UriageRirekiListDomain(uriageId, list, cancelKeijoDate);
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
	 * {@link UriageRirekiListDomain}に与えるuriageIdをこのビルダに設定する。
	 *
	 * @param uriageId
	 *            uriageId
	 * @return {@link UriageRirekiListDomainBuilder}
	 */
	public UriageRirekiListDomainBuilder withUriageId(final String uriageId) {
		addConfigurator(new BuilderConfigurator<UriageRirekiListDomainBuilder>() {
			@Override
			public void configure(UriageRirekiListDomainBuilder builder) {
				builder.uriageId = uriageId;
			}
		});
		return getThis();
	}

	/**
	 * {@link UriageRirekiListDomain}に与えるlistをこのビルダに設定する。
	 *
	 * @param list
	 *            list
	 * @return {@link UriageRirekiListDomainBuilder}
	 */
	public UriageRirekiListDomainBuilder withList(final List<UriageDomain> list) {
		addConfigurator(new BuilderConfigurator<UriageRirekiListDomainBuilder>() {
			@Override
			public void configure(UriageRirekiListDomainBuilder builder) {
				builder.list = list;
			}
		});
		return getThis();
	}

	/**
	 * {@link UriageRirekiListDomain}に与えるcancelKeijoDateをこのビルダに設定する。
	 *
	 * @param cancelKeijoDate
	 *            cancelKeijoDate
	 * @return {@link UriageRirekiListDomainBuilder}
	 */
	public UriageRirekiListDomainBuilder withCancelKeijoDate(final Optional<TheDate> cancelKeijoDate) {
		addConfigurator(new BuilderConfigurator<UriageRirekiListDomainBuilder>() {
			@Override
			public void configure(UriageRirekiListDomainBuilder builder) {
				builder.cancelKeijoDate = cancelKeijoDate;
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