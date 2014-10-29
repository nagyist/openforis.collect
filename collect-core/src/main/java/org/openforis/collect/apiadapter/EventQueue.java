/**
 *
 */
package org.openforis.collect.apiadapter;

import org.openforis.collect.api.event.Event;
import org.openforis.collect.api.event.EventListener;

import java.util.Arrays;
import java.util.List;

/**
 * @author D. Wiell
 * @author S. Ricci
 *
 */
class EventQueue {

	private final List<EventListener> listeners;

	public EventQueue(EventListener... listeners) {
		this.listeners = Arrays.asList(listeners);
	}

	public void publish(List<Event> events) {
		for (EventListener handler : listeners) {
			handler.handle(events);
		}
	}

}
