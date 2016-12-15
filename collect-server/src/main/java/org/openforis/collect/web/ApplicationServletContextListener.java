package org.openforis.collect.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jooq.impl.DataSourceConnectionProvider;
import org.openforis.collect.utils.DbInitializer;
import org.openforis.collect.utils.DbUtils;

public class ApplicationServletContextListener implements ServletContextListener {

	private final Log LOG = LogFactory.getLog(ApplicationServletContextListener.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		//initialize database
		DataSourceConnectionProvider connectionProvider = new DataSourceConnectionProvider(DbUtils.getDataSource());
		new DbInitializer(connectionProvider).start();
		LOG.info("===========Open Foris Collect Initialized===========");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

}
