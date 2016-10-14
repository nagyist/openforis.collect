import { Component, ViewChild, OnInit, LazyLoadEvent } from '@angular/core';

import { RecordSummary } from './../model/record-summary';
import { RecordService } from './../model/record.service';

@Component({
    selector: 'record-table',
    templateUrl: './app/data-management/record-table.component.html',
    styleUrls: ['./app/data-management/record-table.component.css']
})
export class RecordTableComponent implements OnInit {
    
    records: RecordSummary[]; 
//    cols: any[];
    displayDialog: boolean;    
    selectedRecords: RecordSummary[];    
    newRecord: boolean;
    totalRecords: number = 0;
    
    constructor(private recordService: RecordService) { }  
    
    ngOnInit() {
        let surveyId = 1;
        let rootEntityDefId = 1;
        let offset = 0;
        let maxNumberOfRecords = 20;
        
//        this.recordService.getRecordsCount(surveyId, rootEntityDefId)
//            .subscribe(totalRecords => this.totalRecords = totalRecords);
//        
//        this.recordService.getRecordSummaries(surveyId, rootEntityDefId, offset, maxNumberOfRecords)
//            .subscribe(records => this.records = records);
//        this.cols = [            
//            { field: 'name',   header: 'Name' }        
//        ]; 
    } 
    
    loadRecordsLazy(event: LazyLoadEvent) {
        let surveyId = 1;
        let rootEntityDefId = 1;
        
        this.recordService.getRecordsCount(surveyId, rootEntityDefId)
            .subscribe(totalRecords => this.totalRecords = totalRecords);
        
        this.recordService.getRecordSummaries(surveyId, rootEntityDefId, 
            event.first, event.rows, 
            event.sortField ? event.sortField : "key1", event.sortOrder)
            .subscribe(records => this.records = records);
    }
    
}