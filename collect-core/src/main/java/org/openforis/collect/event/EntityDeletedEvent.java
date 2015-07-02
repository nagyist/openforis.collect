package org.openforis.collect.event;

import java.util.Date;

public class EntityDeletedEvent extends RecordEvent {

	public EntityDeletedEvent(String surveyName, Integer recordId, int definitionId,
			Integer parentId, int entityId, Date timestamp, String userName) {
		super(surveyName, recordId, definitionId, parentId, entityId, timestamp, userName);
	}
	
}
