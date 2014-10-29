/**
 *
 */
package org.openforis.collect.api.event;

import org.openforis.collect.api.Value;
import org.openforis.collect.api.command.Command;

/**
 * @author D. Wiell
 * @author S. Ricci
 */
public class AttributeValueUpdatedEvent extends Event {

    public final long id;
    public final Value value;

    public AttributeValueUpdatedEvent(Command trigger, long id, Value value) {
        super(trigger);
        this.id = id;
        this.value = value;
    }
}
