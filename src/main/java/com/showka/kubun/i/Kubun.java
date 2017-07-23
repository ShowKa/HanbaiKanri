package com.showka.kubun.i;

import com.showka.system.exception.SystemException;

public interface Kubun<T> {

	public String getCode();

	@SuppressWarnings("unchecked")
	public static <T extends Kubun<T>> T get(Class<T> enumType, String code) {

		String _code = code == null ? "" : code;

		Kubun<T>[] constants = enumType.getEnumConstants();
		for (Kubun<T> k : constants) {
			if (k.getCode().equals(_code)) {
				return (T) k;
			}
		}

		throw new SystemException("存在しないコードが引数に指定されました : " + code);
	}

	default boolean eq(String code) {
		return getCode().equals(code);
	}

	default boolean notEq(String code) {
		return !getCode().equals(code);
	}

	default boolean isIncludedIn(String... codes) {
		for (String c : codes) {
			if (getCode().equals(c)) {
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	default boolean isIncludedIn(T... kubuns) {
		for (T kubun : kubuns) {
			if (this == kubun) {
				return true;
			}
		}
		return false;
	}

}
