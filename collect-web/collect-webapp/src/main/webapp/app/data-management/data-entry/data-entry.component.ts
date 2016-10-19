import { Component, OnInit }    from '@angular/core';

import { RecordService } from '../../model/record.service';

@Component({
    templateUrl: './app/data-management/data-entry/data-entry.component.html',
    styleUrls: ['./app/data-management/data-entry/data-entry.component.css'],
    providers: [RecordService]
})
export class DataEntryComponent implements OnInit {
    title = 'Data Entry';
   
    constructor() { }

    ngOnInit(): void {
    }

    gotoDetail(): void {
    }

}