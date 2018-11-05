package com.showka.service.event.u06;

import com.showka.domain.u07.Seikyu;
import com.showka.service.event.CrudEvent;

@SuppressWarnings("serial")
public class SeikyuCrudEvent extends CrudEvent<Seikyu> {

	public SeikyuCrudEvent(Object source, EventType type, Seikyu domain) {
		super(source, type, domain);
	}
}
