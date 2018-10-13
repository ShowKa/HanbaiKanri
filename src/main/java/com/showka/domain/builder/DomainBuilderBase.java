package com.showka.domain.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.showka.domain.DomainBase;
import com.showka.domain.DomainCore;

/**
 * {@link AbstractDomain}のインスタンスを生成するビルダー。
 *
 * @param <T>
 *            ビルド対象のインスタンスの型
 * @param <S>
 *            このビルダークラスの型
 */
public abstract class DomainBuilderBase<T extends DomainCore, S extends DomainBuilderBase<T, S>> {

	/**
	 * Empty String
	 */
	protected static final String STRING_EMPTY = "";

	/**
	 * Empty Integer
	 */
	protected static final Integer INTEGER_EMPTY = new Integer(0);

	List<BuilderConfigurator<S>> configurators = new ArrayList<BuilderConfigurator<S>>();

	/**
	 * ビルダの設定に基づき、引数の{@link DomainBase}の内容を変更した新しいインスタンスを生成する。
	 *
	 * @param vo
	 *            状態を引用する{@link DomainBase}
	 * @return vo の内容に対して、このビルダの設定を上書きした{@link DomainBase}の新しいインスタンス
	 */
	public T apply(T vo) {
		S builder = newInstance();
		apply(vo, builder);
		for (BuilderConfigurator<S> configurator : configurators) {
			builder.addConfigurator(configurator);
		}
		return builder.build();
	}

	/**
	 * ビルダの設定に基づいて{@link AbstractDomain}の新しいインスタンスを生成する。
	 *
	 * @return {@link AbstractDomain}の新しいインスタンス
	 */
	public T build() {
		for (BuilderConfigurator<S> configurator : configurators) {
			configurator.configure(getThis());
		}
		T domain = createDomainObject();
		if (domain instanceof DomainBase) {
			DomainBase _domain = (DomainBase) domain;
			if (_domain.getRecordId() == null) {
				_domain.setRecordId(UUID.randomUUID().toString());
			}
		}
		domain.validate();
		return domain;
	}

	/**
	 * {@link BuilderConfigurator}を追加する。
	 *
	 * @param configurator
	 *            {@link BuilderConfigurator}
	 */
	protected void addConfigurator(BuilderConfigurator<S> configurator) {
		configurators.add(configurator);
	}

	/**
	 * 引数のビルダに対して、引数の{@link AbstractDomain}の内容を適用する。
	 *
	 * @param vo
	 *            状態を引用する{@link AbstractDomain}
	 * @param builder
	 *            ビルダ
	 */
	protected abstract void apply(T vo, S builder);

	/**
	 * ビルダの設定に基づいて{@link AbstractDomain}の新しいインスタンスを生成する。
	 *
	 * <p>
	 * {@link DomainBuilderBase#build}内でこのビルダに追加された {@link BuilderConfigurator}
	 * を全て実行した後に、このメソッドが呼ばれる。<br>
	 * その為、このビルダに対する変更を行うロジックはこのメソッド内に記述せず、目的となる{@link AbstractDomain}
	 * を生成し返すロジックを記述することが望まれる。
	 * </p>
	 *
	 * @return {@link AbstractDomain}の新しいインスタンス
	 */
	protected abstract T createDomainObject();

	/**
	 * このビルダークラスのインスタンスを返す。
	 *
	 * @return このビルダークラスのインスタンス。
	 */
	protected abstract S getThis();

	/**
	 * このビルダークラスの新しいインスタンスを返す。
	 *
	 * @return このビルダークラスの新しいインスタンス。
	 */
	protected abstract S newInstance();

	/**
	 * {@link DomainBuilderBase#build()}内で順次実行されるビルダの設定を定義するインタフェース。
	 *
	 * @param <S>
	 *            設定対象ビルダーの型
	 */
	public static interface BuilderConfigurator<S> {

		/**
		 * {@link DomainBuilderBase#build()}内で呼ばれる際に実行するビルドアクションを定義する。
		 *
		 * @param builder
		 *            ビルダーインスタンス
		 */
		void configure(S builder);

	}

}