package com.showka.domain.builder;

import java.util.Map;

import com.showka.domain.Keshikomi;
import com.showka.domain.Nyukin;
import com.showka.domain.NyukinKeshikomi;
import com.showka.domain.Urikake;

public class NyukinKeshikomiBuilder
		extends com.showka.domain.builder.DomainBuilderBase<NyukinKeshikomi, NyukinKeshikomiBuilder> {

	// private member
	/** nyukin */
	private Nyukin nyukin;

	/** keshikomiMap */
	private Map<Keshikomi, Urikake> keshikomiMap;

	/** recordId */
	private String recordId;

	/** version */
	private Integer version;

	// protected method
	@Override
	protected void apply(NyukinKeshikomi domain, NyukinKeshikomiBuilder builder) {
		builder.withNyukin(domain.getNyukin());
		builder.withKeshikomiMap(domain.getKeshikomiMap());
		builder.withRecordId(domain.getRecordId());
		builder.withVersion(domain.getVersion());
	}

	@Override
	protected NyukinKeshikomi createDomainObject() {
		NyukinKeshikomi domain = new NyukinKeshikomi(nyukin, keshikomiMap);
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
	 * {@link NyukinKeshikomi}に与えるkeshikomiMapをこのビルダに設定する。
	 *
	 * @param keshikomiMap
	 *            keshikomiMap
	 * @return {@link NyukinKeshikomiBuilder}
	 */
	public NyukinKeshikomiBuilder withKeshikomiMap(final Map<Keshikomi, Urikake> keshikomiMap) {
		addConfigurator(new BuilderConfigurator<NyukinKeshikomiBuilder>() {
			@Override
			public void configure(NyukinKeshikomiBuilder builder) {
				builder.keshikomiMap = keshikomiMap;
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