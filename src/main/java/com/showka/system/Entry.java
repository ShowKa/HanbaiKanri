package com.showka.system;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import lombok.Setter;

@Component
@Setter
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Entry {

	/**
	 * Entry Class
	 */
	private String entryClass;

	/**
	 * Entry method
	 */
	private String method;

	/**
	 * 
	 * EntryPointの名前（クラス名とメソッド名）取得.
	 * 
	 * @return ClassName.MethodName
	 */
	public String getEntryPointName() {
		return entryClass + "." + method;
	}
}
