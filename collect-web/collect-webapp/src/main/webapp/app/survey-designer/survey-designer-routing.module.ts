import { NgModule }                 from '@angular/core';
import { Routes,
         RouterModule }             from '@angular/router';

import { SurveyDesignerHomeComponent }  from './survey-designer-home.component';

const routes: Routes = [
    {
        path: '',
        component: SurveyDesignerHomeComponent,
        data: {
            title: 'Survey Designer'
        }
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class SurveyDesignerRoutingModule {}