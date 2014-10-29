package org.openforis.collect.apiadapter;

import org.openforis.collect.api.command.UpdateAttributeValueCommand;
import org.openforis.collect.api.event.AttributeValueUpdatedEvent;
import org.openforis.collect.api.event.Event;
import org.openforis.collect.model.RecordUpdater;
import org.openforis.idm.model.TextValue;

import java.util.Arrays;
import java.util.List;

public class UpdateAttributeValueCommandHandler implements CommandHandler<UpdateAttributeValueCommand> {

    private final RecordProvider recordProvider;
    private final RecordUpdater recordUpdater;

    public UpdateAttributeValueCommandHandler(RecordProvider recordProvider, RecordUpdater recordUpdater) {
        this.recordProvider = recordProvider;
        this.recordUpdater = recordUpdater;
    }

    @Override
    public List<Event> handle(UpdateAttributeValueCommand command) {
//        Record record = recordProvider.provideRecord(command.surveyId, command.recordId);
//        Attribute<?, Value> attribute = (Attribute<?, Value>) record.getNodeByInternalId(command.attributeId);
//        NodeChangeSet changeSet = recordUpdater.updateAttribute(attribute, (Value) command.value);
        if (((TextValue) command.value).getValue().equals("t"))
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        Event event = new AttributeValueUpdatedEvent(command, command.attributeId, command.value);
        return Arrays.asList(event);
    }

}
