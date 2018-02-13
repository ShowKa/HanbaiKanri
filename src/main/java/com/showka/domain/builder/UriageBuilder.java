package com.showka.domain.builder;

import java.util.List;

import com.showka.domain.Kokyaku;
import com.showka.domain.Uriage;
import com.showka.domain.UriageMeisai;
import com.showka.kubun.HanbaiKubun;
import com.showka.value.TaxRate;
import com.showka.value.TheDate;

public class UriageBuilder
		extends com.showka.domain.builder.DomainBuilderBase<Uriage, UriageBuilder> {

	// private member
	/** kokyaku */
	private Kokyaku kokyaku;

	/** denpyoNumber */
	private String denpyoNumber;

	/** uriageDate */
	private TheDate uriageDate;

	/** keijoDate */
	private TheDate keijoDate;

	/** hanbaiKubun */
	private HanbaiKubun hanbaiKubun;

	/** shohizeiritsu */
	private TaxRate shohizeiritsu;

	/** uriageMeisai */
	private List<UriageMeisai> uriageMeisai;

	/** recordId */
	private String recordId;

	/** version */
	private Integer version;

	// protected method
	@Override
	protected void apply(Uriage domain, UriageBuilder builder) {
		builder.withKokyaku(domain.getKokyaku());
		builder.withDenpyoNumber(domain.getDenpyoNumber());
		builder.withUriageDate(domain.getUriageDate());
		builder.withKeijoDate(domain.getKeijoDate());
		builder.withHanbaiKubun(domain.getHanbaiKubun());
		builder.withShohizeiritsu(domain.getShohizeiritsu());
		builder.withUriageMeisai(domain.getUriageMeisai());
		builder.withRecordId(domain.getRecordId());
		builder.withVersion(domain.getVersion());
	}

	@Override
	protected Uriage createDomainObject() {
		Uriage domain = new Uriage(kokyaku, denpyoNumber, uriageDate, keijoDate, hanbaiKubun, shohizeiritsu,
				uriageMeisai);
		domain.setRecordId(recordId);
		domain.setVersion(version);
		return domain;
	}

	@Override
	protected UriageBuilder getThis() {
		return this;
	}

	@Override
	protected UriageBuilder newInstance() {
		return new UriageBuilder();
	}

	// public method
	/**
	 * {@link Uriage}に与えるkokyakuをこのビルダに設定する。
	 *
	 * @param kokyaku
	 *            kokyaku
	 * @return {@link UriageBuilder}
	 */
	public UriageBuilder withKokyaku(final Kokyaku kokyaku) {
		addConfigurator(new BuilderConfigurator<UriageBuilder>() {
			@Override
			public void configure(UriageBuilder builder) {
				builder.kokyaku = kokyaku;
			}
		});
		return getThis();
	}

	/**
	 * {@link Uriage}に与えるdenpyoNumberをこのビルダに設定する。
	 *
	 * @param denpyoNumber
	 *            denpyoNumber
	 * @return {@link UriageBuilder}
	 */
	public UriageBuilder withDenpyoNumber(final String denpyoNumber) {
		addConfigurator(new BuilderConfigurator<UriageBuilder>() {
			@Override
			public void configure(UriageBuilder builder) {
				builder.denpyoNumber = denpyoNumber;
			}
		});
		return getThis();
	}

	/**
	 * {@link Uriage}に与えるuriageDateをこのビルダに設定する。
	 *
	 * @param uriageDate
	 *            uriageDate
	 * @return {@link UriageBuilder}
	 */
	public UriageBuilder withUriageDate(final TheDate uriageDate) {
		addConfigurator(new BuilderConfigurator<UriageBuilder>() {
			@Override
			public void configure(UriageBuilder builder) {
				builder.uriageDate = uriageDate;
			}
		});
		return getThis();
	}

	/**
	 * {@link Uriage}に与えるkeijoDateをこのビルダに設定する。
	 *
	 * @param keijoDate
	 *            keijoDate
	 * @return {@link UriageBuilder}
	 */
	public UriageBuilder withKeijoDate(final TheDate keijoDate) {
		addConfigurator(new BuilderConfigurator<UriageBuilder>() {
			@Override
			public void configure(UriageBuilder builder) {
				builder.keijoDate = keijoDate;
			}
		});
		return getThis();
	}

	/**
	 * {@link Uriage}に与えるhanbaiKubunをこのビルダに設定する。
	 *
	 * @param hanbaiKubun
	 *            hanbaiKubun
	 * @return {@link UriageBuilder}
	 */
	public UriageBuilder withHanbaiKubun(final HanbaiKubun hanbaiKubun) {
		addConfigurator(new BuilderConfigurator<UriageBuilder>() {
			@Override
			public void configure(UriageBuilder builder) {
				builder.hanbaiKubun = hanbaiKubun;
			}
		});
		return getThis();
	}

	/**
	 * {@link Uriage}に与えるshohizeiritsuをこのビルダに設定する。
	 *
	 * @param shohizeiritsu
	 *            shohizeiritsu
	 * @return {@link UriageBuilder}
	 */
	public UriageBuilder withShohizeiritsu(final TaxRate shohizeiritsu) {
		addConfigurator(new BuilderConfigurator<UriageBuilder>() {
			@Override
			public void configure(UriageBuilder builder) {
				builder.shohizeiritsu = shohizeiritsu;
			}
		});
		return getThis();
	}

	/**
	 * {@link Uriage}に与えるuriageMeisaiをこのビルダに設定する。
	 *
	 * @param uriageMeisai
	 *            uriageMeisai
	 * @return {@link UriageBuilder}
	 */
	public UriageBuilder withUriageMeisai(final List<UriageMeisai> uriageMeisai) {
		addConfigurator(new BuilderConfigurator<UriageBuilder>() {
			@Override
			public void configure(UriageBuilder builder) {
				builder.uriageMeisai = uriageMeisai;
			}
		});
		return getThis();
	}

	/**
	 * {@link Uriage}に与えるrecordIdをこのビルダに設定する。
	 *
	 * @param recordId
	 *            recordId
	 * @return {@link UriageBuilder}
	 */
	public UriageBuilder withRecordId(final String recordId) {
		addConfigurator(new BuilderConfigurator<UriageBuilder>() {
			@Override
			public void configure(UriageBuilder builder) {
				builder.recordId = recordId;
			}
		});
		return getThis();
	}

	/**
	 * {@link Uriage}に与えるversionをこのビルダに設定する。
	 *
	 * @param version
	 *            version
	 * @return {@link UriageBuilder}
	 */
	public UriageBuilder withVersion(final Integer version) {
		addConfigurator(new BuilderConfigurator<UriageBuilder>() {
			@Override
			public void configure(UriageBuilder builder) {
				builder.version = version;
			}
		});
		return getThis();
	}

}