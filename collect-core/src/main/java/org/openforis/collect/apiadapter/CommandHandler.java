/**
 * 
 */
package org.openforis.collect.apiadapter;

import java.util.List;

import org.openforis.collect.api.command.Command;
import org.openforis.collect.api.event.Event;

/**
 * @author D. Wiell
 * @author S. Ricci
 *
 */
interface CommandHandler<T extends Command> {

	List<Event> handle(T command);
	
}
