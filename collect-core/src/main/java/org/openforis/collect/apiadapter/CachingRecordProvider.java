package org.openforis.collect.apiadapter;

import org.openforis.collect.manager.RecordManager;
import org.openforis.collect.manager.SurveyManager;
import org.openforis.collect.model.CollectSurvey;
import org.openforis.collect.persistence.SurveyDao;
import org.openforis.idm.model.Record;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.Weigher;

public class CachingRecordProvider implements RecordProvider {
    private static final long ONE_MILLION_ITEMS = 1000 * 1000;
    private final SurveyManager surveyManager;
    private final RecordManager recordManager;
    private final LoadingCache<Integer, Record> recordCache;

    public CachingRecordProvider(SurveyManager surveyManager, RecordManager recordManager) {
        this.surveyManager = surveyManager;
        this.recordManager = recordManager;

        CacheLoader<Integer, Record> loader = new CacheLoader<Integer, Record>() {
            public Record load(Integer key) {
                return loadRecord(key);
            }
        };
        recordCache = CacheBuilder.newBuilder()
                .maximumWeight(ONE_MILLION_ITEMS)
                .weigher(new Weigher<Integer, Record>() {
                    public int weigh(Integer key, Record record) {
                        return 1; // TODO: Base weight on record size
                    }
                })
                .build(loader);
    }

    public Record provideRecord(int recordId) {
        return recordCache.getUnchecked(recordId);
    }

    public Record loadRecord(int recordId) {
    	//TODO
        CollectSurvey survey = null; 
        return recordManager.load(survey, recordId);
    }

    private CollectSurvey loadSurvey(int surveyId) {
        SurveyDao surveyDao = surveyManager.getSurveyDao();
        return (CollectSurvey) surveyDao.load(surveyId);
    }

}
