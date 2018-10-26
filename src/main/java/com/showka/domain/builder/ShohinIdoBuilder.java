package com.showka.domain.builder;

import java.util.List;

import com.showka.domain.u11.ShohinIdo;
import com.showka.domain.u11.ShohinIdoMeisai;
import com.showka.domain.z00.Busho;
import com.showka.kubun.ShohinIdoKubun;
import com.showka.value.EigyoDate;
import com.showka.value.TheTimestamp;

public class ShohinIdoBuilder extends com.showka.domain.builder.DomainBuilderBase<ShohinIdo, ShohinIdoBuilder> {

	// private member
	/** busho */
	private Busho busho;

	/** date */
	private EigyoDate date;

	/** kubun */
	private ShohinIdoKubun kubun;

	/** timestamp */
	private TheTimestamp timestamp;

	/** meisai */
	private List<ShohinIdoMeisai> meisai;

	/** recordId */
	private String recordId;

	/** version */
	private Integer version;

	// protected method
	@Override
	protected void apply(ShohinIdo domain, ShohinIdoBuilder builder) {
		builder.withBusho(domain.getBusho());
		builder.withDate(domain.getDate());
		builder.withKubun(domain.getKubun());
		builder.withTimestamp(domain.getTimestamp());
		builder.withMeisai(domain.getMeisai());
		builder.withRecordId(domain.getRecordId());
		builder.withVersion(domain.getVersion());
	}

	@Override
	protected ShohinIdo createDomainObject() {
		ShohinIdo domain = new ShohinIdo(busho, date, kubun, timestamp, meisai);
		domain.setRecordId(recordId);
		domain.setVersion(version);
		return domain;
	}

	@Override
	protected ShohinIdoBuilder getThis() {
		return this;
	}

	@Override
	protected ShohinIdoBuilder newInstance() {
		return new ShohinIdoBuilder();
	}

	// public method
	/**
	 * {@link ShohinIdo}に与えるbushoをこのビルダに設定する。
	 *
	 * @param busho
	 *            busho
	 * @return {@link ShohinIdoBuilder}
	 */
	public ShohinIdoBuilder withBusho(final Busho busho) {
		addConfigurator(new BuilderConfigurator<ShohinIdoBuilder>() {
			@Override
			public void configure(ShohinIdoBuilder builder) {
				builder.busho = busho;
			}
		});
		return getThis();
	}

	/**
	 * {@link ShohinIdo}に与えるdateをこのビルダに設定する。
	 *
	 * @param date
	 *            date
	 * @return {@link ShohinIdoBuilder}
	 */
	public ShohinIdoBuilder withDate(final EigyoDate date) {
		addConfigurator(new BuilderConfigurator<ShohinIdoBuilder>() {
			@Override
			public void configure(ShohinIdoBuilder builder) {
				builder.date = date;
			}
		});
		return getThis();
	}

	/**
	 * {@link ShohinIdo}に与えるkubunをこのビルダに設定する。
	 *
	 * @param kubun
	 *            kubun
	 * @return {@link ShohinIdoBuilder}
	 */
	public ShohinIdoBuilder withKubun(final ShohinIdoKubun kubun) {
		addConfigurator(new BuilderConfigurator<ShohinIdoBuilder>() {
			@Override
			public void configure(ShohinIdoBuilder builder) {
				builder.kubun = kubun;
			}
		});
		return getThis();
	}

	/**
	 * {@link ShohinIdo}に与えるtimestampをこのビルダに設定する。
	 *
	 * @param timestamp
	 *            timestamp
	 * @return {@link ShohinIdoBuilder}
	 */
	public ShohinIdoBuilder withTimestamp(final TheTimestamp timestamp) {
		addConfigurator(new BuilderConfigurator<ShohinIdoBuilder>() {
			@Override
			public void configure(ShohinIdoBuilder builder) {
				builder.timestamp = timestamp;
			}
		});
		return getThis();
	}

	/**
	 * {@link ShohinIdo}に与えるmeisaiをこのビルダに設定する。
	 *
	 * @param meisai
	 *            meisai
	 * @return {@link ShohinIdoBuilder}
	 */
	public ShohinIdoBuilder withMeisai(final List<ShohinIdoMeisai> meisai) {
		addConfigurator(new BuilderConfigurator<ShohinIdoBuilder>() {
			@Override
			public void configure(ShohinIdoBuilder builder) {
				builder.meisai = meisai;
			}
		});
		return getThis();
	}

	/**
	 * {@link ShohinIdo}に与えるrecordIdをこのビルダに設定する。
	 *
	 * @param recordId
	 *            recordId
	 * @return {@link ShohinIdoBuilder}
	 */
	public ShohinIdoBuilder withRecordId(final String recordId) {
		addConfigurator(new BuilderConfigurator<ShohinIdoBuilder>() {
			@Override
			public void configure(ShohinIdoBuilder builder) {
				builder.recordId = recordId;
			}
		});
		return getThis();
	}

	/**
	 * {@link ShohinIdo}に与えるversionをこのビルダに設定する。
	 *
	 * @param version
	 *            version
	 * @return {@link ShohinIdoBuilder}
	 */
	public ShohinIdoBuilder withVersion(final Integer version) {
		addConfigurator(new BuilderConfigurator<ShohinIdoBuilder>() {
			@Override
			public void configure(ShohinIdoBuilder builder) {
				builder.version = version;
			}
		});
		return getThis();
	}

}