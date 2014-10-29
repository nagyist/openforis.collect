package org.openforis.collect.api.event;

import org.openforis.collect.api.Value;
import org.openforis.collect.api.command.Command;

public class AttributeAddedEvent extends NodeAddedEvent {
    public final Value value;

    public AttributeAddedEvent(Command trigger, String parentId, String id, String defId, Value value) {
        super(trigger, parentId, id, defId);
        this.value = value;
    }
}
