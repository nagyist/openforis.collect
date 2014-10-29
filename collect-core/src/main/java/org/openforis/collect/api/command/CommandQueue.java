/**
 * 
 */
package org.openforis.collect.api.command;

import org.openforis.collect.api.event.EventListener;

/**
 * @author D. Wiell
 * @author S. Ricci
 *
 */
public interface CommandQueue {

	void submit(Command command);

	void addListener(EventListener eventListener);

	void removeListener(EventListener eventListener);

	void stop();
}
