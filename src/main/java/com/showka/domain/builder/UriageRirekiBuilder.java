package com.showka.domain.builder;

import java.util.List;
import java.util.Optional;

import com.showka.domain.Uriage;
import com.showka.domain.UriageRireki;
import com.showka.value.TheDate;

public class UriageRirekiBuilder
		extends com.showka.domain.builder.DomainBuilderBase<UriageRireki, UriageRirekiBuilder> {

	// private member
	/** uriageId */
	private String uriageId;

	/** list */
	private List<Uriage> list;

	/** cancelKeijoDate */
	private Optional<TheDate> cancelKeijoDate;

	/** recordId */
	private String recordId;

	/** version */
	private Integer version;

	// protected method
	@Override
	protected void apply(UriageRireki domain, UriageRirekiBuilder builder) {
		builder.withUriageId(domain.getUriageId());
		builder.withList(domain.getList());
		builder.withCancelKeijoDate(domain.getCancelKeijoDate());
		builder.withRecordId(domain.getRecordId());
		builder.withVersion(domain.getVersion());
	}

	@Override
	protected UriageRireki createDomainObject() {
		UriageRireki domain = new UriageRireki(uriageId, list, cancelKeijoDate);
		domain.setRecordId(recordId);
		domain.setVersion(version);
		return domain;
	}

	@Override
	protected UriageRirekiBuilder getThis() {
		return this;
	}

	@Override
	protected UriageRirekiBuilder newInstance() {
		return new UriageRirekiBuilder();
	}

	// public method
	/**
	 * {@link UriageRireki}に与えるuriageIdをこのビルダに設定する。
	 *
	 * @param uriageId
	 *            uriageId
	 * @return {@link UriageRirekiBuilder}
	 */
	public UriageRirekiBuilder withUriageId(final String uriageId) {
		addConfigurator(new BuilderConfigurator<UriageRirekiBuilder>() {
			@Override
			public void configure(UriageRirekiBuilder builder) {
				builder.uriageId = uriageId;
			}
		});
		return getThis();
	}

	/**
	 * {@link UriageRireki}に与えるlistをこのビルダに設定する。
	 *
	 * @param list
	 *            list
	 * @return {@link UriageRirekiBuilder}
	 */
	public UriageRirekiBuilder withList(final List<Uriage> list) {
		addConfigurator(new BuilderConfigurator<UriageRirekiBuilder>() {
			@Override
			public void configure(UriageRirekiBuilder builder) {
				builder.list = list;
			}
		});
		return getThis();
	}

	/**
	 * {@link UriageRireki}に与えるcancelKeijoDateをこのビルダに設定する。
	 *
	 * @param cancelKeijoDate
	 *            cancelKeijoDate
	 * @return {@link UriageRirekiBuilder}
	 */
	public UriageRirekiBuilder withCancelKeijoDate(final Optional<TheDate> cancelKeijoDate) {
		addConfigurator(new BuilderConfigurator<UriageRirekiBuilder>() {
			@Override
			public void configure(UriageRirekiBuilder builder) {
				builder.cancelKeijoDate = cancelKeijoDate;
			}
		});
		return getThis();
	}

	/**
	 * {@link UriageRireki}に与えるrecordIdをこのビルダに設定する。
	 *
	 * @param recordId
	 *            recordId
	 * @return {@link UriageRirekiBuilder}
	 */
	public UriageRirekiBuilder withRecordId(final String recordId) {
		addConfigurator(new BuilderConfigurator<UriageRirekiBuilder>() {
			@Override
			public void configure(UriageRirekiBuilder builder) {
				builder.recordId = recordId;
			}
		});
		return getThis();
	}

	/**
	 * {@link UriageRireki}に与えるversionをこのビルダに設定する。
	 *
	 * @param version
	 *            version
	 * @return {@link UriageRirekiBuilder}
	 */
	public UriageRirekiBuilder withVersion(final Integer version) {
		addConfigurator(new BuilderConfigurator<UriageRirekiBuilder>() {
			@Override
			public void configure(UriageRirekiBuilder builder) {
				builder.version = version;
			}
		});
		return getThis();
	}

}