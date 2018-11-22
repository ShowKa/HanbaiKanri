package com.showka.system;

import javax.persistence.EntityNotFoundException;
import javax.persistence.OptimisticLockException;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 
 * @author ShowKa
 *
 */
@Component
@Aspect
public class OptimisticConcurrencyControl {

	@AfterThrowing(pointcut = "execution(* com.showka.service.crud.Crud+.delete(..))", throwing = "ex")
	public void optimisticConcurrencyControl(EntityNotFoundException ex) throws Throwable {
		throw new OptimisticLockException("OptimisticLockException!! Entity was already deleted.", ex);
	}

}
