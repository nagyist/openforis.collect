import { Component, ViewChild, OnInit } from '@angular/core';

import { RecordSummary } from './../model/record-summary';
import { RecordService } from './../model/record.service';

@Component({
    selector: 'record-table',
    templateUrl: 'app/data-entry/record-table.component.html',
    styleUrls: ['app/data-entry/record-table.component.css']
})
export class RecordTableComponent implements OnInit {
    
    records: RecordSummary[]; 
//    cols: any[];
    displayDialog: boolean;    
    selectedRecord: RecordSummary;    
    newRecord: boolean;    
    
    constructor(private recordService: RecordService) { }  
    
    ngOnInit() {
        let surveyId = 1;
        let rootEntityDefId = 1;
          
        this.recordService.getRecordSummaries(surveyId, rootEntityDefId)
            .subscribe(records => this.records = records);
//        this.cols = [            
//            { field: 'name',   header: 'Name' }        
//        ]; 
     } 
}