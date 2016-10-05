import { Injectable } from '@angular/core';

import { SurveySummary } from 'survey-summary';
import { SURVEYS } from './mock-surveys';

@Injectable()
export class SurveyService {
    getSurveySummaries(): Promise<SurveySummary[]> {
        return Promise.resolve(SURVEYS);
    }
}