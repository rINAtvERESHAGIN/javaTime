import {Component, OnDestroy, OnInit} from '@angular/core';
import {finalize, tap} from "rxjs/internal/operators";
import {FormBuilder} from "@angular/forms";
import {ContributionService} from "@app/service/contribution.service";
import {ContributionView} from "@app/model/view/contributionView";


@Component({
    selector: 'contribution-view',
    providers: [ContributionService],
    templateUrl: './contribution-view.component.html',
    styleUrls: ['./contribution-view.component.scss']
})
export class ContributionViewComponent implements OnInit, OnDestroy {

    allContributionsView: ContributionView[]=[];


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
        this.httpService.getContributionView()
            .pipe(
                tap(() => {
                    // do something before all actions
                }),
                finalize(() => {
                    // do something after all actions
                })
            ).subscribe(
            response => {
                this.allContributionsView = response;
            },
            (error) => {
                console.log(error);
            });
    }


}