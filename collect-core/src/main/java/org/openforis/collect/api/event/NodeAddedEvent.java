package org.openforis.collect.api.event;

import org.openforis.collect.api.command.Command;

public abstract class NodeAddedEvent extends Event {
    public final String parentId;
    public final String id;
    public final String defId;

    public NodeAddedEvent(Command trigger, String parentId, String id, String defId) {
        super(trigger);
        this.parentId = parentId;
        this.id = id;
        this.defId = defId;
    }
}
