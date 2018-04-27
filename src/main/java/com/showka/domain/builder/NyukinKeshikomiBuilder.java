package com.showka.domain.builder;

import java.util.List;

import com.showka.domain.Keshikomi;
import com.showka.domain.Nyukin;
import com.showka.domain.NyukinKeshikomi;

public class NyukinKeshikomiBuilder
		extends com.showka.domain.builder.DomainBuilderBase<NyukinKeshikomi, NyukinKeshikomiBuilder> {

	// private member
	/** nyukin */
	private Nyukin nyukin;

	/** keshikomiList */
	private List<Keshikomi> keshikomiList;

	/** recordId */
	private String recordId;

	/** version */
	private Integer version;

	// protected method
	@Override
	protected void apply(NyukinKeshikomi domain, NyukinKeshikomiBuilder builder) {
		builder.withNyukin(domain.getNyukin());
		builder.withKeshikomiList(domain.getKeshikomiList());
		builder.withRecordId(domain.getRecordId());
		builder.withVersion(domain.getVersion());
	}

	@Override
	protected NyukinKeshikomi createDomainObject() {
		NyukinKeshikomi domain = new NyukinKeshikomi(nyukin, keshikomiList);
		domain.setRecordId(recordId);
		domain.setVersion(version);
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
	public NyukinKeshikomiBuilder withKeshikomiList(final List<Keshikomi> keshikomiList) {
		addConfigurator(new BuilderConfigurator<NyukinKeshikomiBuilder>() {
			@Override
			public void configure(NyukinKeshikomiBuilder builder) {
				builder.keshikomiList = keshikomiList;
			}
		});
		return getThis();
	}

	/**
	 * {@link NyukinKeshikomi}に与えるrecordIdをこのビルダに設定する。
	 *
	 * @param recordId
	 *            recordId
	 * @return {@link NyukinKeshikomiBuilder}
	 */
	public NyukinKeshikomiBuilder withRecordId(final String recordId) {
		addConfigurator(new BuilderConfigurator<NyukinKeshikomiBuilder>() {
			@Override
			public void configure(NyukinKeshikomiBuilder builder) {
				builder.recordId = recordId;
			}
		});
		return getThis();
	}

	/**
	 * {@link NyukinKeshikomi}に与えるversionをこのビルダに設定する。
	 *
	 * @param version
	 *            version
	 * @return {@link NyukinKeshikomiBuilder}
	 */
	public NyukinKeshikomiBuilder withVersion(final Integer version) {
		addConfigurator(new BuilderConfigurator<NyukinKeshikomiBuilder>() {
			@Override
			public void configure(NyukinKeshikomiBuilder builder) {
				builder.version = version;
			}
		});
		return getThis();
	}

}