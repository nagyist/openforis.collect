import { Component, ViewChild, OnInit } from '@angular/core';

import { SurveySummary } from './../metamodel/survey-summary';
import { SurveyService } from './../metamodel/survey.service';

@Component({
    selector: 'survey-table',
    templateUrl: 'app/survey_designer/survey-table.component.html',
    styleUrls: ['app/survey_designer/survey-table.component.css']
})
export class SurveyTableComponent implements OnInit {
    
    surveys: SurveySummary[]; 
//    cols: any[];
    displayDialog: boolean;    
    selectedSurvey: SurveySummary;    
    newSurvey: boolean;    
    
    constructor(private surveyService: SurveyService) { }  
    
    ngOnInit() {  
        this.surveyService.getSurveySummaries()
            .subscribe(surveys => this.surveys = surveys);
//        this.cols = [            
//            { field: 'name',   header: 'Name' }        
//        ]; 
     } 
}