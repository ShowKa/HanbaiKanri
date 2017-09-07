package com.showka.system;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Session の属性設定 (AOP).
 * 
 * @author ShowKa
 *
 */
@Component
@Aspect
public class SessionAttributeSetter {

	@Autowired
	private Entry entry;

	/**
	 * Entry Class の設定
	 * 
	 * @param joinPoint
	 * @throws Throwable
	 */
	@Before(value = "@within(org.springframework.stereotype.Controller)")
	public void before(JoinPoint joinPoint) throws Throwable {
		entry.setEntryClass(joinPoint.getTarget().getClass().getSimpleName());
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		entry.setMethod(signature.getMethod().getName());
	}

}
