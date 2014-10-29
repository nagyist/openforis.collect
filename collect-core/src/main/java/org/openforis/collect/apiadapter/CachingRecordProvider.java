package org.openforis.collect.apiadapter;

import com.google.common.base.Objects;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.Weigher;
import org.openforis.collect.manager.RecordManager;
import org.openforis.collect.manager.SurveyManager;
import org.openforis.collect.model.CollectSurvey;
import org.openforis.collect.persistence.SurveyDao;
import org.openforis.idm.model.Record;

public class CachingRecordProvider implements RecordProvider {
    private static final long ONE_MILLION_ITEMS = 1000 * 1000;
    private final SurveyManager surveyManager;
    private final RecordManager recordManager;
    private final LoadingCache<RecordKey, Record> recordCache;

    public CachingRecordProvider(SurveyManager surveyManager, RecordManager recordManager) {
        this.surveyManager = surveyManager;
        this.recordManager = recordManager;

        CacheLoader<RecordKey, Record> loader = new CacheLoader<RecordKey, Record>() {
            public Record load(RecordKey key) {
                return loadRecord(key.surveyId, key.recordId);
            }
        };
        recordCache = CacheBuilder.newBuilder()
                .maximumWeight(ONE_MILLION_ITEMS)
                .weigher(new Weigher<RecordKey, Record>() {
                    public int weigh(RecordKey key, Record record) {
                        return 1; // TODO: Base weight on record size
                    }
                })
                .build(loader);
    }

    public Record provideRecord(int surveyId, int recordId) {
        return recordCache.getUnchecked(new RecordKey(surveyId, recordId));
    }

    public Record loadRecord(int surveyId, int recordId) {
        CollectSurvey survey = loadSurvey(surveyId);
        return recordManager.load(survey, recordId);
    }

    private CollectSurvey loadSurvey(int surveyId) {
        SurveyDao surveyDao = surveyManager.getSurveyDao();
        return (CollectSurvey) surveyDao.load(surveyId);
    }

    private final class RecordKey {
        public final int surveyId;
        public final int recordId;

        public RecordKey(int surveyId, int recordId) {
            this.surveyId = surveyId;
            this.recordId = recordId;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(surveyId, recordId);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            final RecordKey other = (RecordKey) obj;
            return Objects.equal(this.surveyId, other.surveyId) && Objects.equal(this.recordId, other.recordId);
        }
    }
}
