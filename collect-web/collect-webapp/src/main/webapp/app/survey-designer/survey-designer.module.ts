import { NgModule }                 	from '@angular/core';
import { CollectSharedModule }       	from '../shared/collect-shared.module';

import { SurveyDesignerHomeComponent }      from './survey-designer-home.component';
import { SurveyDesignerRoutingModule }  from './survey-designer-routing.module';
import { SurveyTableComponent }       	from './survey-table.component';
import { DataTableModule, SharedModule } from 'primeng/primeng';

@NgModule({
    imports: [
        SurveyDesignerRoutingModule,
    	CollectSharedModule,
        DataTableModule
    ],
    declarations: [ 
    	SurveyDesignerHomeComponent,
    	SurveyTableComponent
    ]
})
export class SurveyDesignerModule { }