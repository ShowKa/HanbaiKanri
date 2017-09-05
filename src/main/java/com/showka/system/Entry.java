package com.showka.system;

import java.lang.reflect.Method;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Entry {

	/**
	 * Entry Class
	 */
	private Class<?> entryClass;

	/**
	 * Entry method
	 */
	private Method method;

	/**
	 * 
	 * EntryPointの名前（クラス名とメソッド名）取得.
	 * 
	 * @return ClassName.MethodName
	 */
	public String getEntryPointName() {
		return entryClass.getSimpleName() + "." + method.getName();
	}
}
