import { NgModule }                 	from '@angular/core';
import { CommonModule }                 from '@angular/common';
//import { CollectSharedModule }       	from '../shared/collect-shared.module';

import { DataManagementComponent }      from './data-management.component';
import { DataManagementRoutingModule }  from './data-management-routing.module';
import { RecordTableComponent }       	from './record-table.component';
import { DataEntryComponent }       	from './data-entry/data-entry.component';
import { DataTableModule }              from 'primeng/primeng';

@NgModule({
    imports: [
        DataManagementRoutingModule,
//    	CollectSharedModule,
        CommonModule,
        DataTableModule
    ],
    declarations: [ 
    	DataManagementComponent,
    	RecordTableComponent,
    	DataEntryComponent
    ]
})
export class DataManagementModule { }