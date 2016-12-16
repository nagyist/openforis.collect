package org.openforis.collect.relational;

import java.io.File;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.openforis.collect.event.RecordStep;
import org.openforis.collect.manager.BaseStorageManager;
import org.openforis.collect.model.Configuration.ConfigurationItem;
import org.openforis.collect.utils.Dates;
import org.springframework.stereotype.Component;

/**
 * 
 * @author S. Ricci
 *
 */
@Component
public class CollectLocalRDBStorageManager extends BaseStorageManager implements RdbStorageManager {
	
	private static final long serialVersionUID = 1L;
	
	private static final String DEFAULT_STORAGE_SUBFOLDER = "rdb";
	
	public CollectLocalRDBStorageManager() {
		super(DEFAULT_STORAGE_SUBFOLDER);
	}
	
	@PostConstruct
	public void init() {
		initStorageDirectory();
	}

	protected void initStorageDirectory() {
		super.initStorageDirectory(ConfigurationItem.RDB_PATH);
	}

	@Override
	public boolean existsRDB(String surveyName, RecordStep step) {
		File rdbFile = getRDBFile(surveyName, step);
		return rdbFile.exists() && rdbFile.length() > 0;
	}
	
	@Override
	public Date getRDBLastUpdateDate(String surveyName, RecordStep step) {
		File rdbFile = getRDBFile(surveyName, step);
		return rdbFile.exists() ? Dates.millisToDate(rdbFile.lastModified()) : null;
	}
	
	@Override
	public String getJdbcUrl(String surveyName, RecordStep step) {
		File rdbFile = getRDBFile(surveyName, step);
		return "jdbc:sqlite:" + formatPath(rdbFile.getAbsolutePath());
	}
	
	@Override
	public Object getJdbcDriver(String surveyName, RecordStep recordStep) {
		return "org.sqlite.JDBC";
	}
	
	@Override
	public boolean deleteRDB(String surveyName, RecordStep step) {
		File rdbFile = getRDBFile(surveyName, step);
		File rdbJournalFile = new File(getRDBJournalFileName(surveyName, step));
		rdbJournalFile.delete(); //don't care if it exists or not
		return rdbFile.delete();
	}

	@Override
	public String getRDBDescription(String surveyName, RecordStep recordStep) {
		File rdbFile = getRDBFile(surveyName, recordStep);
		String path = rdbFile.getAbsolutePath();
		return path;
	}
	
	private File getRDBFile(String surveyName, RecordStep step) {
		return new File(storageDirectory, getRDBFileName(surveyName, step));
	}
	
	private String getRDBFileName(String surveyName, RecordStep step) {
		return String.format("%s_%s.db", surveyName, step.name().toLowerCase());
	}

	private String getRDBJournalFileName(String surveyName, RecordStep step) {
		return String.format("%s_%s.db-journal", surveyName, step.name().toLowerCase());
	}
	
	private static String formatPath(String path) {
		return path.replaceAll("\\\\", "/");
	}

}
