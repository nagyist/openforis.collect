import { NgModule }                 from '@angular/core';
import { Routes,
         RouterModule }             from '@angular/router';

//import { DataManagementComponent }  from './data-management.component';
import { DataEntryComponent }       from './data-entry/data-entry.component';

const routes: Routes = [
    {
        path: '',
//        component: DataManagementComponent,
        data: {
            title: 'Data Management'
        },
        children: [
            {
                path: 'data-entry',
                component: DataEntryComponent,
                data: {
                    title: 'Data Entry'
                }
            }
        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class DataManagementRoutingModule {}