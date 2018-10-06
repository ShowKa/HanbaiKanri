package com.showka.domain.builder;

import java.util.Set;

import com.showka.domain.u06.Urikake;
import com.showka.domain.u06.UrikakeKeshikomi;
import com.showka.domain.u08.Keshikomi;

public class UrikakeKeshikomiBuilder
		extends com.showka.domain.builder.DomainBuilderBase<UrikakeKeshikomi, UrikakeKeshikomiBuilder> {

	// private member
	/** urikake */
	private Urikake urikake;

	/** keshikomiList */
	private Set<Keshikomi> keshikomiSet;

	// protected method
	@Override
	protected void apply(UrikakeKeshikomi domain, UrikakeKeshikomiBuilder builder) {
		builder.withUrikake(domain.getUrikake());
		builder.withKeshikomiSet(domain.getKeshikomiSet());
	}

	@Override
	protected UrikakeKeshikomi createDomainObject() {
		UrikakeKeshikomi domain = new UrikakeKeshikomi(urikake, keshikomiSet);
		return domain;
	}

	@Override
	protected UrikakeKeshikomiBuilder getThis() {
		return this;
	}

	@Override
	protected UrikakeKeshikomiBuilder newInstance() {
		return new UrikakeKeshikomiBuilder();
	}

	// public method
	/**
	 * {@link UrikakeKeshikomi}に与えるurikakeをこのビルダに設定する。
	 *
	 * @param urikake
	 *            urikake
	 * @return {@link UrikakeKeshikomiBuilder}
	 */
	public UrikakeKeshikomiBuilder withUrikake(final Urikake urikake) {
		addConfigurator(new BuilderConfigurator<UrikakeKeshikomiBuilder>() {
			@Override
			public void configure(UrikakeKeshikomiBuilder builder) {
				builder.urikake = urikake;
			}
		});
		return getThis();
	}

	/**
	 * {@link UrikakeKeshikomi}に与えるkeshikomiListをこのビルダに設定する。
	 *
	 * @param keshikomiList
	 *            keshikomiList
	 * @return {@link UrikakeKeshikomiBuilder}
	 */
	public UrikakeKeshikomiBuilder withKeshikomiSet(final Set<Keshikomi> keshikomiList) {
		addConfigurator(new BuilderConfigurator<UrikakeKeshikomiBuilder>() {
			@Override
			public void configure(UrikakeKeshikomiBuilder builder) {
				builder.keshikomiSet = keshikomiList;
			}
		});
		return getThis();
	}

}