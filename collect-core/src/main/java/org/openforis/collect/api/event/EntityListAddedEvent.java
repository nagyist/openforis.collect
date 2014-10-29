package org.openforis.collect.api.event;

import org.openforis.collect.api.command.Command;

public class EntityListAddedEvent extends NodeAddedEvent {
    public EntityListAddedEvent(Command trigger, String parentId, String id, String defId) {
        super(trigger, parentId, id, defId);
    }
}
