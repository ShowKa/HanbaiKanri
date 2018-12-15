package com.showka.domain.builder;

import java.util.List;

import com.showka.domain.u01.Kokyaku;
import com.showka.domain.u06.KokyakuUrikake;
import com.showka.domain.u06.Urikake;

public class KokyakuUrikakeBuilder
		extends com.showka.domain.builder.DomainBuilderBase<KokyakuUrikake, KokyakuUrikakeBuilder> {

	// private member
	/** kokyaku */
	private Kokyaku kokyaku;

	/** urikakeList */
	private List<Urikake> urikakeList;

	/** recordId */
	private String recordId;

	/** version */
	private Integer version;

	// protected method
	@Override
	protected void apply(KokyakuUrikake domain, KokyakuUrikakeBuilder builder) {
		builder.withKokyaku(domain.getKokyaku());
		builder.withUrikakeList(domain.getUrikakeList());
	}

	@Override
	protected KokyakuUrikake createDomainObject() {
		KokyakuUrikake domain = new KokyakuUrikake(kokyaku, urikakeList);
		return domain;
	}

	@Override
	protected KokyakuUrikakeBuilder getThis() {
		return this;
	}

	@Override
	protected KokyakuUrikakeBuilder newInstance() {
		return new KokyakuUrikakeBuilder();
	}

	// public method
	/**
	 * {@link KokyakuUrikake}に与えるkokyakuをこのビルダに設定する。
	 *
	 * @param kokyaku
	 *            kokyaku
	 * @return {@link KokyakuUrikakeBuilder}
	 */
	public KokyakuUrikakeBuilder withKokyaku(final Kokyaku kokyaku) {
		addConfigurator(new BuilderConfigurator<KokyakuUrikakeBuilder>() {
			@Override
			public void configure(KokyakuUrikakeBuilder builder) {
				builder.kokyaku = kokyaku;
			}
		});
		return getThis();
	}

	/**
	 * {@link KokyakuUrikake}に与えるurikakeListをこのビルダに設定する。
	 *
	 * @param urikakeList
	 *            urikakeList
	 * @return {@link KokyakuUrikakeBuilder}
	 */
	public KokyakuUrikakeBuilder withUrikakeList(final List<Urikake> urikakeList) {
		addConfigurator(new BuilderConfigurator<KokyakuUrikakeBuilder>() {
			@Override
			public void configure(KokyakuUrikakeBuilder builder) {
				builder.urikakeList = urikakeList;
			}
		});
		return getThis();
	}

}