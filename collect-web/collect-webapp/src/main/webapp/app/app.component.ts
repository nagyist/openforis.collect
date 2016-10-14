import { Component } from '@angular/core';
import './rxjs-operators';

import { SurveyService } from './metamodel/survey.service';
import { RecordService } from './model/record.service';

@Component({
    selector: 'collect-app',
    template: '<router-outlet></router-outlet>',
    providers: [SurveyService, RecordService]
})
export class AppComponent {
    
}