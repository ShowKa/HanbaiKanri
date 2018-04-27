package com.showka.domain.builder;

import java.util.List;

import com.showka.domain.Keshikomi;
import com.showka.domain.Urikake;
import com.showka.domain.UrikakeKeshikomi;

public class UrikakeKeshikomiBuilder
		extends com.showka.domain.builder.DomainBuilderBase<UrikakeKeshikomi, UrikakeKeshikomiBuilder> {

	// private member
	/** urikake */
	private Urikake urikake;

	/** keshikomiList */
	private List<Keshikomi> keshikomiList;

	// protected method
	@Override
	protected void apply(UrikakeKeshikomi domain, UrikakeKeshikomiBuilder builder) {
		builder.withUrikake(domain.getUrikake());
		builder.withKeshikomiList(domain.getKeshikomiList());
	}

	@Override
	protected UrikakeKeshikomi createDomainObject() {
		UrikakeKeshikomi domain = new UrikakeKeshikomi(urikake, keshikomiList);
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
	public UrikakeKeshikomiBuilder withKeshikomiList(final List<Keshikomi> keshikomiList) {
		addConfigurator(new BuilderConfigurator<UrikakeKeshikomiBuilder>() {
			@Override
			public void configure(UrikakeKeshikomiBuilder builder) {
				builder.keshikomiList = keshikomiList;
			}
		});
		return getThis();
	}

}