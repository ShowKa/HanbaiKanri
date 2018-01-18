package com.showka.domain.builder;

import java.util.Optional;
import java.util.Set;

import com.showka.domain.UriageRirekiDomain;
import com.showka.domain.UriageRirekiListDomain;
import com.showka.value.TheDate;

public class UriageRirekiListDomainBuilder
		extends com.showka.domain.builder.DomainBuilderBase<UriageRirekiListDomain, UriageRirekiListDomainBuilder> {

	// private member
	/** list */
	private Set<UriageRirekiDomain> list;

	/** cancelKeijoDate */
	private Optional<TheDate> cancelKeijoDate;

	/** recordId */
	private String recordId;

	/** version */
	private Integer version;

	// protected method
	@Override
	protected void apply(UriageRirekiListDomain domain, UriageRirekiListDomainBuilder builder) {
		builder.withList(domain.getList());
		builder.withCancelKeijoDate(domain.getCancelKeijoDate());
		builder.withRecordId(domain.getRecordId());
		builder.withVersion(domain.getVersion());
	}

	@Override
	protected UriageRirekiListDomain createDomainObject() {
		UriageRirekiListDomain domain = new UriageRirekiListDomain(list, cancelKeijoDate);
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
	 * {@link UriageRirekiListDomain}に与えるlistをこのビルダに設定する。
	 *
	 * @param list
	 *            list
	 * @return {@link UriageRirekiListDomainBuilder}
	 */
	public UriageRirekiListDomainBuilder withList(final Set<UriageRirekiDomain> list) {
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

	public UriageRirekiListDomainBuilder withCancelKeijoDate(final TheDate cancelKeijoDate) {
		addConfigurator(new BuilderConfigurator<UriageRirekiListDomainBuilder>() {
			@Override
			public void configure(UriageRirekiListDomainBuilder builder) {
				builder.cancelKeijoDate = Optional.of(cancelKeijoDate);
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