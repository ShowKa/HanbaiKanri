package com.showka.system.triggerEvent;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.showka.domain.DomainRoot;
import com.showka.system.exception.SystemException;
import com.showka.system.triggerEvent.CrudEvent.EventType;

@Aspect
@Component
public class TriggerCrudEventOnService {

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	@Around("execution(* com.showka.service.crud.Crud+.save(..)) && @annotation(triggerCrudEvent) && args(domain)")
	public <T extends DomainRoot> Object triggerSaveEvent(ProceedingJoinPoint pjp, TriggerCrudEvent triggerCrudEvent,
			T domain) throws Throwable {
		// register or update
		boolean doRegister = domain.getVersion() == null ? true : false;
		// get constructor for event
		Class<?> eventClass = CrudEventClassProxy.get(domain.getClass());
		@SuppressWarnings("unchecked")
		Constructor<CrudEvent<T>> constructor = this.getConstructor((Class<CrudEvent<T>>) eventClass);
		// get event instance
		Object target = pjp.getTarget();
		CrudEvent<T> beforeSaveEvent = this.getEventInstance(constructor, target, EventType.beforeSave, domain);
		EventType _beforeRegOrUpdate = doRegister ? EventType.beforeNewRegister : EventType.beforeUpdate;
		CrudEvent<T> beforeRegOrUpdateEvent = this.getEventInstance(constructor, target, _beforeRegOrUpdate, domain);
		// publish event
		applicationEventPublisher.publishEvent(beforeSaveEvent);
		applicationEventPublisher.publishEvent(beforeRegOrUpdateEvent);
		// do
		Object ret = pjp.proceed();
		// get event instance
		CrudEvent<T> saveEvent = this.getEventInstance(constructor, target, EventType.save, domain);
		EventType _regOrUpdate = doRegister ? EventType.newRegister : EventType.update;
		CrudEvent<T> regOrUpdateEvent = this.getEventInstance(constructor, target, _regOrUpdate, domain);
		// publish event
		applicationEventPublisher.publishEvent(saveEvent);
		applicationEventPublisher.publishEvent(regOrUpdateEvent);
		// return
		return ret;
	}

	@Around("execution(* com.showka.service.crud.Crud+.delete(..)) && @annotation(triggerCrudEvent) && args(domain)")
	public <T extends DomainRoot> Object triggerDeleteEvent(ProceedingJoinPoint pjp, TriggerCrudEvent triggerCrudEvent,
			T domain) throws Throwable {
		// get constructor for event
		Class<?> eventClass = CrudEventClassProxy.get(domain.getClass());
		@SuppressWarnings("unchecked")
		Constructor<CrudEvent<T>> constructor = this.getConstructor((Class<CrudEvent<T>>) eventClass);
		// get event instance
		Object target = pjp.getTarget();
		CrudEvent<T> beforeDeleteEvent = this.getEventInstance(constructor, target, EventType.beforeDelete, domain);
		// publish event
		applicationEventPublisher.publishEvent(beforeDeleteEvent);
		// do
		Object ret = pjp.proceed();
		// get event instance
		CrudEvent<T> deleteEvent = this.getEventInstance(constructor, target, EventType.delete, domain);
		// publish event
		applicationEventPublisher.publishEvent(deleteEvent);
		// return
		return ret;
	}

	private <T extends DomainRoot> Constructor<CrudEvent<T>> getConstructor(Class<CrudEvent<T>> eventClass) {
		if (!CrudEvent.class.isAssignableFrom(eventClass)) {
			throw new SystemException("@TriggerCrudEventにはCrudEventのサブクラスを設定してください。 : " + eventClass);
		}
		@SuppressWarnings("unchecked")
		Constructor<CrudEvent<T>>[] constuctor = (Constructor<CrudEvent<T>>[]) eventClass.getConstructors();
		if (constuctor.length == 0) {
			throw new SystemException("CrudEventにコンストラクタが設定されていません。 : " + eventClass);
		} else if (constuctor.length > 1) {
			throw new SystemException("CrudEventにコンストラクタは1つだけ許可されています。 : " + eventClass);
		}
		return constuctor[0];
	}

	private <T extends DomainRoot> CrudEvent<T> getEventInstance(Constructor<CrudEvent<T>> constructor, Object target,
			EventType type, T domain) {
		CrudEvent<T> event;
		try {
			event = constructor.newInstance(target, type, domain);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new SystemException("CrudEventにコンストラクタの設定が誤っています。 ", e);
		}
		return event;
	}
}