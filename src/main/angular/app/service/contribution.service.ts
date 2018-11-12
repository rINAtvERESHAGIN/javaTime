import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {map} from "rxjs/internal/operators";
import {Observable} from "rxjs/Rx";
import {plainToClass} from "class-transformer";
import {ContributionView} from "@app/model/view/contributionView";


@Injectable()
export class ContributionService {

    constructor(private http: HttpClient) {
    }

    

    getContributionView(): Observable<ContributionView[]> {
        return this.http.get<ContributionView[]>("java-people/get-contribution-view")
            .pipe(
                map(response => plainToClass(ContributionView, response as Object[])
                )
            );
    }
}