package com.showka.system;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.showka.domain.z00.Shain;
import com.showka.service.crud.z00.i.ShainCrudService;
import com.showka.web.ModelAndViewExtended;

/**
 * ユーザー情報設定 (AOP).
 * 
 * @author ShowKa
 *
 */
@Component
@Aspect
public class UserInformationSetter {

	@Autowired
	private ShainCrudService shainCrudService;

	/**
	 * ユーザー情報の設定.
	 * 
	 * @param joinPoint
	 * @throws Throwable
	 */
	@Before(value = "@within(org.springframework.stereotype.Controller)")
	public void before(JoinPoint joinPoint) throws Throwable {
		Object[] args = joinPoint.getArgs();
		for (Object arg : args) {
			if (arg instanceof ModelAndViewExtended) {
				ModelAndViewExtended model = (ModelAndViewExtended) arg;
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				Shain shain = shainCrudService.getDomain(auth.getName());
				model.setUserInfo(shain);
			}
		}
	}
}
