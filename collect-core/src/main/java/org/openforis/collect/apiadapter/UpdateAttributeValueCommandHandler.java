package org.openforis.collect.apiadapter;

import java.util.Arrays;
import java.util.List;

import org.openforis.collect.api.command.UpdateAttributeValueCommand;
import org.openforis.collect.api.event.AttributeValueUpdatedEvent;
import org.openforis.collect.api.event.Event;
import org.openforis.collect.model.NodeChangeSet;
import org.openforis.collect.model.RecordUpdater;
import org.openforis.idm.model.Attribute;
import org.openforis.idm.model.Record;

public class UpdateAttributeValueCommandHandler implements CommandHandler<UpdateAttributeValueCommand> {

    private final RecordProvider recordProvider;
    private final RecordUpdater recordUpdater;

    public UpdateAttributeValueCommandHandler(RecordProvider recordProvider, RecordUpdater recordUpdater) {
        this.recordProvider = recordProvider;
        this.recordUpdater = recordUpdater;
    }

    @Override
    public List<Event> handle(UpdateAttributeValueCommand command) {
        Record record = recordProvider.provideRecord(command.recordId);
        @SuppressWarnings("unchecked")
		Attribute<?, org.openforis.idm.model.Value> attribute = (Attribute<?, org.openforis.idm.model.Value>) record.getNodeByInternalId(command.attributeId);
        NodeChangeSet changeSet = recordUpdater.updateAttribute(attribute, (org.openforis.idm.model.Value) command.value);
        //TODO convert changeset into events
        Event event = new AttributeValueUpdatedEvent(command, command.attributeId, command.value);
        return Arrays.asList(event);
    }

}
