/**
 * 
 */
package org.openforis.collect.js.dummy;

import java.util.Arrays;
import java.util.List;

import org.openforis.collect.apiadapter.RecordProvider;
import org.openforis.collect.apiadapter.SurveyProvider;
import org.openforis.collect.model.RecordUpdater;
import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.model.Record;

/**
 * @author D. Wiell
 * @author S. Ricci
 *
 */
public class DummyRecordProvider implements RecordProvider {

	private List<Record> records;

	public DummyRecordProvider(SurveyProvider surveyProvider) {
		Survey survey = surveyProvider.provideSurvey(1);
		Record record = survey.createRecord("2.0");
		record.setId(2);
		record.createRootEntity("cluster");
		RecordUpdater recordUpdater = new RecordUpdater();
		recordUpdater.initializeRecord(record);
		records = Arrays.asList(record);
	}
	
	@Override
	public Record provideRecord(int recordId) {
		return records.get(0);
	}

}
