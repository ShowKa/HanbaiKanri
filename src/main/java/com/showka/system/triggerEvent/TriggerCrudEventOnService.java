package com.showka.system.triggerEvent;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.showka.domain.DomainBase;
import com.showka.event.CrudEvent;
import com.showka.event.CrudEvent.EventType;
import com.showka.system.exception.SystemException;

@Aspect
@Component
public class TriggerCrudEventOnService {

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	@Around("execution(* com.showka.service.crud.Crud+.save(..)) && @annotation(triggerCrudEvent) && args(domain)")
	public Object triggerSaveEvent(ProceedingJoinPoint pjp, TriggerCrudEvent triggerCrudEvent, DomainBase domain)
			throws Throwable {
		// register or update
		boolean doRegister = domain.getVersion() == null ? true : false;
		// get constructor for event
		Class<?> eventClass = triggerCrudEvent.event();
		Constructor<?> constructor = this.getConstructor(eventClass);
		// get event instance
		Object target = pjp.getTarget();
		CrudEvent<?> beforeSaveEvent = this.getEventInstance(constructor, target, EventType.beforeSave, domain);
		EventType _beforeRegOrUpdate = doRegister ? EventType.beforeNewRegister : EventType.beforeUpdate;
		CrudEvent<?> beforeRegOrUpdateEvent = this.getEventInstance(constructor, target, _beforeRegOrUpdate, domain);
		// publish event
		applicationEventPublisher.publishEvent(beforeSaveEvent);
		applicationEventPublisher.publishEvent(beforeRegOrUpdateEvent);
		// do
		Object ret = pjp.proceed();
		// get event instance
		CrudEvent<?> saveEvent = this.getEventInstance(constructor, target, EventType.save, domain);
		EventType _regOrUpdate = doRegister ? EventType.newRegister : EventType.update;
		CrudEvent<?> regOrUpdateEvent = this.getEventInstance(constructor, target, _regOrUpdate, domain);
		// publish event
		applicationEventPublisher.publishEvent(saveEvent);
		applicationEventPublisher.publishEvent(regOrUpdateEvent);
		// return
		return ret;
	}

	@Around("execution(* com.showka.service.crud.Crud+.delete(..)) && @annotation(triggerCrudEvent) && args(domain)")
	public Object triggerDeleteEvent(ProceedingJoinPoint pjp, TriggerCrudEvent triggerCrudEvent, DomainBase domain)
			throws Throwable {
		// get constructor for event
		Class<?> eventClass = triggerCrudEvent.event();
		Constructor<?> constructor = this.getConstructor(eventClass);
		// get event instance
		Object target = pjp.getTarget();
		CrudEvent<?> beforeDeleteEvent = this.getEventInstance(constructor, target, EventType.beforeDelete, domain);
		// publish event
		applicationEventPublisher.publishEvent(beforeDeleteEvent);
		// do
		Object ret = pjp.proceed();
		// get event instance
		CrudEvent<?> deleteEvent = this.getEventInstance(constructor, target, EventType.delete, domain);
		// publish event
		applicationEventPublisher.publishEvent(deleteEvent);
		// return
		return ret;
	}

	private Constructor<?> getConstructor(Class<?> eventClass) {
		if (!CrudEvent.class.isAssignableFrom(eventClass)) {
			throw new SystemException("@TriggerCrudEventにはCrudEventのサブクラスを設定してください。 : " + eventClass);
		}
		Constructor<?>[] constuctor = eventClass.getConstructors();
		if (constuctor.length == 0) {
			throw new SystemException("CrudEventにコンストラクタが設定されていません。 : " + eventClass);
		} else if (constuctor.length > 1) {
			throw new SystemException("CrudEventにコンストラクタは1つだけ許可されています。 : " + eventClass);
		}
		return constuctor[0];
	}

	private CrudEvent<?> getEventInstance(Constructor<?> constructor, Object target, EventType type,
			DomainBase domain) {
		CrudEvent<?> event;
		try {
			event = (CrudEvent<?>) constructor.newInstance(target, type, domain);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new SystemException("CrudEventにコンストラクタの設定が誤っています。 ", e);
		}
		return event;
	}
}
