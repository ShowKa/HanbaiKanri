package com.showka.event;

import org.springframework.context.ApplicationEvent;

import com.showka.domain.DomainBase;

import lombok.Getter;

@SuppressWarnings("serial")
public abstract class CrudEvent<T extends DomainBase> extends ApplicationEvent {

	public enum EventType {
		save, delete, newRegister, update, beforeSave, beforeDelete, beforeNewRegister, beforeUpdate
	}

	/** domain */
	@Getter
	private T domain;

	/** event type */
	@Getter
	private EventType type;

	/**
	 * Constructor.
	 */
	public CrudEvent(Object source, EventType type, T domain) {
		super(source);
		this.domain = domain;
		this.type = type;
	}
}
