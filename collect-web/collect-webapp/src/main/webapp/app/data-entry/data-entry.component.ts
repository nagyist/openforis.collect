import { Component, OnInit }    from '@angular/core';

@Component({
    templateUrl: 'app/data-entry/data-entry.component.html',
    styleUrls: ['app/data-entry/data-entry.component.css']
})
export class DataEntryComponent implements OnInit {
    title = 'Data Entry';
   
    constructor() { }

    ngOnInit(): void {
    }

    gotoDetail(): void {
    }

}