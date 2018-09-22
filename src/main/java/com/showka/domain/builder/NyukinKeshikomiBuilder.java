package com.showka.domain.builder;

import java.util.Set;

import com.showka.domain.Keshikomi;
import com.showka.domain.Nyukin;
import com.showka.domain.NyukinKeshikomi;

public class NyukinKeshikomiBuilder
		extends com.showka.domain.builder.DomainBuilderBase<NyukinKeshikomi, NyukinKeshikomiBuilder> {

	// private member
	/** nyukin */
	private Nyukin nyukin;

	/** keshikomiList */
	private Set<Keshikomi> keshikomiSet;

	// protected method
	@Override
	protected void apply(NyukinKeshikomi domain, NyukinKeshikomiBuilder builder) {
		builder.withNyukin(domain.getNyukin());
		builder.withKeshikomiSet(domain.getKeshikomiSet());
	}

	@Override
	protected NyukinKeshikomi createDomainObject() {
		NyukinKeshikomi domain = new NyukinKeshikomi(nyukin, keshikomiSet);
		return domain;
	}

	@Override
	protected NyukinKeshikomiBuilder getThis() {
		return this;
	}

	@Override
	protected NyukinKeshikomiBuilder newInstance() {
		return new NyukinKeshikomiBuilder();
	}

	// public method
	/**
	 * {@link NyukinKeshikomi}に与えるnyukinをこのビルダに設定する。
	 *
	 * @param nyukin
	 *            nyukin
	 * @return {@link NyukinKeshikomiBuilder}
	 */
	public NyukinKeshikomiBuilder withNyukin(final Nyukin nyukin) {
		addConfigurator(new BuilderConfigurator<NyukinKeshikomiBuilder>() {
			@Override
			public void configure(NyukinKeshikomiBuilder builder) {
				builder.nyukin = nyukin;
			}
		});
		return getThis();
	}

	/**
	 * {@link NyukinKeshikomi}に与えるkeshikomiListをこのビルダに設定する。
	 *
	 * @param keshikomiList
	 *            keshikomiList
	 * @return {@link NyukinKeshikomiBuilder}
	 */
	public NyukinKeshikomiBuilder withKeshikomiSet(final Set<Keshikomi> keshikomiList) {
		addConfigurator(new BuilderConfigurator<NyukinKeshikomiBuilder>() {
			@Override
			public void configure(NyukinKeshikomiBuilder builder) {
				builder.keshikomiSet = keshikomiList;
			}
		});
		return getThis();
	}
}