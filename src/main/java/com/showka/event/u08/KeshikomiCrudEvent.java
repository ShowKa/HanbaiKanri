package com.showka.event.u08;

import com.showka.domain.u08.Keshikomi;
import com.showka.event.CrudEvent;

@SuppressWarnings("serial")
public class KeshikomiCrudEvent extends CrudEvent<Keshikomi> {

	public KeshikomiCrudEvent(Object source, EventType type, Keshikomi domain) {
		super(source, type, domain);
	}
}
