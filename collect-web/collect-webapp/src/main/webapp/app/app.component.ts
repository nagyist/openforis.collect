import { Component } from '@angular/core';
import './rxjs-operators';

import { SurveyService } from './metamodel/survey.service';

@Component({
    selector: 'collect-app',
    template: '<router-outlet></router-outlet>',
    providers: [SurveyService]
})
export class AppComponent {
    
}