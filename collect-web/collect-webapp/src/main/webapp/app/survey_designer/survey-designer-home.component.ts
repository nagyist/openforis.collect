import { Component }        from '@angular/core';

import { SurveySummary } from './../metamodel/survey-summary';
import { SurveyService } from './../metamodel/survey.service';

@Component({
    templateUrl: 'app/survey_designer/survey-designer-home.component.html',
    providers: [SurveyService]
})
export class SurveyDesignerHomeComponent implements OnInit {
    title = 'Survey Designer';
    surveySummaries: SurveySummary[];
    selectedSurvey: SurveySummary;
    
    constructor(private surveyService: SurveyService) { }
    
    getSurveySummaries(): void {
        this.surveyService.getSurveySummaries().then(surveySummaries => this.surveySummaries = surveySummaries);
    }
    
    ngOnInit(): void {
        this.getSurveySummaries();
    }

}