import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder} from "@angular/forms";
import {RawContribution} from "@app/model/tables/rawContribution";
import {ContributionService} from "@app/service/contribution.service";


@Component({
    selector: 'contribution-table',
    providers: [ContributionService],
    templateUrl: './contribution-table.component.html',
    styleUrls: ['./contribution-table.component.scss']
})
export class ContributionTableComponent implements OnInit, OnDestroy {

    allContributions: RawContribution[]=[];
    selectedContributions: RawContribution[]=[];


    constructor(private httpService: ContributionService,
                private builder: FormBuilder
    ) {

    }

    ngOnDestroy(): void {
    }

    ngOnInit(): void {
        this.updateTable();
    }

    updateTable() {

    }


}