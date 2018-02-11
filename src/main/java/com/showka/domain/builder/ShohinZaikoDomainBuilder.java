package com.showka.domain.builder;

import java.util.List;

import com.showka.domain.BushoDomain;
import com.showka.domain.ShohinDomain;
import com.showka.domain.ShohinZaikoDomain;
import com.showka.domain.ShohinZaikoDomain.ShohinIdoOnDate;
import com.showka.value.TheDate;

public class ShohinZaikoDomainBuilder
		extends com.showka.domain.builder.DomainBuilderBase<ShohinZaikoDomain, ShohinZaikoDomainBuilder> {

	// private member
	/** busho */
	private BushoDomain busho;

	/** date */
	private TheDate date;

	/** shohin */
	private ShohinDomain shohin;

	/** number */
	private Integer kurikoshiNumber;

	/** shohinIdoList */
	private List<ShohinIdoOnDate> shohinIdoList;

	/** recordId */
	private String recordId;

	/** version */
	private Integer version;

	// protected method
	@Override
	protected void apply(ShohinZaikoDomain domain, ShohinZaikoDomainBuilder builder) {
		builder.withBusho(domain.getBusho());
		builder.withDate(domain.getDate());
		builder.withShohin(domain.getShohin());
		builder.withKurikoshiNumber(domain.getNumber());
		builder.withShohinIdoList(domain.getShohinIdoList());
		builder.withRecordId(domain.getRecordId());
		builder.withVersion(domain.getVersion());
	}

	@Override
	protected ShohinZaikoDomain createDomainObject() {
		ShohinZaikoDomain domain = new ShohinZaikoDomain(busho, date, shohin, kurikoshiNumber, shohinIdoList);
		domain.setRecordId(recordId);
		domain.setVersion(version);
		return domain;
	}

	@Override
	protected ShohinZaikoDomainBuilder getThis() {
		return this;
	}

	@Override
	protected ShohinZaikoDomainBuilder newInstance() {
		return new ShohinZaikoDomainBuilder();
	}

	// public method
	/**
	 * {@link ShohinZaikoDomain}に与えるbushoをこのビルダに設定する。
	 *
	 * @param busho
	 *            busho
	 * @return {@link ShohinZaikoDomainBuilder}
	 */
	public ShohinZaikoDomainBuilder withBusho(final BushoDomain busho) {
		addConfigurator(new BuilderConfigurator<ShohinZaikoDomainBuilder>() {
			@Override
			public void configure(ShohinZaikoDomainBuilder builder) {
				builder.busho = busho;
			}
		});
		return getThis();
	}

	/**
	 * {@link ShohinZaikoDomain}に与えるdateをこのビルダに設定する。
	 *
	 * @param date
	 *            date
	 * @return {@link ShohinZaikoDomainBuilder}
	 */
	public ShohinZaikoDomainBuilder withDate(final TheDate date) {
		addConfigurator(new BuilderConfigurator<ShohinZaikoDomainBuilder>() {
			@Override
			public void configure(ShohinZaikoDomainBuilder builder) {
				builder.date = date;
			}
		});
		return getThis();
	}

	/**
	 * {@link ShohinZaikoDomain}に与えるshohinをこのビルダに設定する。
	 *
	 * @param shohin
	 *            shohin
	 * @return {@link ShohinZaikoDomainBuilder}
	 */
	public ShohinZaikoDomainBuilder withShohin(final ShohinDomain shohin) {
		addConfigurator(new BuilderConfigurator<ShohinZaikoDomainBuilder>() {
			@Override
			public void configure(ShohinZaikoDomainBuilder builder) {
				builder.shohin = shohin;
			}
		});
		return getThis();
	}

	/**
	 * {@link ShohinZaikoDomain}に与えるzaikoをこのビルダに設定する。
	 *
	 * @param zaiko
	 *            zaiko
	 * @return {@link ShohinZaikoDomainBuilder}
	 */
	public ShohinZaikoDomainBuilder withKurikoshiNumber(final Integer number) {
		addConfigurator(new BuilderConfigurator<ShohinZaikoDomainBuilder>() {
			@Override
			public void configure(ShohinZaikoDomainBuilder builder) {
				builder.kurikoshiNumber = number;
			}
		});
		return getThis();
	}

	/**
	 * {@link ShohinZaikoDomain}に与えるshohinIdoListをこのビルダに設定する。
	 *
	 * @param shohinIdoList
	 *            shohinIdoList
	 * @return {@link ShohinZaikoDomainBuilder}
	 */
	public ShohinZaikoDomainBuilder withShohinIdoList(final List<ShohinIdoOnDate> shohinIdoList) {
		addConfigurator(new BuilderConfigurator<ShohinZaikoDomainBuilder>() {
			@Override
			public void configure(ShohinZaikoDomainBuilder builder) {
				builder.shohinIdoList = shohinIdoList;
			}
		});
		return getThis();
	}

	/**
	 * {@link ShohinZaikoDomain}に与えるrecordIdをこのビルダに設定する。
	 *
	 * @param recordId
	 *            recordId
	 * @return {@link ShohinZaikoDomainBuilder}
	 */
	public ShohinZaikoDomainBuilder withRecordId(final String recordId) {
		addConfigurator(new BuilderConfigurator<ShohinZaikoDomainBuilder>() {
			@Override
			public void configure(ShohinZaikoDomainBuilder builder) {
				builder.recordId = recordId;
			}
		});
		return getThis();
	}

	/**
	 * {@link ShohinZaikoDomain}に与えるversionをこのビルダに設定する。
	 *
	 * @param version
	 *            version
	 * @return {@link ShohinZaikoDomainBuilder}
	 */
	public ShohinZaikoDomainBuilder withVersion(final Integer version) {
		addConfigurator(new BuilderConfigurator<ShohinZaikoDomainBuilder>() {
			@Override
			public void configure(ShohinZaikoDomainBuilder builder) {
				builder.version = version;
			}
		});
		return getThis();
	}

}