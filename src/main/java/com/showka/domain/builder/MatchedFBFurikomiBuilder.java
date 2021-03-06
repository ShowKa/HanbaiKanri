package com.showka.domain.builder;

import com.showka.domain.u07.Seikyu;
import com.showka.domain.u08.MatchedFBFurikomi;
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

	// protected method
	@Override
	protected void apply(MatchedFBFurikomi domain, MatchedFBFurikomiBuilder builder) {
		builder.withFBFurikomiId(domain.getFBFurikomiId());
		builder.withTransmissionDate(domain.getTransmissionDate());
		builder.withKingaku(domain.getKingaku());
		builder.withSeikyu(domain.getSeikyu());
	}

	@Override
	protected MatchedFBFurikomi createDomainObject() {
		MatchedFBFurikomi domain = new MatchedFBFurikomi(fBFurikomiId, transmissionDate, kingaku, seikyu);
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
}