package org.openforis.collect.api.event;

import org.openforis.collect.api.command.Command;

public class EntityAddedEvent extends NodeAddedEvent {
    public EntityAddedEvent(Command trigger, String parentId, String id, String defId) {
        super(trigger, parentId, id, defId);
    }
}
