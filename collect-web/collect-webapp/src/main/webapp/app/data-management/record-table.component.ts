import { Component, ViewChild, OnInit } from '@angular/core';

import { LazyLoadEvent } from './../..//node_modules/primeng/components/common/api';

import { RecordSummary } from './../model/record-summary';
import { RecordService } from './../model/record.service';

import { DataTableModule } from 'primeng/primeng';

@Component({
    selector: 'record-table',
    templateUrl: './app/data-management/record-table.component.html',
    styleUrls: ['./app/data-management/record-table.component.css']
})
export class RecordTableComponent implements OnInit {
    
    records: RecordSummary[]; 
    displayDialog: boolean;    
    selectedRecords: RecordSummary[];    
    newRecord: boolean;
    totalRecords: number = 0;
    keyColumns: any[];
    
    constructor(private recordService: RecordService) { }  
    
    ngOnInit() {
        this.keyColumns = [
            {field: 'rootEntityKey1', header: 'Key 1', sortable: true},
            {field: 'rootEntityKey2', header: 'Key 2', sortable: true},
            {field: 'rootEntityKey3', header: 'Key 3', sortable: true}
        ];
    } 
    
    loadRecordsLazy(event: LazyLoadEvent) {
        let surveyId = 1;
        let rootEntityDefId = 1;
        
        this.recordService.getRecordsCount(surveyId, rootEntityDefId)
            .subscribe(totalRecords => this.totalRecords = totalRecords);
        
        this.recordService.getRecordSummaries(surveyId, rootEntityDefId, 
                event.first, event.rows, 
                event.sortField ? event.sortField : "rootEnityKey1", event.sortOrder)
            .subscribe(records => this.records = records);
    }
    
}