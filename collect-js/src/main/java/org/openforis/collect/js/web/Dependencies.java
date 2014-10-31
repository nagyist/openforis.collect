package org.openforis.collect.js.web;

import org.openforis.collect.api.command.AsyncCommandQueue;
import org.openforis.collect.api.command.CommandQueue;
import org.openforis.collect.api.query.RecordEventsProvider;
import org.openforis.collect.api.query.SchemaProvider;
import org.openforis.collect.apiadapter.DefaultRecordEventsProvider;
import org.openforis.collect.apiadapter.DefaultSchemaProvider;
import org.openforis.collect.apiadapter.RecordProvider;
import org.openforis.collect.apiadapter.SurveyProvider;
import org.openforis.collect.apiadapter.SynchronousCommandQueue;
import org.openforis.collect.js.dummy.DummyRecordProvider;
import org.openforis.collect.js.dummy.DummySurveyProvider;

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
//        SurveyManager surveyManager = new SurveyManager();
//        RecordManager recordManager = new RecordManager();
//        RecordProvider recordProvider = new CachingRecordProvider(
//                surveyManager, recordManager
//        );
        
    	SurveyProvider surveyProvider = new DummySurveyProvider();
        RecordProvider recordProvider = new DummyRecordProvider(surveyProvider);
        
        commandQueue = new AsyncCommandQueue(
                new SynchronousCommandQueue(recordProvider)
        );
        recordEventsProvider = new DefaultRecordEventsProvider(recordProvider);
        schemaProvider = new DefaultSchemaProvider(surveyProvider);
    }

    public void stop() {
        commandQueue.stop();
    }
}
