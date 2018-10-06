package com.showka.domain.builder;

import java.util.Optional;

import com.showka.domain.u01.Kokyaku;
import com.showka.domain.u01.NyukinKakeInfo;
import com.showka.domain.z00.Busho;
import com.showka.kubun.HanbaiKubun;
import com.showka.kubun.KokyakuKubun;

public class KokyakuBuilder extends com.showka.domain.builder.DomainBuilderBase<Kokyaku, KokyakuBuilder> {

	// private member
	/** code */
	private String code = STRING_EMPTY;;

	/** name */
	private String name = STRING_EMPTY;;

	/** address */
	private String address = STRING_EMPTY;

	/** kokyakuKubun */
	private KokyakuKubun kokyakuKubun = KokyakuKubun.EMPTY;

	/** hanbaiKubun */
	private HanbaiKubun hanbaiKubun = HanbaiKubun.EMPTY;

	/** shukanBusho */
	private Busho shukanBusho;

	/** nyukinKakeInfo */
	private Optional<NyukinKakeInfo> nyukinKakeInfo = Optional.empty();

	/** recordId */
	private String recordId;;

	/** version */
	private Integer version;

	// protected method
	@Override
	protected void apply(Kokyaku domain, KokyakuBuilder builder) {
		builder.withCode(domain.getCode());
		builder.withName(domain.getName());
		builder.withAddress(domain.getAddress());
		builder.withKokyakuKubun(domain.getKokyakuKubun());
		builder.withHanbaiKubun(domain.getHanbaiKubun());
		builder.withShukanBusho(domain.getShukanBusho());
		builder.withNyukinKakeInfo(domain.getNyukinKakeInfo());
		builder.withRecordId(domain.getRecordId());
		builder.withVersion(domain.getVersion());
	}

	@Override
	protected Kokyaku createDomainObject() {
		Kokyaku domain = new Kokyaku(code, name, address, kokyakuKubun, hanbaiKubun, shukanBusho, nyukinKakeInfo);
		domain.setRecordId(recordId);
		domain.setVersion(version);
		return domain;
	}

	@Override
	protected KokyakuBuilder getThis() {
		return this;
	}

	@Override
	protected KokyakuBuilder newInstance() {
		return new KokyakuBuilder();
	}

	// public method
	/**
	 * {@link Kokyaku}に与えるcodeをこのビルダに設定する。
	 *
	 * @param code
	 *            code
	 * @return {@link KokyakuBuilder}
	 */
	public KokyakuBuilder withCode(final String code) {
		addConfigurator(new BuilderConfigurator<KokyakuBuilder>() {
			@Override
			public void configure(KokyakuBuilder builder) {
				builder.code = code;
			}
		});
		return getThis();
	}

	/**
	 * {@link Kokyaku}に与えるnameをこのビルダに設定する。
	 *
	 * @param name
	 *            name
	 * @return {@link KokyakuBuilder}
	 */
	public KokyakuBuilder withName(final String name) {
		addConfigurator(new BuilderConfigurator<KokyakuBuilder>() {
			@Override
			public void configure(KokyakuBuilder builder) {
				builder.name = name;
			}
		});
		return getThis();
	}

	/**
	 * {@link Kokyaku}に与えるaddressをこのビルダに設定する。
	 *
	 * @param address
	 *            address
	 * @return {@link KokyakuBuilder}
	 */
	public KokyakuBuilder withAddress(final String address) {
		addConfigurator(new BuilderConfigurator<KokyakuBuilder>() {
			@Override
			public void configure(KokyakuBuilder builder) {
				builder.address = address;
			}
		});
		return getThis();
	}

	/**
	 * {@link Kokyaku}に与えるkokyakuKubunをこのビルダに設定する。
	 *
	 * @param kokyakuKubun
	 *            kokyakuKubun
	 * @return {@link KokyakuBuilder}
	 */
	public KokyakuBuilder withKokyakuKubun(final KokyakuKubun kokyakuKubun) {
		addConfigurator(new BuilderConfigurator<KokyakuBuilder>() {
			@Override
			public void configure(KokyakuBuilder builder) {
				builder.kokyakuKubun = kokyakuKubun;
			}
		});
		return getThis();
	}

	/**
	 * {@link Kokyaku}に与えるhanbaiKubunをこのビルダに設定する。
	 *
	 * @param hanbaiKubun
	 *            hanbaiKubun
	 * @return {@link KokyakuBuilder}
	 */
	public KokyakuBuilder withHanbaiKubun(final HanbaiKubun hanbaiKubun) {
		addConfigurator(new BuilderConfigurator<KokyakuBuilder>() {
			@Override
			public void configure(KokyakuBuilder builder) {
				builder.hanbaiKubun = hanbaiKubun;
			}
		});
		return getThis();
	}

	/**
	 * {@link Kokyaku}に与えるshukanBushoをこのビルダに設定する。
	 *
	 * @param shukanBusho
	 *            shukanBusho
	 * @return {@link KokyakuBuilder}
	 */
	public KokyakuBuilder withShukanBusho(final Busho shukanBusho) {
		addConfigurator(new BuilderConfigurator<KokyakuBuilder>() {
			@Override
			public void configure(KokyakuBuilder builder) {
				builder.shukanBusho = shukanBusho;
			}
		});
		return getThis();
	}

	/**
	 * {@link Kokyaku}に与えるnyukinKakeInfoをこのビルダに設定する。
	 *
	 * @param nyukinKakeInfo
	 *            nyukinKakeInfo
	 * @return {@link KokyakuBuilder}
	 */
	public KokyakuBuilder withNyukinKakeInfo(final Optional<NyukinKakeInfo> nyukinKakeInfo) {
		addConfigurator(new BuilderConfigurator<KokyakuBuilder>() {
			@Override
			public void configure(KokyakuBuilder builder) {
				builder.nyukinKakeInfo = nyukinKakeInfo;
			}
		});
		return getThis();
	}

	/**
	 * {@link Kokyaku}に与えるnyukinKakeInfoをこのビルダに設定する。
	 *
	 * @param nyukinKakeInfo
	 *            nyukinKakeInfo
	 * @return {@link KokyakuBuilder}
	 */
	public KokyakuBuilder withNyukinKakeInfo(final NyukinKakeInfo nyukinKakeInfo) {
		addConfigurator(new BuilderConfigurator<KokyakuBuilder>() {
			@Override
			public void configure(KokyakuBuilder builder) {
				builder.nyukinKakeInfo = Optional.of(nyukinKakeInfo);
			}
		});
		return getThis();
	}

	/**
	 * {@link Kokyaku}に与えるrecordIdをこのビルダに設定する。
	 *
	 * @param recordId
	 *            recordId
	 * @return {@link KokyakuBuilder}
	 */
	public KokyakuBuilder withRecordId(final String recordId) {
		addConfigurator(new BuilderConfigurator<KokyakuBuilder>() {
			@Override
			public void configure(KokyakuBuilder builder) {
				builder.recordId = recordId;
			}
		});
		return getThis();
	}

	/**
	 * {@link Kokyaku}に与えるversionをこのビルダに設定する。
	 *
	 * @param version
	 *            version
	 * @return {@link KokyakuBuilder}
	 */
	public KokyakuBuilder withVersion(final Integer version) {
		addConfigurator(new BuilderConfigurator<KokyakuBuilder>() {
			@Override
			public void configure(KokyakuBuilder builder) {
				builder.version = version;
			}
		});
		return getThis();
	}

}