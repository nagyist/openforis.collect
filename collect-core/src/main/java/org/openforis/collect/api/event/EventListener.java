package org.openforis.collect.api.event;

import java.util.List;

/**
 * @author D. Wiell
 * @author S. Ricci
 *
 */
public interface EventListener {

	void handle(List<? extends Event> events);
	
}
