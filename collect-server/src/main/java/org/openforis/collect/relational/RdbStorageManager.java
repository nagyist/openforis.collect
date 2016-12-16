package org.openforis.collect.relational;

import java.util.Date;

import org.openforis.collect.event.RecordStep;

public interface RdbStorageManager {

	boolean existsRDB(String surveyName, RecordStep step);

	Date getRDBLastUpdateDate(String surveyName, RecordStep step);

	String getJdbcUrl(String surveyName, RecordStep step);

	Object getJdbcDriver(String surveyName, RecordStep recordStep);

	boolean deleteRDB(String surveyName, RecordStep step);

	String getRDBDescription(String surveyName, RecordStep recordStep);

}