package org.openforis.collect.apiadapter;

import java.util.ArrayList;
import java.util.List;

import org.openforis.collect.api.event.AttributeAddedEvent;
import org.openforis.collect.api.event.EntityAddedEvent;
import org.openforis.collect.api.event.Event;
import org.openforis.collect.api.query.RecordEventsProvider;
import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.model.Entity;
import org.openforis.idm.model.Node;
import org.openforis.idm.model.Record;
import org.openforis.idm.model.TextValue;

public class DefaultRecordEventsProvider implements RecordEventsProvider {

	private RecordProvider recordProvider;

    public DefaultRecordEventsProvider(RecordProvider recordProvider) {
		this.recordProvider = recordProvider;
	}
    
    public List<? extends Event> eventsForRecord(int recordId) {
    	Record record = recordProvider.provideRecord(recordId);
    	Entity rootEntity = record.getRootEntity();
    	return eventsForEntity(rootEntity);
    }

	private List<? extends Event> eventsForEntity(Entity entity) {
		List<Event> result = new ArrayList<Event>();
		String parentId = entity.getParent() == null ? null: String.valueOf(entity.getParent().getInternalId());
		result.add(new EntityAddedEvent(null, parentId,
				String.valueOf(entity.getInternalId()), 
				String.valueOf(entity.getDefinition().getId()))
		);
    	for (Node<? extends NodeDefinition> node : entity.getChildren()) {
			if (node instanceof Entity) {
				result.addAll(eventsForEntity((Entity) node));
			} else {
				result.add(new AttributeAddedEvent(null, 
						String.valueOf(entity.getInternalId()), 
						String.valueOf(node.getInternalId()),
						String.valueOf(node.getDefinition().getId()), 
						new TextValue("")
					));
			}
		}
		return result;
	}


}
