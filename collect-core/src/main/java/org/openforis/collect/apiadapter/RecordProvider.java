package org.openforis.collect.apiadapter;

import org.openforis.idm.model.Record;

/**
 * @author D. Wiell
 * @author S. Ricci
 *
 */
public interface RecordProvider {
	
	Record provideRecord(int surveyId, int recordId);

}
