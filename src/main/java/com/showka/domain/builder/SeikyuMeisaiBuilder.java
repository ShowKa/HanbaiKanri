package com.showka.domain.builder;

import com.showka.domain.u06.Urikake;
import com.showka.domain.u07.SeikyuMeisai;
import com.showka.value.AmountOfMoney;

public class SeikyuMeisaiBuilder
		extends com.showka.domain.builder.DomainBuilderBase<SeikyuMeisai, SeikyuMeisaiBuilder> {

	// private member
	/** urikake */
	private Urikake urikake;

	/** kingaku */
	private AmountOfMoney kingaku;

	/** recordId */
	private String recordId;

	/** version */
	private Integer version;

	// protected method
	@Override
	protected void apply(SeikyuMeisai domain, SeikyuMeisaiBuilder builder) {
		builder.withUrikake(domain.getUrikake());
		builder.withKingaku(domain.getKingaku());
		builder.withRecordId(domain.getRecordId());
		builder.withVersion(domain.getVersion());
	}

	@Override
	protected SeikyuMeisai createDomainObject() {
		SeikyuMeisai domain = new SeikyuMeisai(urikake, kingaku);
		domain.setRecordId(recordId);
		domain.setVersion(version);
		return domain;
	}

	@Override
	protected SeikyuMeisaiBuilder getThis() {
		return this;
	}

	@Override
	protected SeikyuMeisaiBuilder newInstance() {
		return new SeikyuMeisaiBuilder();
	}

	// public method
	/**
	 * {@link SeikyuMeisai}に与えるurikakeをこのビルダに設定する。
	 *
	 * @param urikake
	 *            urikake
	 * @return {@link SeikyuMeisaiBuilder}
	 */
	public SeikyuMeisaiBuilder withUrikake(final Urikake urikake) {
		addConfigurator(new BuilderConfigurator<SeikyuMeisaiBuilder>() {
			@Override
			public void configure(SeikyuMeisaiBuilder builder) {
				builder.urikake = urikake;
			}
		});
		return getThis();
	}

	/**
	 * {@link SeikyuMeisai}に与えるkingakuをこのビルダに設定する。
	 *
	 * @param kingaku
	 *            kingaku
	 * @return {@link SeikyuMeisaiBuilder}
	 */
	public SeikyuMeisaiBuilder withKingaku(final AmountOfMoney kingaku) {
		addConfigurator(new BuilderConfigurator<SeikyuMeisaiBuilder>() {
			@Override
			public void configure(SeikyuMeisaiBuilder builder) {
				builder.kingaku = kingaku;
			}
		});
		return getThis();
	}

	/**
	 * {@link SeikyuMeisai}に与えるrecordIdをこのビルダに設定する。
	 *
	 * @param recordId
	 *            recordId
	 * @return {@link SeikyuMeisaiBuilder}
	 */
	public SeikyuMeisaiBuilder withRecordId(final String recordId) {
		addConfigurator(new BuilderConfigurator<SeikyuMeisaiBuilder>() {
			@Override
			public void configure(SeikyuMeisaiBuilder builder) {
				builder.recordId = recordId;
			}
		});
		return getThis();
	}

	/**
	 * {@link SeikyuMeisai}に与えるversionをこのビルダに設定する。
	 *
	 * @param version
	 *            version
	 * @return {@link SeikyuMeisaiBuilder}
	 */
	public SeikyuMeisaiBuilder withVersion(final Integer version) {
		addConfigurator(new BuilderConfigurator<SeikyuMeisaiBuilder>() {
			@Override
			public void configure(SeikyuMeisaiBuilder builder) {
				builder.version = version;
			}
		});
		return getThis();
	}

}