package org.openforis.collect.js.web;

import org.openforis.collect.api.command.AsyncCommandQueue;
import org.openforis.collect.api.command.CommandQueue;
import org.openforis.collect.api.query.RecordEventsProvider;
import org.openforis.collect.api.query.SchemaProvider;
import org.openforis.collect.apiadapter.*;
import org.openforis.collect.manager.RecordManager;
import org.openforis.collect.manager.SurveyManager;

class Dependencies {
    private static Dependencies instance;

    public final CommandQueue commandQueue;
    public final RecordEventsProvider recordEventsProvider;
    public final SchemaProvider schemaProvider;

    public synchronized static Dependencies dependencies() {
        if (instance == null)
            instance = new Dependencies();
        return instance;
    }

    private Dependencies() {
        SurveyManager surveyManager = new SurveyManager();
        RecordManager recordManager = new RecordManager();
        RecordProvider recordProvider = new CachingRecordProvider(
                surveyManager, recordManager
        );
        commandQueue = new AsyncCommandQueue(
                new SynchronousCommandQueue(recordProvider)
        );
        recordEventsProvider = new DefaultRecordEventsProvider();
        schemaProvider = new DefaultSchemaProvider();
    }

    public void stop() {
        commandQueue.stop();
    }
}
