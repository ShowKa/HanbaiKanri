package com.showka.domain.builder;

import com.showka.domain.Keshikomi;
import com.showka.value.AmountOfMoney;
import com.showka.value.EigyoDate;

public class KeshikomiBuilder extends com.showka.domain.builder.DomainBuilderBase<Keshikomi, KeshikomiBuilder> {

	// private member
	/** date */
	private EigyoDate date;

	/** kingaku */
	private AmountOfMoney kingaku;

	/** recordId */
	private String recordId;

	/** version */
	private Integer version;

	// protected method
	@Override
	protected void apply(Keshikomi domain, KeshikomiBuilder builder) {
		builder.withDate(domain.getDate());
		builder.withKingaku(domain.getKingaku());
		builder.withRecordId(domain.getRecordId());
		builder.withVersion(domain.getVersion());
	}

	@Override
	protected Keshikomi createDomainObject() {
		Keshikomi domain = new Keshikomi(date, kingaku);
		domain.setRecordId(recordId);
		domain.setVersion(version);
		return domain;
	}

	@Override
	protected KeshikomiBuilder getThis() {
		return this;
	}

	@Override
	protected KeshikomiBuilder newInstance() {
		return new KeshikomiBuilder();
	}

	// public method
	/**
	 * {@link Keshikomi}に与えるdateをこのビルダに設定する。
	 *
	 * @param date
	 *            date
	 * @return {@link KeshikomiBuilder}
	 */
	public KeshikomiBuilder withDate(final EigyoDate date) {
		addConfigurator(new BuilderConfigurator<KeshikomiBuilder>() {
			@Override
			public void configure(KeshikomiBuilder builder) {
				builder.date = date;
			}
		});
		return getThis();
	}

	/**
	 * {@link Keshikomi}に与えるkingakuをこのビルダに設定する。
	 *
	 * @param kingaku
	 *            kingaku
	 * @return {@link KeshikomiBuilder}
	 */
	public KeshikomiBuilder withKingaku(final AmountOfMoney kingaku) {
		addConfigurator(new BuilderConfigurator<KeshikomiBuilder>() {
			@Override
			public void configure(KeshikomiBuilder builder) {
				builder.kingaku = kingaku;
			}
		});
		return getThis();
	}

	/**
	 * {@link Keshikomi}に与えるkingakuをこのビルダに設定する。
	 *
	 * @param kingaku
	 *            kingaku
	 * @return {@link KeshikomiBuilder}
	 */
	public KeshikomiBuilder withKingaku(final Integer kingaku) {
		addConfigurator(new BuilderConfigurator<KeshikomiBuilder>() {
			@Override
			public void configure(KeshikomiBuilder builder) {
				builder.kingaku = new AmountOfMoney(kingaku);
			}
		});
		return getThis();
	}

	/**
	 * {@link Keshikomi}に与えるrecordIdをこのビルダに設定する。
	 *
	 * @param recordId
	 *            recordId
	 * @return {@link KeshikomiBuilder}
	 */
	public KeshikomiBuilder withRecordId(final String recordId) {
		addConfigurator(new BuilderConfigurator<KeshikomiBuilder>() {
			@Override
			public void configure(KeshikomiBuilder builder) {
				builder.recordId = recordId;
			}
		});
		return getThis();
	}

	/**
	 * {@link Keshikomi}に与えるversionをこのビルダに設定する。
	 *
	 * @param version
	 *            version
	 * @return {@link KeshikomiBuilder}
	 */
	public KeshikomiBuilder withVersion(final Integer version) {
		addConfigurator(new BuilderConfigurator<KeshikomiBuilder>() {
			@Override
			public void configure(KeshikomiBuilder builder) {
				builder.version = version;
			}
		});
		return getThis();
	}

}