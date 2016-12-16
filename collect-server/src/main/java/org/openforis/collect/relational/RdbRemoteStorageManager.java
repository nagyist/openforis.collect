package org.openforis.collect.relational;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javax.sql.DataSource;

import org.openforis.collect.event.RecordStep;

public class RdbRemoteStorageManager implements RdbStorageManager {

	private DataSource dataSource;
	
	public RdbRemoteStorageManager(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}

	@Override
	public boolean existsRDB(final String surveyName, final RecordStep step) {
		ConnectionExecutable<Boolean> exec = new ConnectionExecutable<Boolean>() {
			public Boolean execute(Connection conn) throws Exception {
				String schema = determineSchemaName(surveyName, step);
				Statement stmt = conn.createStatement();
				stmt.executeQuery("SELECT ");
				return null;
			}
		};
		Boolean result = executeWithConnection(exec);
		return result == null ? false : result.booleanValue();
	}

	@Override
	public Date getRDBLastUpdateDate(String surveyName, RecordStep step) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getJdbcUrl(String surveyName, RecordStep step) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getJdbcDriver(String surveyName, RecordStep recordStep) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteRDB(String surveyName, RecordStep step) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getRDBDescription(String surveyName, RecordStep recordStep) {
		// TODO Auto-generated method stub
		return null;
	}

	private void closeQuietly(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {}
	}
	
	private String determineSchemaName(String surveyName, RecordStep step) {
		return "of_rdb_" + surveyName + "_" + step.name().toLowerCase();
	}

	private <O extends Object> O executeWithConnection(ConnectionExecutable<O> exec) {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			return exec.execute(conn);
		} catch (Exception e) {
			return null;
		} finally {
			closeQuietly(conn);
		}
	}

	private interface ConnectionExecutable<O extends Object> {
		
		O execute(Connection conn) throws Exception;
	}
}
