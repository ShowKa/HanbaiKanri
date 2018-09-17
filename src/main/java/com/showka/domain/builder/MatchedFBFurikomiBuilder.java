package com.showka.domain.builder;

import com.showka.domain.MatchedFBFurikomi;
import com.showka.domain.Seikyu;
import com.showka.value.AmountOfMoney;
import com.showka.value.TheDate;

public class MatchedFBFurikomiBuilder
		extends com.showka.domain.builder.DomainBuilderBase<MatchedFBFurikomi, MatchedFBFurikomiBuilder> {

	// private member
	/** fBFurikomiId */
	private String fBFurikomiId;

	/** transmissionDate */
	private TheDate transmissionDate;

	/** kingaku */
	private AmountOfMoney kingaku;

	/** seikyu */
	private Seikyu seikyu;

	/** recordId */
	private String recordId;

	/** version */
	private Integer version;

	// protected method
	@Override
	protected void apply(MatchedFBFurikomi domain, MatchedFBFurikomiBuilder builder) {
		builder.withFBFurikomiId(domain.getFBFurikomiId());
		builder.withTransmissionDate(domain.getTransmissionDate());
		builder.withKingaku(domain.getKingaku());
		builder.withSeikyu(domain.getSeikyu());
		builder.withRecordId(domain.getRecordId());
		builder.withVersion(domain.getVersion());
	}

	@Override
	protected MatchedFBFurikomi createDomainObject() {
		MatchedFBFurikomi domain = new MatchedFBFurikomi(fBFurikomiId, transmissionDate, kingaku, seikyu);
		domain.setRecordId(recordId);
		domain.setVersion(version);
		return domain;
	}

	@Override
	protected MatchedFBFurikomiBuilder getThis() {
		return this;
	}

	@Override
	protected MatchedFBFurikomiBuilder newInstance() {
		return new MatchedFBFurikomiBuilder();
	}

	// public method
	/**
	 * {@link MatchedFBFurikomi}に与えるfBFurikomiIdをこのビルダに設定する。
	 *
	 * @param fBFurikomiId
	 *            fBFurikomiId
	 * @return {@link MatchedFBFurikomiBuilder}
	 */
	public MatchedFBFurikomiBuilder withFBFurikomiId(final String fBFurikomiId) {
		addConfigurator(new BuilderConfigurator<MatchedFBFurikomiBuilder>() {
			@Override
			public void configure(MatchedFBFurikomiBuilder builder) {
				builder.fBFurikomiId = fBFurikomiId;
			}
		});
		return getThis();
	}

	/**
	 * {@link MatchedFBFurikomi}に与えるtransmissionDateをこのビルダに設定する。
	 *
	 * @param transmissionDate
	 *            transmissionDate
	 * @return {@link MatchedFBFurikomiBuilder}
	 */
	public MatchedFBFurikomiBuilder withTransmissionDate(final TheDate transmissionDate) {
		addConfigurator(new BuilderConfigurator<MatchedFBFurikomiBuilder>() {
			@Override
			public void configure(MatchedFBFurikomiBuilder builder) {
				builder.transmissionDate = transmissionDate;
			}
		});
		return getThis();
	}

	/**
	 * {@link MatchedFBFurikomi}に与えるkingakuをこのビルダに設定する。
	 *
	 * @param kingaku
	 *            kingaku
	 * @return {@link MatchedFBFurikomiBuilder}
	 */
	public MatchedFBFurikomiBuilder withKingaku(final AmountOfMoney kingaku) {
		addConfigurator(new BuilderConfigurator<MatchedFBFurikomiBuilder>() {
			@Override
			public void configure(MatchedFBFurikomiBuilder builder) {
				builder.kingaku = kingaku;
			}
		});
		return getThis();
	}

	/**
	 * {@link MatchedFBFurikomi}に与えるseikyuをこのビルダに設定する。
	 *
	 * @param seikyu
	 *            seikyu
	 * @return {@link MatchedFBFurikomiBuilder}
	 */
	public MatchedFBFurikomiBuilder withSeikyu(final Seikyu seikyu) {
		addConfigurator(new BuilderConfigurator<MatchedFBFurikomiBuilder>() {
			@Override
			public void configure(MatchedFBFurikomiBuilder builder) {
				builder.seikyu = seikyu;
			}
		});
		return getThis();
	}

	/**
	 * {@link MatchedFBFurikomi}に与えるrecordIdをこのビルダに設定する。
	 *
	 * @param recordId
	 *            recordId
	 * @return {@link MatchedFBFurikomiBuilder}
	 */
	public MatchedFBFurikomiBuilder withRecordId(final String recordId) {
		addConfigurator(new BuilderConfigurator<MatchedFBFurikomiBuilder>() {
			@Override
			public void configure(MatchedFBFurikomiBuilder builder) {
				builder.recordId = recordId;
			}
		});
		return getThis();
	}

	/**
	 * {@link MatchedFBFurikomi}に与えるversionをこのビルダに設定する。
	 *
	 * @param version
	 *            version
	 * @return {@link MatchedFBFurikomiBuilder}
	 */
	public MatchedFBFurikomiBuilder withVersion(final Integer version) {
		addConfigurator(new BuilderConfigurator<MatchedFBFurikomiBuilder>() {
			@Override
			public void configure(MatchedFBFurikomiBuilder builder) {
				builder.version = version;
			}
		});
		return getThis();
	}

}