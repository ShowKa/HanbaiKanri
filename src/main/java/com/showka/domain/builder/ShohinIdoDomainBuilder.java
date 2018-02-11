package com.showka.domain.builder;

import java.util.List;

import com.showka.domain.BushoDomain;
import com.showka.domain.ShohinIdoDomain;
import com.showka.domain.ShohinIdoMeisaiDomain;
import com.showka.kubun.ShohinIdoKubun;
import com.showka.value.TheDate;
import com.showka.value.TheTimestamp;

public class ShohinIdoDomainBuilder
		extends com.showka.domain.builder.DomainBuilderBase<ShohinIdoDomain, ShohinIdoDomainBuilder> {

	// private member
	/** busho */
	private BushoDomain busho;

	/** date */
	private TheDate date;

	/** kubun */
	private ShohinIdoKubun kubun;

	/** timestamp */
	private TheTimestamp timestamp;

	/** meisai */
	private List<ShohinIdoMeisaiDomain> meisai;

	/** recordId */
	private String recordId;

	/** version */
	private Integer version;

	// protected method
	@Override
	protected void apply(ShohinIdoDomain domain, ShohinIdoDomainBuilder builder) {
		builder.withBusho(domain.getBusho());
		builder.withDate(domain.getDate());
		builder.withKubun(domain.getKubun());
		builder.withTimestamp(domain.getTimestamp());
		builder.withMeisai(domain.getMeisai());
		builder.withRecordId(domain.getRecordId());
		builder.withVersion(domain.getVersion());
	}

	@Override
	protected ShohinIdoDomain createDomainObject() {
		ShohinIdoDomain domain = new ShohinIdoDomain(busho, date, kubun, timestamp, meisai);
		domain.setRecordId(recordId);
		domain.setVersion(version);
		return domain;
	}

	@Override
	protected ShohinIdoDomainBuilder getThis() {
		return this;
	}

	@Override
	protected ShohinIdoDomainBuilder newInstance() {
		return new ShohinIdoDomainBuilder();
	}

	// public method
	/**
	 * {@link ShohinIdoDomain}に与えるbushoをこのビルダに設定する。
	 *
	 * @param busho
	 *            busho
	 * @return {@link ShohinIdoDomainBuilder}
	 */
	public ShohinIdoDomainBuilder withBusho(final BushoDomain busho) {
		addConfigurator(new BuilderConfigurator<ShohinIdoDomainBuilder>() {
			@Override
			public void configure(ShohinIdoDomainBuilder builder) {
				builder.busho = busho;
			}
		});
		return getThis();
	}

	/**
	 * {@link ShohinIdoDomain}に与えるdateをこのビルダに設定する。
	 *
	 * @param date
	 *            date
	 * @return {@link ShohinIdoDomainBuilder}
	 */
	public ShohinIdoDomainBuilder withDate(final TheDate date) {
		addConfigurator(new BuilderConfigurator<ShohinIdoDomainBuilder>() {
			@Override
			public void configure(ShohinIdoDomainBuilder builder) {
				builder.date = date;
			}
		});
		return getThis();
	}

	/**
	 * {@link ShohinIdoDomain}に与えるkubunをこのビルダに設定する。
	 *
	 * @param kubun
	 *            kubun
	 * @return {@link ShohinIdoDomainBuilder}
	 */
	public ShohinIdoDomainBuilder withKubun(final ShohinIdoKubun kubun) {
		addConfigurator(new BuilderConfigurator<ShohinIdoDomainBuilder>() {
			@Override
			public void configure(ShohinIdoDomainBuilder builder) {
				builder.kubun = kubun;
			}
		});
		return getThis();
	}

	/**
	 * {@link ShohinIdoDomain}に与えるtimestampをこのビルダに設定する。
	 *
	 * @param timestamp
	 *            timestamp
	 * @return {@link ShohinIdoDomainBuilder}
	 */
	public ShohinIdoDomainBuilder withTimestamp(final TheTimestamp timestamp) {
		addConfigurator(new BuilderConfigurator<ShohinIdoDomainBuilder>() {
			@Override
			public void configure(ShohinIdoDomainBuilder builder) {
				builder.timestamp = timestamp;
			}
		});
		return getThis();
	}

	/**
	 * {@link ShohinIdoDomain}に与えるmeisaiをこのビルダに設定する。
	 *
	 * @param meisai
	 *            meisai
	 * @return {@link ShohinIdoDomainBuilder}
	 */
	public ShohinIdoDomainBuilder withMeisai(final List<ShohinIdoMeisaiDomain> meisai) {
		addConfigurator(new BuilderConfigurator<ShohinIdoDomainBuilder>() {
			@Override
			public void configure(ShohinIdoDomainBuilder builder) {
				builder.meisai = meisai;
			}
		});
		return getThis();
	}

	/**
	 * {@link ShohinIdoDomain}に与えるrecordIdをこのビルダに設定する。
	 *
	 * @param recordId
	 *            recordId
	 * @return {@link ShohinIdoDomainBuilder}
	 */
	public ShohinIdoDomainBuilder withRecordId(final String recordId) {
		addConfigurator(new BuilderConfigurator<ShohinIdoDomainBuilder>() {
			@Override
			public void configure(ShohinIdoDomainBuilder builder) {
				builder.recordId = recordId;
			}
		});
		return getThis();
	}

	/**
	 * {@link ShohinIdoDomain}に与えるversionをこのビルダに設定する。
	 *
	 * @param version
	 *            version
	 * @return {@link ShohinIdoDomainBuilder}
	 */
	public ShohinIdoDomainBuilder withVersion(final Integer version) {
		addConfigurator(new BuilderConfigurator<ShohinIdoDomainBuilder>() {
			@Override
			public void configure(ShohinIdoDomainBuilder builder) {
				builder.version = version;
			}
		});
		return getThis();
	}

}