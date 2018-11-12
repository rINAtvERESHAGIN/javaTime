import {Component, OnDestroy, OnInit} from '@angular/core';
import {ResourceService} from "@app/service/resource.service";
import {finalize, tap} from "rxjs/internal/operators";
import {AbstractControl, FormBuilder, FormControl, FormGroup} from "@angular/forms";
import {Dictionary} from "async";
import {RawResource} from "@app/model/tables/rawResource";

export interface ResourceInputs extends Dictionary<AbstractControl> {
    resourceNameInputFormControlName: AbstractControl,
    resourceCommentInputFormControlName: AbstractControl,
}
export interface ResourceEdits extends Dictionary<AbstractControl> {
    resourceEditNameInputFormControlName: AbstractControl,
    resourceEditCommentInputFormControlName: AbstractControl,
}

@Component({
    selector: 'resource-table',
    providers: [ResourceService],
    templateUrl: './resource-table.component.html',
    styleUrls: ['./resource-table.component.scss']
})
export class ResourceTableComponent implements OnInit, OnDestroy {

    allResources: RawResource[] = [];
    selectedResources: RawResource[] = [];

    doOpenAddResourceModal: boolean = false;
    doOpenEditResourceModal: boolean = false;


    resourceInputFormGroup: FormGroup;
    resourceNameInputFormControl: FormControl = new FormControl();
    resourceCommentInputFormControl: FormControl = new FormControl();

    resourceEditInputFormGroup : FormGroup;
    resourceEditNameInputFormControl: FormControl = new FormControl();
    resourceEditCommentInputFormControl: FormControl = new FormControl();

    constructor(private httpService: ResourceService,
                private builder: FormBuilder
    ) {
        this.resourceInputFormGroup = this.builder.group({
            resourceNameInputFormControlName: this.resourceNameInputFormControl,
            resourceCommentInputFormControlName: this.resourceCommentInputFormControl,
        } as ResourceInputs);
        this.resourceEditInputFormGroup=this.builder.group({
            resourceEditNameInputFormControlName: this.resourceEditNameInputFormControl,
            resourceEditCommentInputFormControlName: this.resourceEditCommentInputFormControl,
            }as ResourceEdits)
    }

    ngOnInit() {

        this.updateTable();
    }

    ngOnDestroy() {

    }

    updateTable() {
        this.httpService.getResourceTable()
            .pipe(
                tap(() => {
                    // do something before all actions
                }),
                finalize(() => {
                    // do something after all actions
                })
            ).subscribe(
            response => {
                this.allResources = response;
            },
            (error) => {
                console.log(error);
            });
    }

    onAdd() {
        console.log("open modal window");
        this.doOpenAddResourceModal = true;

    }


    saveNewResource() {
        console.log("saveNewResource");

        let inputs = this.resourceInputFormGroup.controls as ResourceInputs;

        let resourceName: string = inputs.resourceNameInputFormControlName.value;
        let resourceComment: string = inputs.resourceCommentInputFormControlName.value;

        if (resourceName.trim().length == 0) {
            console.log("ERROR: empty value for resourceName");
            return;
        }

        let resource: RawResource = new RawResource();
        resource.name = resourceName;
        resource.comment = resourceComment;

        this.httpService.addNewResource(resource)
            .pipe(
                tap(() => {
                    // do something before all actions
                }),
                finalize(() => {
                    // do something after all actions
                    this.closeModal();
                    this.updateTable();
                })
            ).subscribe();
    }




    closeModal() {
        console.log("close modal");

        this.doOpenAddResourceModal = false;
        this.resourceInputFormGroup.reset();

        this.doOpenEditResourceModal = false;
        this.resourceEditInputFormGroup.reset();

    }


    editResource() {
        console.log("editResource");

        let inputs = this.resourceEditInputFormGroup.controls as ResourceEdits;
        let resourceName: string = inputs.resourceEditNameInputFormControlName.value;
        let resourceComment: string = inputs.resourceEditCommentInputFormControlName.value;

        console.log("before",this.selectedResources[0]);

        let resource: RawResource =this.selectedResources[0];
        resource.name = resourceName;
        resource.comment = resourceComment;

        this.httpService.editResource(resource)
            .pipe(
                tap(() => {
                    // do something before all actions
                }),
                finalize(() => {
                    // do something after all actions
                    this.closeModal();
                    this.updateTable();
                })
            ).subscribe();


    }

    doEdit() {
        this.doOpenEditResourceModal = true;
        let editSelectedRow=this.selectedResources[0];

        let inputs = this.resourceEditInputFormGroup.controls as ResourceEdits;

        inputs.resourceEditNameInputFormControlName.setValue(editSelectedRow.name);
        inputs.resourceEditCommentInputFormControlName.setValue(editSelectedRow.comment);
    }

    onDelete() {
        console.log("onDelete");
    }
}