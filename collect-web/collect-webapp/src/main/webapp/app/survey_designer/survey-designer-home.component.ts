import { Component, OnInit }    from '@angular/core';

import { SurveySummary }        from './../metamodel/survey-summary';
import { SurveyService }        from './../metamodel/survey.service';
//import { SurveyTable }          from './../survey_designer/survey-table.component';

@Component({
    templateUrl: 'app/survey_designer/survey-designer-home.component.html',
    styleUrls: ['app/survey_designer/survey-designer-home.component.css']
})
export class SurveyDesignerHomeComponent implements OnInit {
    title = 'Survey Designer';
   
    constructor() { }

    ngOnInit(): void {
//        this.getSurveySummaries();
    }

    gotoDetail(): void {
//        this.router.navigate(['/detail', this.selectedSurvey.id]);
    }

}