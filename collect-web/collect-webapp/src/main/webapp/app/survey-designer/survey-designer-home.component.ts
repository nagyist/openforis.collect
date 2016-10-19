import { Component, OnInit }    from '@angular/core';

import { SurveySummary }        from './../metamodel/survey-summary';
import { SurveyService }        from './../metamodel/survey.service';

@Component({
    templateUrl: 'app/survey-designer/survey-designer-home.component.html',
    styleUrls: ['app/survey-designer/survey-designer-home.component.css'],
    providers: [SurveyService]
})
export class SurveyDesignerHomeComponent implements OnInit {
    title = 'Survey Designer';
   
    constructor() { }

    ngOnInit(): void {
    }

    gotoDetail(): void {
//        this.router.navigate(['/detail', this.selectedSurvey.id]);
    }

}