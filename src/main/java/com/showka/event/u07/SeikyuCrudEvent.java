package com.showka.event.u07;

import com.showka.domain.u07.Seikyu;
import com.showka.event.CrudEvent;

@SuppressWarnings("serial")
public class SeikyuCrudEvent extends CrudEvent<Seikyu> {

	public SeikyuCrudEvent(Object source, EventType type, Seikyu domain) {
		super(source, type, domain);
	}
}
